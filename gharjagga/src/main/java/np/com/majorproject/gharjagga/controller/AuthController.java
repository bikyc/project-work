package np.com.majorproject.gharjagga.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;

import np.com.majorproject.gharjagga.entities.ERole;
import np.com.majorproject.gharjagga.entities.Roles;
import np.com.majorproject.gharjagga.entities.Users;
import np.com.majorproject.gharjagga.jwt.JwtUtils;
import np.com.majorproject.gharjagga.repository.RoleRepository;
import np.com.majorproject.gharjagga.repository.UsersRepository;
import np.com.majorproject.gharjagga.request.LoginRequest;
import np.com.majorproject.gharjagga.request.SignUpRequest;
import np.com.majorproject.gharjagga.response.JwtResponse;
import np.com.majorproject.gharjagga.services.UserDetailsImpl;

@SuppressWarnings("unused")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController{

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UsersRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												userDetails.getId(),
												userDetails.getName(),
												userDetails.getUsername(),
												userDetails.getEmail(),
												userDetails.getContact(),												
												userDetails.getGender(),
												userDetails.getAddress(),
												roles));
	}
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body("Error: Username is already taken!");
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body("Error: Email is already in use!");
		}

		// Create new user's account
		Users user = new Users();
				 			user.setName(signUpRequest.getName());
				 			user.setUsername(signUpRequest.getUsername());
				 			user.setEmail(signUpRequest.getEmail());
				 			
							 user.setPassword(encoder.encode(signUpRequest.getPassword()));
							
							 user.setAddress(signUpRequest.getAddress());
							 user.setContact(signUpRequest.getContact());
							
							 user.setGender(signUpRequest.getGender());
						

		
		Set<String> strRoles = signUpRequest.getRole();
		Set<Roles> roles = new HashSet<>();

		if (strRoles == null) {
			Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Roles adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Roles modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok("User registered successfully!");
	}

	@PutMapping("updateUserInfo/{id}")
	public Users updateUserInfo(@PathVariable Long id ,@RequestBody Users user) {
		
		
	Object principal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	Long userId=((UserDetailsImpl)principal).getId();
		try{
		if(id==userId){
	 	 return userRepository.findById(userId).map(updateUser->{
		updateUser.setEmail(user.getEmail());
		updateUser.setAddress(user.getAddress());
		updateUser.setContact(user.getContact());
		updateUser.setGender(user.getGender());
		updateUser.setName(user.getName());
		 return userRepository.save(updateUser);
	}).orElseThrow(()->new Exception("id"+id+"not found"));
	
	}
		}catch(Exception e){
			return null;
		}
		return null;
	}
	
	
}

	


	

