package com.cmpl.web.core.meta;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.cmpl.web.core.common.service.BaseServiceImpl;

/**
 * Service pour les balises meta
 * 
 * @author Louis
 *
 */
@CacheConfig(cacheNames = "metaElements")
public class MetaElementServiceImpl extends BaseServiceImpl<MetaElementDTO, MetaElement> implements MetaElementService {

  private final MetaElementRepository metaElementRepository;

  public MetaElementServiceImpl(MetaElementRepository metaElementRepository) {
    super(metaElementRepository);
    this.metaElementRepository = metaElementRepository;
  }

  @Override
  @Cacheable(value = "metaForPage", key = "#a0")
  public List<MetaElementDTO> findMetaElementsByPageId(String pageId) {
    return toListDTO(metaElementRepository.findByPageId(pageId));
  }

  @Override
  protected MetaElementDTO toDTO(MetaElement entity) {
    MetaElementDTO dto = new MetaElementDTO();
    fillObject(entity, dto);
    return dto;
  }

  @Override
  protected MetaElement toEntity(MetaElementDTO dto) {
    MetaElement entity = new MetaElement();
    fillObject(dto, entity);
    return entity;
  }

  @Override
  @CacheEvict(value = {"metaForPage", "pages"}, allEntries = true)
  public MetaElementDTO createEntity(MetaElementDTO dto) {
    return super.createEntity(dto);
  }

  @Override
  @CacheEvict(value = {"metaForPage", "pages"}, allEntries = true)
  public void deleteEntity(Long id) {
    super.deleteEntity(id);
  }

}
