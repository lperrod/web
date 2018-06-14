package com.cmpl.web.core.page;

import com.cmpl.web.core.common.mapper.BaseMapper;

public class PageMapper extends BaseMapper<PageDTO, Page> {

  @Override
  public PageDTO toDTO(Page entity) {
    PageDTO dto = PageDTOBuilder.create().build();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  public Page toEntity(PageDTO dto) {
    Page entity = PageBuilder.create().build();
    fillObject(dto, entity);
    return entity;
  }
}
