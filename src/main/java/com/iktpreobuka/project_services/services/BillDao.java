package com.iktpreobuka.project_services.services;

import java.util.Date;
import java.util.List;

import com.iktpreobuka.project_services.entities.BillEntity;
import com.iktpreobuka.project_services.entities.CategoryEntity;
import com.iktpreobuka.project_services.entities.OfferEntity;

public interface BillDao {
		public List<BillEntity> findActiveBillsForCategories (CategoryEntity category);
		public List<BillEntity> findBillsInPeriod (Date startDate, Date endDate);
		public void cancelBillsForOffer (OfferEntity offer);
}
