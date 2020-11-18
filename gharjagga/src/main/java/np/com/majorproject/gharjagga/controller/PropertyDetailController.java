package np.com.majorproject.gharjagga.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import np.com.majorproject.gharjagga.entities.PropertyDetails;
import np.com.majorproject.gharjagga.entities.PropertyRegistration;
import np.com.majorproject.gharjagga.repository.PropertyDetailsRepository;
import np.com.majorproject.gharjagga.repository.PropertyRegistrationRepository;
import np.com.majorproject.gharjagga.services.UserDetailsImpl;

@RestController
@RequestMapping("api/property")
public class PropertyDetailController {
	@Autowired
	PropertyRegistrationRepository addPRepo;
	
	@Autowired 
	PropertyDetailsRepository pDetailsRepo;
	
	@PostMapping("/save")
	
	public ResponseEntity<?> savePropertyDeatil(@RequestBody PropertyDetails pDetails){
	
		//to get current logged in user
		
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=((UserDetailsImpl)principal).getUsername();
		
		//to get current date and time
		DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime currentTime= LocalDateTime.now();
		
		PropertyDetails addProperty= new PropertyDetails(pDetails.getType(),
														pDetails.getPrice(),
														pDetails.getLocation(),
														pDetails.getCity(),
														pDetails.getPurpose(),
														pDetails.isAvailability(),
														pDetails.getDescription(),
														username,
														dtf.format(currentTime),
														pDetails.getOwnerContact(),
														pDetails.getUploadDir(),
														pDetails.getLegalDocumentDir()
															);
		
		pDetailsRepo.save(addProperty);
		
		return ResponseEntity.ok("Property added successfully, please wait for verification");
	}
	
	@GetMapping("/listOfProperty")
	
	public List<PropertyDetails> listOfProperty(){
		return pDetailsRepo.findAll();
		
	}
	
	@GetMapping("/search/ById/{id}")
	
	public Optional<PropertyDetails> listOfPropertyById(@PathVariable Long id){
		return pDetailsRepo.findById(id);		
		
	}
	
	@GetMapping("/search/ByType/{type}")
	
	public List<PropertyDetails> listOfPropertyByType(@PathVariable String type){
		return pDetailsRepo.findByType(type);	
		
	}
	
	@GetMapping("/search/ByCity/{city}")
	
	public List<PropertyDetails> listOfPropertyByCity(@PathVariable String city){
		return pDetailsRepo.findByCity(city);		
		
	}
		
	@GetMapping("/search/ByPurpose/{Purpose}")
		
	public List<PropertyDetails> listOfPropertyByPurpose(@PathVariable String purpose){
		return pDetailsRepo.findByPurpose(purpose);		
			
		}
	
	@PutMapping("/updateProperty/{id}")
	
	public PropertyDetails updateProperty (@RequestBody PropertyDetails pDetails, @PathVariable Long id){
		
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=((UserDetailsImpl)principal).getUsername();
		
		Optional<PropertyDetails> a=pDetailsRepo.findById(id);
		
		PropertyDetails b=a.get();
		String propertyOwner=b.getPropertyOwner();
		
		if(username.equals(propertyOwner)) {
			
		
		try {
			
			return  pDetailsRepo.findById(id).map(updateProperty->{
					
				updateProperty.setAvailability(pDetails.isAvailability());
				updateProperty.setCity(pDetails.getCity());
				updateProperty.setDescription(pDetails.getDescription());
				updateProperty.setLegalDocumentDir(pDetails.getLegalDocumentDir());
				updateProperty.setLocation(pDetails.getLocation());
				updateProperty.setOwnerContact(pDetails.getOwnerContact());
				updateProperty.setPrice(pDetails.getPrice());
				updateProperty.setPurpose(pDetails.getPurpose());
				updateProperty.setType(pDetails.getType());
				updateProperty.setUploadDir(pDetails.getUploadDir());
				
			  return pDetailsRepo.save(updateProperty);}).orElseThrow(()->new Exception("id"+id+"not found"));
	
		} 
		catch (Exception e) {
			return null;
		
		}
		}
		else {
			return null;
		}
		
		}
	
	@PutMapping("/add")
	
	public PropertyRegistration addProperty (@RequestBody PropertyRegistration rDetails, @PathVariable Long id){
		
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username=((UserDetailsImpl)principal).getUsername();
		
		Optional<PropertyRegistration> a=addPRepo.findById(id);
		
		PropertyRegistration b=a.get();
		String propertyOwner=b.getPropertyOwner();
		
		if(username.equals(propertyOwner)) {
			
		
		try {
			
			return  addPRepo.findById(id).map(addProperty->{
					
				addProperty.setAvailability(rDetails.isAvailability());
				addProperty.setCity(rDetails.getCity());
				addProperty.setDescription(rDetails.getDescription());
				addProperty.setLegalDocumentDir(rDetails.getLegalDocumentDir());
				addProperty.setLocation(rDetails.getLocation());
				addProperty.setOwnerContact(rDetails.getOwnerContact());
				addProperty.setPrice(rDetails.getPrice());
				addProperty.setPurpose(rDetails.getPurpose());
				addProperty.setType(rDetails.getType());
				addProperty.setUploadDir(rDetails.getUploadDir());
				
			  return addPRepo.save(addProperty);}).orElseThrow(()->new Exception("id"+id+"not found"));
	
		} 
		catch (Exception e) {
			return null;
		
		}
		}
		else {
			return null;
		}
		
		}
		
	@DeleteMapping("/deleteProperty/{id}")
	
	public ResponseEntity<?> deleteProperty( @PathVariable Long id){
		
		pDetailsRepo.deleteById(id);
		return ResponseEntity.ok("deleted successfully");
	}
		
	}
	
	
