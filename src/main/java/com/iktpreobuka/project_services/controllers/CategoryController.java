package com.iktpreobuka.project_services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project_services.entities.CategoryEntity;
import com.iktpreobuka.project_services.repositories.CategoryRepository;
import com.iktpreobuka.project_services.services.BillDao;
import com.iktpreobuka.project_services.services.OfferDao;

@RestController
@RequestMapping(path = "/project/categories")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private BillDao billDao;

	@Autowired
	private OfferDao offerDao;

	@RequestMapping(method = RequestMethod.POST)
	public CategoryEntity addNewCategory(@RequestBody CategoryEntity newCategory) {

		if (newCategory == null || newCategory.getCategoryName() == null || newCategory.getCategoryName().equals(" ")
				|| newCategory.getCategoryName().equals("")) {
			return null;
		}

		return categoryRepository.save(newCategory);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public CategoryEntity updateCategory(@PathVariable Integer id, @RequestBody CategoryEntity updateCategory) {
		CategoryEntity category = categoryRepository.findById(id).get();

		if (category == null || updateCategory == null) {
			return null;
		}

		if (updateCategory.getCategoryName() != null || !updateCategory.getCategoryName().equals(" ")
				|| !updateCategory.getCategoryName().equals("")) {
			category.setCategoryName(updateCategory.getCategoryName());
		}

		if (updateCategory.getCategoryDescription() != null || !updateCategory.getCategoryDescription().equals(" ")
				|| !updateCategory.getCategoryDescription().equals("")) {
			category.setCategoryDescription(updateCategory.getCategoryDescription());
		}

		return categoryRepository.save(category);

	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<CategoryEntity> getAllCategories() {
		return categoryRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public CategoryEntity findByCategoryId(@PathVariable Integer id) {
		return categoryRepository.findById(id).get();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public CategoryEntity deleteCategory(@PathVariable Integer id) {
		CategoryEntity category = categoryRepository.findById(id).get();

		if (category == null) {
			return null;
		}

		if (billDao.findActiveBillsForCategories(category).size() == 0
				&& offerDao.findByActiveOffersForCategory(category).size() == 0) {
			categoryRepository.deleteById(id);
			return category;
		}

		return null;

	}

}
