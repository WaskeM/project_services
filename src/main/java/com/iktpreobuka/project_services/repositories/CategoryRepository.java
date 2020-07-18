package com.iktpreobuka.project_services.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.project_services.entities.CategoryEntity;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
	CategoryEntity findByCategoryName (String name);
}
