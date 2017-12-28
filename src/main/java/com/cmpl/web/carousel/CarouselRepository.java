package com.cmpl.web.carousel;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmpl.web.core.repository.BaseRepository;

@Repository
public interface CarouselRepository extends BaseRepository<Carousel> {

  List<Carousel> findByPageId(String pageId);

}