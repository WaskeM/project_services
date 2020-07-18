package com.iktpreobuka.project_services.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.project_services.entities.BillEntity;
import com.iktpreobuka.project_services.entities.CategoryEntity;
import com.iktpreobuka.project_services.entities.OfferEntity;
import com.iktpreobuka.project_services.entities.UserEntity;

public interface BillRepository extends CrudRepository<BillEntity, Integer> {

	List<BillEntity> findByBillMade(Date date);

	List<BillEntity> findByOffer(OfferEntity offer);
	
	List<BillEntity> findByPaymentCanceledAndOffer_OfferCategory(boolean paymentCanceled, CategoryEntity category);
	
	List<BillEntity> findByBuyer(UserEntity buyer);
}
