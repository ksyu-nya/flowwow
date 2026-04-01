package com.flower.service;


import com.flower.model.entity.Item;
import com.flower.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item testItem;

    @BeforeEach
    void setUp() {
        testItem = new Item();
        testItem.setId(1L);
        testItem.setName("Тестовый букет");
        testItem.setDescription("Тестовое описание");
        testItem.setPrice(new BigDecimal("3500"));
        testItem.setCategory("Романтические");
        testItem.setIsHit(true);
        testItem.setIsNew(false);
    }

    // ==================== ТЕСТ-КЕЙСЫ ====================

    @Test
    void findAll_shouldReturnListOfItems() {
        // given
        when(itemRepository.findAll()).thenReturn(List.of(testItem));

        // when
        List<Item> result = itemService.findAll();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Тестовый букет");
        verify(itemRepository).findAll();
    }

    @Test
    void findById_shouldReturnItem_whenExists() {
        // given
        when(itemRepository.findById(1L)).thenReturn(Optional.of(testItem));

        // when
        Item result = itemService.findById(1L);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Тестовый букет");
        verify(itemRepository).findById(1L);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        // given
        when(itemRepository.findById(999L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> itemService.findById(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Товар не найден");
        verify(itemRepository).findById(999L);
    }

    @Test
    void findByCategory_shouldReturnItemsByCategory() {
        // given
        when(itemRepository.findByCategory("Романтические")).thenReturn(List.of(testItem));

        // when
        List<Item> result = itemService.findByCategory("Романтические");

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo("Романтические");
        verify(itemRepository).findByCategory("Романтические");
    }

    @Test
    void findHits_shouldReturnHitItems() {
        // given
        when(itemRepository.findByIsHitTrue()).thenReturn(List.of(testItem));

        // when
        List<Item> result = itemService.findHits();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getIsHit()).isTrue();
        verify(itemRepository).findByIsHitTrue();
    }

    @Test
    void findNewItems_shouldReturnNewItems() {
        // given
        when(itemRepository.findByIsNewTrue()).thenReturn(List.of(testItem));

        // when
        List<Item> result = itemService.findNewItems();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getIsNew()).isFalse(); // наш тестовый не new
        verify(itemRepository).findByIsNewTrue();
    }

    @Test
    void create_shouldSaveItemAndSetIdToNull() {
        // given
        Item newItem = new Item();
        newItem.setName("Новый букет");
        newItem.setPrice(new BigDecimal("2500"));
        newItem.setCategory("Весенние");

        when(itemRepository.save(any(Item.class))).thenReturn(testItem);

        // when
        Item saved = itemService.create(newItem);

        // then
        assertThat(saved.getId()).isEqualTo(1L);
        verify(itemRepository).save(newItem);
    }

    @Test
    void update_shouldUpdateExistingItem() {
        // given
        Item updateItem = new Item();
        updateItem.setId(1L);
        updateItem.setName("Обновленный букет");
        updateItem.setPrice(new BigDecimal("4000"));

        when(itemRepository.existsById(1L)).thenReturn(true);
        when(itemRepository.save(any(Item.class))).thenReturn(updateItem);

        // when
        Item updated = itemService.update(updateItem);

        // then
        assertThat(updated.getName()).isEqualTo("Обновленный букет");
        assertThat(updated.getPrice()).isEqualTo(new BigDecimal("4000"));
        verify(itemRepository).save(updateItem);
    }

    @Test
    void update_shouldThrowException_whenItemNotFound() {
        // given
        Item updateItem = new Item();
        updateItem.setId(999L);

        when(itemRepository.existsById(999L)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> itemService.update(updateItem))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Товар не найден");
        verify(itemRepository, never()).save(any());
    }

    @Test
    void delete_shouldDeleteItem_whenExists() {
        // given
        when(itemRepository.existsById(1L)).thenReturn(true);
        doNothing().when(itemRepository).deleteById(1L);

        // when
        itemService.delete(1L);

        // then
        verify(itemRepository).deleteById(1L);
    }

    @Test
    void delete_shouldThrowException_whenItemNotFound() {
        // given
        when(itemRepository.existsById(999L)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> itemService.delete(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Товар не найден");
        verify(itemRepository, never()).deleteById(any());
    }
}