package com.iktpreobuka.project_services.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.project_services.enumerations.EOfferStatus;


@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class OfferEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String offerName;
	private String offerDescription;
	private Date offerCreated;
	private Date offerExpires;
	private Double regularPrice;
	private Double actionPrice;
	private String imagePath;
	private Integer availableOffers;
	private Integer boughtOffers;
	private EOfferStatus offerStatus;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "offerCategory")
	private CategoryEntity offerCategory;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "seller")
	private UserEntity seller;

	
	
	@JsonIgnore
	@OneToMany(mappedBy = "offer", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<BillEntity> offer_bills = new ArrayList<>();


	@JsonIgnore
	@OneToMany(mappedBy = "offer", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<VoucherEntity> offer_vouchers = new ArrayList<>();

	public OfferEntity() {
		super();
	}

	public OfferEntity(Integer id, String offerName, String offerDescription, Date offerCreated, Date offerExpires,
			Double regularPrice, Double actionPrice, String imagePath, Integer availableOffers, Integer boughtOffers,
			EOfferStatus offerStatus, CategoryEntity offerCategory, UserEntity seller) {
		super();
		this.id = id;
		this.offerName = offerName;
		this.offerDescription = offerDescription;
		this.offerCreated = offerCreated;
		this.offerExpires = offerExpires;
		this.regularPrice = regularPrice;
		this.actionPrice = actionPrice;
		this.imagePath = imagePath;
		this.availableOffers = availableOffers;
		this.boughtOffers = boughtOffers;
		this.offerStatus = offerStatus;
		this.offerCategory = offerCategory;
		this.seller = seller;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getOfferDescription() {
		return offerDescription;
	}

	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}

	public Date getOfferCreated() {
		return offerCreated;
	}

	public void setOfferCreated(Date offerCreated) {
		this.offerCreated = offerCreated;
	}

	public Date getOfferExpires() {
		return offerExpires;
	}

	public void setOfferExpires(Date offerExpires) {
		this.offerExpires = offerExpires;
	}

	public Double getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(Double regularPrice) {
		this.regularPrice = regularPrice;
	}

	public Double getActionPrice() {
		return actionPrice;
	}

	public void setActionPrice(Double actionPrice) {
		this.actionPrice = actionPrice;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getAvailableOffers() {
		return availableOffers;
	}

	public void setAvailableOffers(Integer availableOffers) {
		this.availableOffers = availableOffers;
	}

	public Integer getBoughtOffers() {
		return boughtOffers;
	}

	public void setBoughtOffers(Integer boughtOffers) {
		this.boughtOffers = boughtOffers;
	}

	public EOfferStatus getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(EOfferStatus offerStatus) {
		this.offerStatus = offerStatus;
	}

	public CategoryEntity getOfferCategory() {
		return offerCategory;
	}

	public void setOfferCategory(CategoryEntity offerCategory) {
		this.offerCategory = offerCategory;
	}

	public List<BillEntity> getOffer_bills() {
		return offer_bills;
	}

	public void setOffer_bills(List<BillEntity> offer_bills) {
		this.offer_bills = offer_bills;
	}

	public List<VoucherEntity> getOffer_vouchers() {
		return offer_vouchers;
	}

	public void setOffer_vouchers(List<VoucherEntity> offer_vouchers) {
		this.offer_vouchers = offer_vouchers;
	}

	public UserEntity getSeller() {
		return seller;
	}

	public void setSeller(UserEntity seller) {
		this.seller = seller;
	}
	
}
