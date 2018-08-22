package com.cmpl.web.configuration.core.common;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.message.DefaultWebMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration des cles i18n
 *
 * @author Louis
 */
@Configuration
public class ResourceConfiguration {

  /**
   * Declaration des sources de cles i18n
   */
  @Bean
  public WebMessageSource messageSource() {
    DefaultWebMessageSource source = new DefaultWebMessageSource();
    source.setBasenames("i18n/keys", "i18n/back", "i18n/error", "i18n/form", "i18n/mails");
    source.setUseCodeAsDefaultMessage(true);
    source.setDefaultEncoding("UTF-8");
    return source;
  }
}
