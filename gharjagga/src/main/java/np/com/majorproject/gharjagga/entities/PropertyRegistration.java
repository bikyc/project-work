package np.com.majorproject.gharjagga.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PropertyRegistration {
	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 private Long Id;
	 
	 private String type;
	 
	 private Long price;
	 
	 private String location;
	 
	 private String city;
	 
	 private String purpose;
	 
	 private boolean availability =true;
	 
	 private String description;
	 
	 private String propertyOwner;
	 
	 private String propertyAddedTime;
	 
	 private String ownerContact;
	 
	 private String uploadDir;
	 
	 private String legalDocumentDir;

	public PropertyRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PropertyRegistration(String type, Long price, String location, String city, String purpose,
			boolean availability, String description, String propertyOwner, String propertyAddedTime,
			String ownerContact, String uploadDir, String legalDocumentDir) {
		super();
		this.type = type;
		this.price = price;
		this.location = location;
		this.city = city;
		this.purpose = purpose;
		this.availability = availability;
		this.description = description;
		this.propertyOwner = propertyOwner;
		this.propertyAddedTime = propertyAddedTime;
		this.ownerContact = ownerContact;
		this.uploadDir = uploadDir;
		this.legalDocumentDir = legalDocumentDir;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPropertyOwner() {
		return propertyOwner;
	}

	public void setPropertyOwner(String propertyOwner) {
		this.propertyOwner = propertyOwner;
	}

	public String getPropertyAddedTime() {
		return propertyAddedTime;
	}

	public void setPropertyAddedTime(String propertyAddedTime) {
		this.propertyAddedTime = propertyAddedTime;
	}

	public String getOwnerContact() {
		return ownerContact;
	}

	public void setOwnerContact(String ownerContact) {
		this.ownerContact = ownerContact;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public String getLegalDocumentDir() {
		return legalDocumentDir;
	}

	public void setLegalDocumentDir(String legalDocumentDir) {
		this.legalDocumentDir = legalDocumentDir;
	}
	 
	 
	 
	 
	 

}