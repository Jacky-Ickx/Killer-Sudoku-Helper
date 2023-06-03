package ksh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket Endpoint Configuration for Spring application
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	/**
	 * add /session/broker as Broker Endpoint add /session/handler as Destination Prefix
	 */
	@Override
	public void configureMessageBroker(final MessageBrokerRegistry config) {
		config.enableSimpleBroker("/session/broker");
		config.setApplicationDestinationPrefixes("/session/handler");
	}

	/**
	 * add /websocket as endpoint for connecting to application via Stomp & SockJS
	 * 
	 * allow all origins
	 */
	@Override
	public void registerStompEndpoints(final StompEndpointRegistry registry) {
		registry.addEndpoint("/websocket").setAllowedOrigins("*");
		registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
	}
}
