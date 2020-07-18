package com.iktpreobuka.project_services.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project_services.entities.BillEntity;
import com.iktpreobuka.project_services.entities.CategoryEntity;
import com.iktpreobuka.project_services.entities.OfferEntity;
import com.iktpreobuka.project_services.entities.UserEntity;
import com.iktpreobuka.project_services.enumerations.EUserRole;
import com.iktpreobuka.project_services.repositories.BillRepository;
import com.iktpreobuka.project_services.repositories.CategoryRepository;
import com.iktpreobuka.project_services.repositories.OfferRepository;
import com.iktpreobuka.project_services.repositories.UserRepository;
import com.iktpreobuka.project_services.services.BillDao;
import com.iktpreobuka.project_services.services.OfferDao;
import com.iktpreobuka.project_services.services.VoucherDao;

@RestController
@RequestMapping(path = "/project/bills")
public class BillController {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private OfferDao offerDao;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private BillDao billDao;

	@Autowired
	private VoucherDao voucherDao;
	
	@Value(value = "${dateFormat}")
	private String dateFormat;

	@RequestMapping(method = RequestMethod.POST, value = "/{offerId}/buyer/{buyerId}")
	public BillEntity addNewBill(@RequestBody BillEntity newBill, @PathVariable Integer offerId, 
			@PathVariable Integer buyerId) {
		
		if(!offerRepository.findById(offerId).isPresent()) {
			return null;
		}
		
		if(!userRepository.findById(buyerId).isPresent() || !userRepository.findById(buyerId).get().getRole().equals(EUserRole.ROLE_CUSTOMER)) {
			return null;
		}
		
		OfferEntity offer =  offerRepository.findById(offerId).get();
		UserEntity buyer = userRepository.findById(buyerId).get();
	
		newBill.setBillMade(new Date());
		newBill.setPaymentCanceled(false);
		newBill.setPaymentMade(false);
		newBill.setOffer(offer);
		newBill.setBuyer(buyer);
		
		offerDao.updateOffer(offer, true);
		
		return billRepository.save(newBill);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public BillEntity updateBill(@PathVariable Integer id, @RequestBody BillEntity updateBill) {

		if (!billRepository.findById(id).isPresent()) {
			return null;
		}
		BillEntity bill = billRepository.findById(id).get();

		bill.setPaymentCanceled(updateBill.isPaymentCanceled());
		bill.setPaymentMade(updateBill.isPaymentMade());
		
		if(bill.isPaymentMade()) {
			voucherDao.createVoucher(bill);
		}

		if (bill.isPaymentCanceled()) {
			offerDao.updateOffer(updateBill.getOffer(), false);
		}

		return billRepository.save(bill);
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<BillEntity> getAllBills() {
		return billRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public BillEntity findById(@PathVariable Integer id) {
		return billRepository.findById(id).get();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public BillEntity deleteBill(@PathVariable Integer id) {
		

		if (!billRepository.findById(id).isPresent()) {
			return null;
		}
		BillEntity bill = billRepository.findById(id).get();
		billRepository.deleteById(id);
		return bill;
	}


	@RequestMapping(method = RequestMethod.GET, value = "/findByBuyer/{buyerId}")
	public List<BillEntity> findBillsByBuyerId(@PathVariable Integer buyerId) {
		List<BillEntity> bills = new ArrayList<BillEntity>();
		UserEntity buyer = userRepository.findById(buyerId).get();

		if (buyer != null) {
			bills = billRepository.findByBuyer(buyer);
		}

		return bills;
	}


	@RequestMapping(method = RequestMethod.GET, value = "/findByCategory/{categoryId}")
	public List<BillEntity> findBillsByCategoryId(@PathVariable Integer categoryId) {
		List<BillEntity> bills = new ArrayList<BillEntity>();
		List<OfferEntity> offers = new ArrayList<OfferEntity>();
		CategoryEntity category = categoryRepository.findById(categoryId).get();

		if (category != null) {
			offers = offerRepository.findByOfferCategory(category);
		}

		for (OfferEntity offer : offers) {
			bills.addAll(billRepository.findByOffer(offer));
		}
		return bills;
	}


	@RequestMapping(method = RequestMethod.GET, value = "/findByDate/{startDate}/and/{endDate}")
	public List<BillEntity> findBillsByDate(@PathVariable String startDate, @PathVariable String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		Date start = null;
		Date end = null;

		try {
			start = sdf.parse(startDate);
			end = sdf.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		return billDao.findBillsInPeriod(start, end);
	}
}
