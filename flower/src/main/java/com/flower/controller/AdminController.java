package com.flower.controller;

import com.flower.model.entity.Item;
import com.flower.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ItemService itemService;

    // ==================== ТОВАРЫ ====================

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItem(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findById(id));
    }

    @PostMapping("/items")
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) {
        item.setId(null);
        Item saved = itemService.create(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<Item> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody Item item) {
        item.setId(id);
        Item updated = itemService.update(item);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== КАТЕГОРИИ ====================

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.ok(itemService.getAllCategories());
    }

    @GetMapping("/categories/{category}/items")
    public ResponseEntity<List<Item>> getItemsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(itemService.findByCategory(category));
    }
}