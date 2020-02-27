package store.server.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.server.category.domain.Category;
import store.server.category.repository.CategoryRepository;
import store.server.item.domain.Item;
import store.server.item.exception.InvalidItemInfoException;
import store.server.item.exception.ItemNotFoundException;
import store.server.item.repository.ItemRepository;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final CategoryRepository categoryRepository;

    @Transactional
    public void deleteById(Long existingId) {
        if (itemNotFound(existingId))
            throw new ItemNotFoundException(String.format("Item with id: %d wasn't found!", existingId));

        itemRepository.deleteById(existingId);
    }

    private boolean itemNotFound(Long existingId) {
        return itemRepository.findById(existingId).isEmpty();
    }

    @Transactional
    public Item save(Item itemRequest) {
        if (nameExists(itemRequest))
            throw new InvalidItemInfoException(
                    String.format("Item name: %s already exists!", itemRequest.getName())
            );

        if (invalidCategories(itemRequest))
            throw new InvalidItemInfoException(
                    String.format("Invalid categories: %s!", itemRequest.getCategories())
            );

        itemRequest.initializePostDate();

        return itemRepository.save(itemRequest);
    }

    private boolean nameExists(Item itemRequest) {
        return itemRepository.findByName(itemRequest.getName()).isPresent();
    }

    private boolean invalidCategories(Item itemRequest) {
        return itemRequest.getCategories()
                .stream()
                .anyMatch(this::invalidCategory);
    }

    private boolean invalidCategory(Category category) {
        return categoryRepository.findById(category.getId()).isEmpty();
    }

    @Transactional
    public Item update(Long existingId, Item itemRequest) {
        Item existingItem = findById(existingId);

        if (nameChanged(existingItem, itemRequest) && nameExists(itemRequest))
            throw new InvalidItemInfoException(
                    String.format("Item name: %s already exists!", itemRequest.getName())
            );

        if (invalidCategories(itemRequest))
            throw new InvalidItemInfoException(
                    String.format("Invalid categories: %s!", itemRequest.getCategories())
            );

        return itemRepository.save(mapRequest(existingItem, itemRequest));
    }

    private boolean nameChanged(Item existingItem, Item itemRequest) {
        return !existingItem.getName().equalsIgnoreCase(itemRequest.getName());
    }

    private Item mapRequest(Item existingItem, Item itemRequest) {
        existingItem.setName(itemRequest.getName());
        existingItem.setPrice(itemRequest.getPrice());
        existingItem.setCategories(itemRequest.getCategories());
        existingItem.setDescription(itemRequest.getDescription());

        return existingItem;
    }

    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(
                        String.format("Item with id: %d wasn't found!", id)
                ));
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

}
