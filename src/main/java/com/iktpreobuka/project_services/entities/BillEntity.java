package com.iktpreobuka.project_services.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BillEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private Boolean paymentMade;
	private Boolean paymentCanceled;
	private Date billMade;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer")
	private UserEntity buyer;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "offer")
	private OfferEntity offer;

	public BillEntity() {
		super();
	}

	public BillEntity(Integer id, Boolean paymentMade, Boolean paymentCanceled, UserEntity buyer, OfferEntity offer, Date billMade) {
		super();
		this.id = id;
		this.paymentMade = paymentMade;
		this.paymentCanceled = paymentCanceled;
		this.buyer = buyer;
		this.offer = offer;
		this.billMade = billMade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean isPaymentMade() {
		return paymentMade;
	}

	public void setPaymentMade(Boolean paymentMade) {
		this.paymentMade = paymentMade;
	}

	public Boolean isPaymentCanceled() {
		return paymentCanceled;
	}

	public void setPaymentCanceled(Boolean paymentCanceled) {
		this.paymentCanceled = paymentCanceled;
	}

	public UserEntity getBuyer() {
		return buyer;
	}

	public void setBuyer(UserEntity buyer) {
		this.buyer = buyer;
	}

	public OfferEntity getOffer() {
		return offer;
	}

	public void setOffer(OfferEntity offer) {
		this.offer = offer;
	}

	public Date getBillMade() {
		return billMade;
	}

	public void setBillMade(Date billMade) {
		this.billMade = billMade;
	}
	
}
