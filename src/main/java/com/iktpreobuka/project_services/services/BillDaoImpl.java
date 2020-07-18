package com.iktpreobuka.project_services.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.project_services.entities.BillEntity;
import com.iktpreobuka.project_services.entities.CategoryEntity;
import com.iktpreobuka.project_services.entities.OfferEntity;
import com.iktpreobuka.project_services.repositories.BillRepository;

@Service
public class BillDaoImpl implements BillDao{

	@PersistenceContext
    EntityManager em;
	
	@Autowired
	private BillRepository billRepository;
	
	@Override
	public List<BillEntity> findActiveBillsForCategories(CategoryEntity category) {
		return billRepository.findByPaymentCanceledAndOffer_OfferCategory(false, category);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BillEntity> findBillsInPeriod(Date startDate, Date endDate) {
		String sql = "select b " +
                "from BillEntity b "
                + "where b.billMade >= :startDate and b.billMade <= :endDate";
		
		Query query = em.createQuery(sql);
        query.setParameter("startDate", startDate, TemporalType.DATE);
        query.setParameter("endDate", endDate, TemporalType.DATE);
        
        List<BillEntity> result = new ArrayList<BillEntity>();
        result = query.getResultList();
        return result;
	}

	@Override
	public void cancelBillsForOffer(OfferEntity offer) {
		List<BillEntity> bills = billRepository.findByOffer(offer);
		for(BillEntity b : bills) {
			b.setPaymentCanceled(true);
		}
		billRepository.saveAll(bills);
		
	}

}
