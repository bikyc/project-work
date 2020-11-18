package np.com.majorproject.gharjagga.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import np.com.majorproject.gharjagga.entities.PropertyDetails;


@Repository
public interface PropertyDetailsRepository extends JpaRepository<PropertyDetails, Long> {
	
	@Query(value="select * from property_details where city like %:city%" , nativeQuery = true)
	List<PropertyDetails> findByCity(String city); 
	
	@Query(value="select * from property_details where type like %:type%",nativeQuery = true)
	List<PropertyDetails> findByType(String type); 
	
	@Query(value="select * from property_details where purpose like %:purpose%",nativeQuery = true)
	List<PropertyDetails> findByPurpose(String purpose); 
	

}
