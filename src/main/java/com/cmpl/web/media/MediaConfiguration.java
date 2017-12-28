package com.cmpl.web.media;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.context.ContextHolder;
import com.cmpl.web.file.FileService;
import com.cmpl.web.menu.MenuFactory;
import com.cmpl.web.message.WebMessageSource;

@Configuration
public class MediaConfiguration {

  @Bean
  public MediaService mediaService(MediaRepository mediaRepository, FileService fileService, ContextHolder contextHolder) {
    return new MediaServiceImpl(mediaRepository, fileService, contextHolder);
  }

  @Bean
  public MediaManagerDisplayFactory mediaManagerDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      MediaService mediaService, ContextHolder contextHolder) {
    return new MediaManagerDisplayFactoryImpl(menuFactory, messageSource, mediaService, contextHolder);
  }

}