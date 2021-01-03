package com.jose.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jose.api.models.Servicios;

public interface ServicesRepository extends JpaRepository<Servicios, Long>{

	/*@Query(value="SELECT s FROM Servicios s")
	List<Servicios> findAll();*/
	
}
