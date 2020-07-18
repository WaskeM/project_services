package com.iktpreobuka.project_services.services;

import java.util.List;

import com.iktpreobuka.project_services.entities.CategoryEntity;
import com.iktpreobuka.project_services.entities.OfferEntity;

public interface OfferDao {
	
	public void updateOffer(OfferEntity offer, boolean billCreated);
	public List<OfferEntity> findByActiveOffersForCategory(CategoryEntity category);
}
