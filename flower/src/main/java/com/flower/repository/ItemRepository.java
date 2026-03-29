package com.flower.repository;

import com.flower.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {
    Page <Item> findAll(Pageable pageable);
    Page <Item> findByCategoryId(Long categoryId, Pageable pageable);
    List <Item> findByHitTrue();
    List <Item> findByIsNewTrue();
}
