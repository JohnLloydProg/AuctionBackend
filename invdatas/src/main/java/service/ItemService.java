package service;

import dto.ItemDTO;
import entity.Category;
import entity.Item;
import repository.CategoryRepository;
import repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ItemDTO> getAllItems() {
        return itemRepository.findAll().stream().map(this::toDto).toList();
    }

    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found: " + id));
        return toDto(item);
    }

    public ItemDTO createItem(ItemDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: " + dto.getCategoryId()));

        Item item = new Item();
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setSellerId(dto.getSellerId());
        item.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        item.setCategory(category);

        return toDto(itemRepository.save(item));
    }

    public ItemDTO updateItem(Long id, ItemDTO dto) {
        Item existing = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found: " + id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setSellerId(dto.getSellerId());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found: " + dto.getCategoryId()));
            existing.setCategory(category);
        }

        return toDto(itemRepository.save(existing));
    }

    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("Item not found: " + id);
        }
        itemRepository.deleteById(id);
    }

    private ItemDTO toDto(Item item) {
        ItemDTO dto = new ItemDTO();
        dto.setItemId(item.getItemId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setCreatedAt(item.getCreatedAt());
        dto.setSellerId(item.getSellerId());
        dto.setCategoryId(item.getCategory() != null ? item.getCategory().getCategoryId() : null);
        return dto;
    }
}
