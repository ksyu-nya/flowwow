package com.flower.controller;


import com.flower.model.entity.Item;
import com.flower.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.ok(itemService.getAllCategories());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Item>> getItemsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(itemService.findByCategory(category));
    }

    @GetMapping("/hits")
    public ResponseEntity<List<Item>> getHits() {
        return ResponseEntity.ok(itemService.findHits());
    }

    @GetMapping("/new")
    public ResponseEntity<List<Item>> getNewItems() {
        return ResponseEntity.ok(itemService.findNewItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findById(id));
    }

}
