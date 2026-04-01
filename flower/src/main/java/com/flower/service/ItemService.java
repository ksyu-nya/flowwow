package com.flower.service;


import com.flower.model.entity.Item;
import com.flower.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class ItemService {

private final ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public List<Item> findByCategory(String category) {
        return itemRepository.findByCategory(category);
    }

    public List<String> getAllCategories() {
        return itemRepository.findDistinctCategory();
    }

    public List<Item> findHits() {
        return itemRepository.findByIsHitTrue();
    }

    public List<Item> findNewItems() {
        return itemRepository.findByIsNewTrue();
    }

    public Item findById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Товар не найден с id " + id));
    }

    // ==================== АДМИНСКИЕ МЕТОДЫ ====================

    @Transactional
    public Item create(Item item) {
        // id должен быть null, чтобы БД сама сгенерировала
        item.setId(null);
        return itemRepository.save(item);
    }

    @Transactional
    public Item update(Item item) {
        if (!itemRepository.existsById(item.getId())) {
            throw new EntityNotFoundException("Товар не найден с id: " + item.getId());
        }
        return itemRepository.save(item);
    }

    @Transactional
    public void delete(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new EntityNotFoundException("Товар не найден с id: " + id);
        }
        itemRepository.deleteById(id);
    }

}
