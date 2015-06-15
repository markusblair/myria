package org.adventure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.SockJsServiceRegistration;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketAppConfig extends AbstractWebSocketMessageBrokerConfigurer {
	private static Logger log = LoggerFactory.getLogger(WebSocketAppConfig.class);
	
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        SockJsServiceRegistration sockJsServiceRegistration = registry.addEndpoint("/notify").withSockJS();
    }

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.setInterceptors(new ChannelInterceptorAdapter() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				if (message.getHeaders().containsKey("stompCommand")) {
					if (message.getHeaders().get("stompCommand").equals(StompCommand.DISCONNECT)) {
						log.debug("Disconnecting");
					}
					else if (message.getHeaders().get("stompCommand").equals(StompCommand.CONNECT)) {
						log.debug("Connecting");
					}
				}
				return message;
			}
			 
		});
	}
    
    
}
