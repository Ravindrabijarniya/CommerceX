package com.ravindra.commercex.product.entity;

import com.ravindra.commercex.category.entity.Category;
import com.ravindra.commercex.common.entity.BaseEntity;
import com.ravindra.commercex.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
    name = "products",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_products_slug",
            columnNames = "slug"
        ),
        @UniqueConstraint(
            name = "uk_products_sku",
            columnNames = "sku"
        )
    }
)
public class Product extends BaseEntity {

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String slug;

    @Column(length = 500)
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String brand;

    @Column(nullable = false, length = 100)
    private String sku;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(length = 500)
    private String thumbnailUrl;

    @Column(nullable = false)
    private boolean featured;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "category_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_products_category")
    )
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return getId() != null && Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void assignCategory(Category category) {
        this.category = category;
    }

    public static Product create(
        String name,
        String slug,
        String shortDescription,
        String description,
        String brand,
        String sku,
        BigDecimal price,
        String thumbnailUrl,
        boolean featured,
        ProductStatus status
    ) {
        Product product = new Product();

        product.name = name;
        product.slug = slug;
        product.shortDescription = shortDescription;
        product.description = description;
        product.brand = brand;
        product.sku = sku;
        product.price = price;
        product.thumbnailUrl = thumbnailUrl;
        product.featured = featured;
        product.status = status;

        return product;
    }

    public void update(
        String name,
        String slug,
        String shortDescription,
        String description,
        String brand,
        String sku,
        BigDecimal price,
        String thumbnailUrl,
        boolean featured,
        ProductStatus status
    ) {
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.description = description;
        this.brand = brand;
        this.sku = sku;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.featured = featured;
        this.status = status;
    }

    public void changeStatus(ProductStatus status) {
        this.status = status;
    }

    public void markAsFeatured() {
        this.featured = true;
    }

    public void removeFeatured() {
        this.featured = false;
    }
}
