package com.cmpl.web.configuration.core.notification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.notification.NotificationCenter;

@Configuration
@EnableWebSocketMessageBroker
public class NotificationConfiguration implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/notifications");
    config.setApplicationDestinationPrefixes("/manager");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/manager-websocket").setAllowedOrigins("*").withSockJS();
  }

  @Bean
  public NotificationCenter notificationCenter(SimpMessagingTemplate template, WebMessageSource messageSource) {
    return new NotificationCenter(template, messageSource);
  }

}
