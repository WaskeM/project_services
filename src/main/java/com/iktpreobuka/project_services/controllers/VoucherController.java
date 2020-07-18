package com.iktpreobuka.project_services.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project_services.entities.OfferEntity;
import com.iktpreobuka.project_services.entities.UserEntity;
import com.iktpreobuka.project_services.entities.VoucherEntity;
import com.iktpreobuka.project_services.enumerations.EUserRole;
import com.iktpreobuka.project_services.repositories.OfferRepository;
import com.iktpreobuka.project_services.repositories.UserRepository;
import com.iktpreobuka.project_services.repositories.VoucherRepository;

@RestController
@RequestMapping(path = "/project/vouchers")
public class VoucherController {
	
	@Autowired
	private VoucherRepository voucherRepository;

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(method = RequestMethod.POST, value = "/{offerId}/buyerId/{buyerId}")
	public VoucherEntity addNewVoucher(@PathVariable Integer offerId, @PathVariable Integer buyerId) {
		VoucherEntity newVoucher = new VoucherEntity ();
		OfferEntity offer = offerRepository.findById(offerId).get();
		UserEntity buyer = userRepository.findById(buyerId).get();
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 7);

		newVoucher.setExpirationDate(cal.getTime());
		newVoucher.setUsed(false);
		if(offer!=null) {
			newVoucher.setOffer(offer);
		}
		
		if(buyer==null || !buyer.getRole().equals(EUserRole.ROLE_CUSTOMER)) {
			return null;
		}
		newVoucher.setBuyer(buyer);
		return voucherRepository.save(newVoucher);
		
		
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public VoucherEntity updateVoucher(@PathVariable Integer id, @RequestBody VoucherEntity updateVoucher) {
		VoucherEntity voucher = voucherRepository.findById(id).get();

		if (voucher == null || updateVoucher == null) {
			return null;
		}

		voucher.setUsed(updateVoucher.isUsed());
		return voucherRepository.save(voucher);

	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<VoucherEntity> getAllVouchers() {
		return voucherRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public VoucherEntity findByVoucherById(@PathVariable Integer id) {
		return voucherRepository.findById(id).get();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public VoucherEntity deleteVoucher(@PathVariable Integer id) {
		VoucherEntity voucher = voucherRepository.findById(id).get();

		if (voucher == null) {
			return null;
		}

		voucherRepository.deleteById(id);
		return voucher;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/findByBuyer/{buyerId}")
	public List<VoucherEntity> findVouchersByBuyerId(@PathVariable Integer buyerId) {
		List<VoucherEntity> vouchers = new ArrayList<VoucherEntity> ();
		UserEntity buyer = userRepository.findById(buyerId).get();
		if(buyer!=null) {
			vouchers = voucherRepository.findByBuyer(buyer);
		}
		
		return vouchers;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/findByOffer/{offerId}")
	public List<VoucherEntity> findVouchersByOfferId(@PathVariable Integer offerId) {

		OfferEntity offer = offerRepository.findById(offerId).get();
		if(offer!=null) {
			return voucherRepository.findByOffer(offer);
		}else {
			return null;
		}
		
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findNonExpiredVouchers")
	public List<VoucherEntity> findNonExpiredVouchers() {
		List<VoucherEntity> vouchers = (List<VoucherEntity>) voucherRepository.findAll();
		List<VoucherEntity> result =  new ArrayList<VoucherEntity> ();
		for(VoucherEntity v : vouchers) {
			if(v.getExpirationDate().after(new Date())) {
				result.add(v);
			}
		}
		return result;
	}

}
