package com.icia.devhub.dto.order;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="DEV_CATEGORY")
@SequenceGenerator(name="DCG_SEQ_GENERATOR", sequenceName="DCG_SEQ", allocationSize=1)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DCG_SEQ_GENERATOR")
    private int category;

    @Column(nullable = false, length = 1000)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<ProductEntity> projects;

    public static CategoryEntity toEntity(CategoryDTO dto) {
        CategoryEntity category = new CategoryEntity();

        category.setCategory(dto.getCategory());
        category.setCategoryName(dto.getCategoryName());

        return category;
    }
}