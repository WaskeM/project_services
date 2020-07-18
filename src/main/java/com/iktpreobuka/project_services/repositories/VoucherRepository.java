package com.iktpreobuka.project_services.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.project_services.entities.OfferEntity;
import com.iktpreobuka.project_services.entities.UserEntity;
import com.iktpreobuka.project_services.entities.VoucherEntity;

public interface VoucherRepository extends CrudRepository<VoucherEntity, Integer> {
	List<VoucherEntity> findByBuyer (UserEntity buyer);
	List<VoucherEntity> findByOffer (OfferEntity offer);
}
