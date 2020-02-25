package store.server.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.server.category.domain.Category;
import store.server.category.exception.CategoryNotFoundException;
import store.server.category.exception.InvalidCategoryInfoException;
import store.server.category.repository.CategoryRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void deleteById(Long existingId) {
        if (categoryNotFound(existingId))
            throw new CategoryNotFoundException(String.format("Category with id: %d wasn't found!", existingId));

        categoryRepository.deleteById(existingId);
    }

    private boolean categoryNotFound(Long existingId) {
        return categoryRepository.findById(existingId).isEmpty();
    }

    @Transactional
    public Category save(Category categoryRequest) {
        if (nameExists(categoryRequest))
            throw new InvalidCategoryInfoException(
                    String.format("Category name: %s already exists!", categoryRequest.getName())
            );

        return categoryRepository.save(categoryRequest);
    }

    private boolean nameExists(Category categoryRequest) {
        return categoryRepository.findByName(categoryRequest.getName()).isPresent();
    }

    @Transactional
    public Category update(Long existingId, Category categoryRequest) {
        Category existingCategory = findById(existingId);

        if (nameChanged(existingCategory, categoryRequest) && nameExists(categoryRequest))
            throw new InvalidCategoryInfoException(
                    String.format("Category name: %s already exists!", categoryRequest.getName())
            );

        return categoryRepository.save(mapRequest(existingCategory, categoryRequest));
    }

    private boolean nameChanged(Category existingCategory, Category categoryRequest) {
        return !existingCategory.getName().equalsIgnoreCase(categoryRequest.getName());
    }

    private Category mapRequest(Category existingCategory, Category categoryRequest) {
        existingCategory.setName(categoryRequest.getName());
        existingCategory.setDescription(categoryRequest.getDescription());

        return existingCategory;
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(
                        String.format("Category with id: %d wasn't found!", id)
                ));
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}
