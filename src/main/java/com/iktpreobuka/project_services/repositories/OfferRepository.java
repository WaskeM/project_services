package com.iktpreobuka.project_services.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.project_services.entities.CategoryEntity;
import com.iktpreobuka.project_services.entities.OfferEntity;
import com.iktpreobuka.project_services.enumerations.EOfferStatus;

public interface OfferRepository extends CrudRepository<OfferEntity, Integer>{
	
	OfferEntity findByOfferName (String name);
	List<OfferEntity> findByOfferCategory (CategoryEntity category);	
	List<OfferEntity> findByOfferStatus (EOfferStatus status);
	List<OfferEntity> findByOfferStatusAndOfferCategory(EOfferStatus status, CategoryEntity category);
}
