package com.geekinstitut.smartmoney.service;

import com.geekinstitut.smartmoney.entity.Category;
import com.geekinstitut.smartmoney.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Ruft alle Kategorien ab.
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Ruft eine Kategorie anhand ihrer ID ab.
     */
    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category not found with id: " + id)
        );
    }

    /**
     * Erstellt eine neue Kategorie.
     */
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Aktualisiert eine bestehende Kategorie.
     */
    public Category updateCategory(UUID id, Category updatedCategory) {
        Category category = getCategoryById(id);
        category.setName(updatedCategory.getName());
        category.setPlannedInMonth(updatedCategory.getPlannedInMonth());
        category.setType(updatedCategory.getType());
        return categoryRepository.save(category);
    }

    /**
     * Löscht eine Kategorie anhand ihrer ID.
     */
    public void deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Kategorie nicht gefunden mit ID: " + id);
        }
        categoryRepository.deleteById(id);
    }
}