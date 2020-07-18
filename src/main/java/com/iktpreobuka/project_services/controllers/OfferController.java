package com.iktpreobuka.project_services.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iktpreobuka.project_services.entities.CategoryEntity;
import com.iktpreobuka.project_services.entities.OfferEntity;
import com.iktpreobuka.project_services.entities.UserEntity;
import com.iktpreobuka.project_services.enumerations.EOfferStatus;
import com.iktpreobuka.project_services.enumerations.EUserRole;
import com.iktpreobuka.project_services.repositories.CategoryRepository;
import com.iktpreobuka.project_services.repositories.OfferRepository;
import com.iktpreobuka.project_services.repositories.UserRepository;
import com.iktpreobuka.project_services.services.BillDao;
import com.iktpreobuka.project_services.services.FileHandler;

@RestController
@RequestMapping(path = "/project/offers")
public class OfferController {

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FileHandler fileHandler;
	
	@Autowired
	private BillDao billDao;

	@RequestMapping(method = RequestMethod.POST, value = "/{categoryId}/seller/{sellerId}")
	public OfferEntity addNewOffer(@RequestBody OfferEntity newOffer, @PathVariable Integer categoryId,
			@PathVariable Integer sellerId) {
		CategoryEntity category = categoryRepository.findById(categoryId).get();
		UserEntity seller = userRepository.findById(sellerId).get();

		if (category != null) {
			newOffer.setOfferCategory(category);
		}

		if (seller == null || !seller.getRole().equals(EUserRole.ROLE_SELLER)) {
			return null;
		}
		newOffer.setSeller(seller);
		newOffer.setOfferStatus(EOfferStatus.WAIT_FOR_APPROVING);
		newOffer.setOfferCreated(new Date());

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 10);

		newOffer.setOfferExpires(cal.getTime());

		return offerRepository.save(newOffer);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/category/{categoryId}")
	public OfferEntity updateOffer(@PathVariable Integer id, @PathVariable Integer categoryId,
			@RequestBody OfferEntity updateOffer) {
		OfferEntity offer = offerRepository.findById(id).get();
		CategoryEntity category = categoryRepository.findById(categoryId).get();

		if (offer == null || updateOffer == null) {
			return null;
		}

		if (updateOffer.getOfferName() != null || !updateOffer.getOfferName().equals(" ")
				|| !updateOffer.getOfferName().equals("")) {
			offer.setOfferName(updateOffer.getOfferName());
		}

		if (updateOffer.getOfferDescription() != null || !updateOffer.getOfferDescription().equals(" ")
				|| !updateOffer.getOfferDescription().equals("")) {
			offer.setOfferDescription(updateOffer.getOfferDescription());
		}

		if (updateOffer.getOfferCreated() != null || !updateOffer.getOfferCreated().toString().equals(" ")
				|| !updateOffer.getOfferCreated().toString().equals("")) {
			offer.setOfferCreated(updateOffer.getOfferCreated());
		}

		if (updateOffer.getOfferExpires() != null || !updateOffer.getOfferExpires().toString().equals(" ")
				|| !updateOffer.getOfferExpires().toString().equals("")) {
			offer.setOfferExpires(updateOffer.getOfferExpires());
		}

		if (updateOffer.getAvailableOffers() != null) {
			offer.setAvailableOffers(updateOffer.getAvailableOffers());
		}

		if (updateOffer.getBoughtOffers() != null) {
			offer.setBoughtOffers(updateOffer.getBoughtOffers());
		}

		if (updateOffer.getRegularPrice() != null) {
			offer.setRegularPrice(updateOffer.getRegularPrice());
		}

		if (updateOffer.getActionPrice() != null) {
			offer.setActionPrice(updateOffer.getActionPrice());
		}

		if (category != null) {
			offer.setOfferCategory(category);
		}

		return offerRepository.save(offer);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "changeOffer/{id}/status/{status}")
	public OfferEntity changeOfferStatus(@PathVariable Integer id, @PathVariable String status) {
		OfferEntity offer = offerRepository.findById(id).get();

		if (offer == null) {
			return null;
		}

		EOfferStatus offerStatus = EOfferStatus.valueOf(status);
		offer.setOfferStatus(offerStatus);
		if(offerStatus.equals(EOfferStatus.EXPIRED)) {
			billDao.cancelBillsForOffer(offer);
		}

		return offerRepository.save(offer);
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<OfferEntity> getAllOffers() {
		return offerRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public OfferEntity findById(@PathVariable Integer id) {
		return offerRepository.findById(id).get();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public OfferEntity deleteOffer(@PathVariable Integer id) {
		OfferEntity offer = offerRepository.findById(id).get();

		if (offer == null) {
			return null;
		}

		offerRepository.deleteById(id);
		return offer;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/approvedOffers")
	public List<OfferEntity> findApprovedOffers() {
		return offerRepository.findByOfferStatus(EOfferStatus.APPROVED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "findByPrice/{lowerPrice}/and/{upperPrice}")
	public List<OfferEntity> findOfferByPrice(@PathVariable Integer lowerPrice, @PathVariable Integer upperPrice) {
		List<OfferEntity> offers = (List<OfferEntity>) offerRepository.findAll();
		List<OfferEntity> result = new ArrayList<OfferEntity>();
		for (OfferEntity offer : offers) {
			if (offer.getActionPrice() >= lowerPrice && offer.getActionPrice() <= upperPrice) {
				result.add(offer);
			}
		}

		return result;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/uploadImage")
	public String uploadImage(@RequestParam("offerId") Integer offerId, @RequestParam("file") MultipartFile file) {
	
		if (offerRepository.findById(offerId).isPresent()) {
			OfferEntity offer = offerRepository.findById(offerId).get();
			String imagePath = null;
	        try {
	        	imagePath = fileHandler.singleFileUpload(offerId, file);
	        }catch (IOException e) {
				e.printStackTrace();
			}
			offer.setImagePath(imagePath);
			return "Image successfully uploaded";
		}
		return "Image has not been uploaded";
	}


}
