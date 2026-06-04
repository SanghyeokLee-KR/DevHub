package com.icia.devhub.service;

import com.icia.devhub.dao.CategoryRepository;
import com.icia.devhub.dto.order.CategoryDTO;
import com.icia.devhub.dto.order.CategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository crepo;

    public List<CategoryDTO> CategoryList(int category) {
        List<CategoryDTO> dtoList = new ArrayList<>();
        List<CategoryEntity> entityList = crepo.findAllBycategory(category);

        for (CategoryEntity entity : entityList) {
            dtoList.add(CategoryDTO.toDTO(entity));
        }
        return dtoList;
    }
}
