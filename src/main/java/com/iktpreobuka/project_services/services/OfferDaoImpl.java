package com.iktpreobuka.project_services.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.project_services.entities.CategoryEntity;
import com.iktpreobuka.project_services.entities.OfferEntity;
import com.iktpreobuka.project_services.enumerations.EOfferStatus;
import com.iktpreobuka.project_services.repositories.OfferRepository;

@Service
public class OfferDaoImpl implements OfferDao{

	@Autowired
	private OfferRepository offerRepository;
	
	@Override
	public void updateOffer(OfferEntity offer, boolean billCreated) {

		if(billCreated) {
			offer.setAvailableOffers(offer.getAvailableOffers()-1);
			offer.setBoughtOffers(offer.getBoughtOffers()+1);
		}else {
			offer.setAvailableOffers(offer.getAvailableOffers()+1);
			offer.setBoughtOffers(offer.getBoughtOffers()-1);
		}
		
		offerRepository.save(offer);
		
	}

	@Override
	public List<OfferEntity> findByActiveOffersForCategory(CategoryEntity category) {
		return offerRepository.findByOfferStatusAndOfferCategory(EOfferStatus.APPROVED, category);
	}


}
