package store.server.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.server.exception.ItemNotFoundException;
import store.server.item.domain.Item;
import store.server.item.repository.ItemRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

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
        itemRequest.initializePostDate();

        return itemRepository.save(itemRequest);
    }

    @Transactional
    public Item update(Long existingId, Item itemRequest) {
        if (itemNotFound(existingId))
            throw new ItemNotFoundException(String.format("Item with id: %d wasn't found!", existingId));

        itemRequest.setId(existingId);

        return itemRepository.save(itemRequest);
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
