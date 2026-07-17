package com.ravindra.commercex.category.entity;

import com.ravindra.commercex.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
    name = "categories",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_category_parent_name",
            columnNames = {"parent_id", "name"}
        )
    }
)
@ToString(callSuper = true)
public class Category extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, unique = true, length = 150)
    private String slug;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private CategoryStatus status = CategoryStatus.ACTIVE;

    @Column(nullable = false)
    @Builder.Default
    private Integer sortOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "parent_id",
        foreignKey = @ForeignKey(name = "fk_category_parent")
    )
    @ToString.Exclude
    private Category parent;

    @OneToMany(
        mappedBy = "parent",
        fetch = FetchType.LAZY
    )
    @Builder.Default
    @ToString.Exclude
    private Set<Category> children = new HashSet<>();


    public void rename(String name) {
        this.name = name;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void activate() {
        this.status = CategoryStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = CategoryStatus.INACTIVE;
    }

    public void softDelete() {
        this.status = CategoryStatus.DELETED;
    }

    public void changeSlug(String slug) {
        this.slug = slug;
    }


    public void changeParent(Category newParent) {

        if (this.parent != null) {
            this.parent.children.remove(this);
        }

        this.parent = newParent;

        if (newParent != null) {
            newParent.children.add(this);
        }
    }


    public void addChild(Category child) {

        Objects.requireNonNull(child, "Child category cannot be null");

        if (child == this) {
            throw new IllegalArgumentException("Category cannot be its own child.");
        }

        if (children.contains(child)) {
            return;
        }

        child.changeParent(this);
    }

    public void removeChild(Category child) {

        if (child == null) {
            return;
        }

        child.changeParent(null);
    }


    public boolean isRoot() {
        return parent == null;
    }


    public boolean isLeaf() {
        return children.isEmpty();
    }

    public boolean isActive() {
        return status == CategoryStatus.ACTIVE;
    }

    public boolean isDeleted() {
        return status == CategoryStatus.DELETED;
    }

    public boolean hasParent() {
        return parent != null;
    }


}
