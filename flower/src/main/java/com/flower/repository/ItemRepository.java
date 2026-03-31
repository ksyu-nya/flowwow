package com.flower.repository;

import com.flower.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {
    List <Item> findAll();
    List <Item> findByIsHitTrue();
    List <Item> findByIsNewTrue();
    List<Item> findByCategory(String category);
    // Получить все уникальные категории (для фильтров)
    List<String> findDistinctCategory();

}
