
import api from './axios';
import axios from "axios";

// Публичные эндпоинты (без авторизации)
export const getItems = async () => {
  try {
    const response = await api.get('/items');
    // Преобразуем поля из snake_case в camelCase для фронта
    return response.data.map(item => ({
      id: item.id,
      name: item.name,
      description: item.description,
      price: item.price,
      image_url: item.imageUrl,  // бэк отдает imageUrl, фронту нужно image_url
      category: item.category,
      is_hit: item.isHit,          // бэк отдает hit, фронту нужно is_hit
      is_new: item.isNew           // бэк отдает new, фронту нужно is_new
    }));
  } catch (error) {
    console.error('Ошибка загрузки товаров:', error);
    throw error;
  }
};

export const getItemById = async (id) => {
  try {
    const response = await api.get(`/items/${id}`);
    const item = response.data;
    return {
      id: item.id,
      name: item.name,
      description: item.description,
      price: item.price,
      image_url: item.imageUrl,
      category: item.category,
      is_hit: item.isHit,
      is_new: item.isNew
    };
  } catch (error) {
    console.error('Ошибка загрузки товара:', error);
    throw error;
  }
};

export const createItem = async (itemData) => {
  try {
    const backendData = {
      name: itemData.name,
      description: itemData.description,
      price: itemData.price,
      imageUrl: itemData.image_url,  // фронт дает image_url, бэку нужно imageUrl
      category: itemData.category,
      isHit: itemData.is_hit,        // фронт дает is_hit, бэку нужно isHit
      isNew: itemData.is_new         // фронт дает is_new, бэку нужно isNew
    };


    const response = await api.post('/admin/items', backendData);

    return response.data;
  } catch (error) {
    console.error('Ошибка создания товара:', error);
    throw error;
  }
};

export const updateItem = async (id, itemData) => {
  try {
    const backendData = {
      name: itemData.name,
      description: itemData.description,
      price: itemData.price,
      imageUrl: itemData.image_url,
      category: itemData.category,
      isHit: itemData.is_hit,
      isNew: itemData.is_new
    };

    const response = await api.put(`/admin/items/${id}`, backendData);
    return response.data;
  } catch (error) {
    console.error('Ошибка обновления товара:', error);
    throw error;
  }
};

export const deleteItem = async (id) => {
  try {
    const response = await api.delete(`/admin/items/${id}`);
    return response.data;
  } catch (error) {
    console.error('Ошибка удаления товара:', error);
    throw error;
  }
};