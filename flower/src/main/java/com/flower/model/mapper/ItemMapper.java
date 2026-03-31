//package com.flower.model.mapper;
//
//import com.flower.model.dto.request.items.CreateItemRequest;
//import com.flower.model.dto.request.items.UpdateItemRequest;
//import com.flower.model.dto.response.items.ItemResponse;
//import com.flower.model.entity.Item;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingTarget;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface ItemMapper {
//
//    @Mapping(target = "id", ignore = true)
//    Item toEntity(CreateItemRequest request);
//
//    ItemResponse toResponse(Item item);
//
//    List<ItemResponse> toResponseList(List<Item> items);
//
//    @Mapping(target = "id", ignore = true)
//    void updateEntity(UpdateItemRequest request, @MappingTarget Item item);
//}
