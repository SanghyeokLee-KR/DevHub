package com.icia.devhub.dao;

import com.icia.devhub.dto.order.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    List<CategoryEntity> findAllBycategory(int category);


}
