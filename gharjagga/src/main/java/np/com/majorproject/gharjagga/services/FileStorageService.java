package np.com.majorproject.gharjagga.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import np.com.majorproject.gharjagga.entities.PropertyDetails;
import np.com.majorproject.gharjagga.exception.FileStorageException;
import np.com.majorproject.gharjagga.repository.PropertyDetailsRepository;

@Service
public class FileStorageService {
	@Value("${file.storage.location}")
	private String hospitalFileDir;
	String relatedBodyPart;
	private Path fileStoragePath;
	private String fileStorageLocation;
	private String diseaseStatus;
	private String fileUploadedByDr;
	private String treatmentInHospital;
	@Autowired
	private PropertyDetailsRepository fileRepository;

	public FileStorageService(@Value("${file.storage.location:temp}") String fileStorageLocation)
	{
	this.fileStorageLocation=fileStorageLocation;
	fileStoragePath=Paths.get(fileStorageLocation).toAbsolutePath().normalize();
	try {
	Files.createDirectories(fileStoragePath);
	} catch (IOException e) {
	throw new RuntimeException("Issue in creating file directory");
	}
	}

	public PropertyDetails storeFile (PropertyDetails storeFile, MultipartFile file,String username,String fileDownloadUri,String randomChars )
	{
	// Normalize file name
	String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	String newFileName=randomChars+"_"+fileName;

	Path filePath = Paths.get(fileStoragePath + "\\" + newFileName);

	 try {
	Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	} catch (IOException e) {
	throw new RuntimeException("Issue in storing the file", e);
	}
	try {
	// Check if the file's name contains invalid characters
	if (newFileName.contains("..")) {
	throw new FileStorageException("Sorry! Filename contains invalid path sequence " + newFileName);
	}

	 //getting local date and time
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
	//set the details about PropertyDetails

	 return(fileRepository.save(storeFile));
	} catch (Exception ex) {
	throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	}
	}



	public Resource loadFileAsResource(String fileName) {
	try {
	Path filePath= this.fileStoragePath.resolve(fileName).normalize();
	Resource resource = new UrlResource(filePath.toUri());
	if (resource.exists()) {
	return resource;
	} else {
	throw new FileStorageException("File Not Found" + fileName);
	}
	} catch (MalformedURLException ex) {
	throw new FileStorageException("File Not Found"+ fileName, ex);
	}
	}

}
