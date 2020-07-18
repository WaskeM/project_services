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

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CategoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String categoryName;
	private String categoryDescription;
	
	@JsonIgnore
	@OneToMany(mappedBy = "offerCategory", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<OfferEntity> offers = new ArrayList<>();
	
	public CategoryEntity() {
		super();
	}

	public CategoryEntity(Integer id, String categoryName, String categoryDescription) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public List<OfferEntity> getOffers() {
		return offers;
	}

	public void setOffers(List<OfferEntity> offers) {
		this.offers = offers;
	}

}
