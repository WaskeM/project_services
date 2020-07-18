package com.iktpreobuka.project_services.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktpreobuka.project_services.enumerations.EUserRole;


@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private EUserRole role;

	@JsonIgnore
	@OneToMany(mappedBy = "seller", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<OfferEntity> seller_offers = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "buyer", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<BillEntity> buyer_bills = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "buyer", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<VoucherEntity> buyer_vouchers = new ArrayList<>();

	public UserEntity() {
		super();
	}

	public UserEntity(Integer id, String firstName, String lastName, String email, String username, String password,
			EUserRole role) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EUserRole getRole() {
		return role;
	}

	public void setRole(EUserRole role) {
		this.role = role;
	}

	public List<BillEntity> getBuyer_bills() {
		return buyer_bills;
	}

	public void setBuyer_bills(List<BillEntity> buyer_bills) {
		this.buyer_bills = buyer_bills;
	}

	public List<VoucherEntity> getBuyer_vouchers() {
		return buyer_vouchers;
	}

	public void setBuyer_vouchers(List<VoucherEntity> buyer_vouchers) {
		this.buyer_vouchers = buyer_vouchers;
	}

	public List<OfferEntity> getSeller_offers() {
		return seller_offers;
	}

	public void setSeller_offers(List<OfferEntity> seller_offers) {
		this.seller_offers = seller_offers;
	}
	
	
}
