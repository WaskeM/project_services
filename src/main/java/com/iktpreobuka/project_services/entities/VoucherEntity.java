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
public class VoucherEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id; 
	private Date expirationDate;
	private Boolean isUsed;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer")
	private UserEntity buyer;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "offer")
	private OfferEntity offer;

	public VoucherEntity() {}
	
	public VoucherEntity(Integer id, Date expirationDate, Boolean isUsed, UserEntity buyer, OfferEntity offer) {
		super();
		this.id = id;
		this.expirationDate = expirationDate;
		this.isUsed = isUsed;
		this.buyer = buyer;
		this.offer = offer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Boolean isUsed() {
		return isUsed;
	}

	public void setUsed(Boolean isUsed) {
		this.isUsed = isUsed;
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
	
}
