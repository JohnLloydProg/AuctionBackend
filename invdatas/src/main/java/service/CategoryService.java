package service;

import dto.CategoryDTO;
import entity.Category;
import repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::toDto).toList();
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));
        return toDto(category);
    }

    public CategoryDTO createCategory(CategoryDTO dto) {
        Category saved = categoryRepository.save(toEntity(dto));
        return toDto(saved);
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));
        existing.setName(dto.getName());
        return toDto(categoryRepository.save(existing));
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found: " + id);
        }
        categoryRepository.deleteById(id);
    }

    private CategoryDTO toDto(Category c) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(c.getCategoryId());
        dto.setName(c.getName());
        return dto;
    }

    private Category toEntity(CategoryDTO dto) {
        Category c = new Category();
        c.setCategoryId(dto.getCategoryId());
        c.setName(dto.getName());
        return c;
    }
}
