package com.flower.model.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ItemMapper {
}
