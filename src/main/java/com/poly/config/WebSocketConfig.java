package com.poly.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private static String Authorization = "Authorization";
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/channel");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/icomments").setAllowedOriginPatterns("*").withSockJS();
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//
//                if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
//                    String authToken = accessor.getFirstNativeHeader("Authorization");
//                    System.out.println("authToken " + authToken);
//                    if (authToken != null && authToken.startsWith("Bearer ")) {
//                        String jwt = jwtUtil.extractUsername(authToken.substring(7));
//                        UserDetails userDetails = userDetailsService.loadUserByUsername(jwt);
//                        if (jwtUtil.validateToken(authToken, userDetails)) {
//                            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//                            accessor.setUser(authentication);
//                            accessor.setHeader("Authorization", authToken);
//                            Authorization = authToken;
//                            SecurityContextHolder.getContext().setAuthentication(authentication);
////                        String itemId = accessor.getFirstNativeHeader("item_id");
////                        accessor.setDestination("/topic" + privateChatService.getChannelId(itemId, authentication.getName()));
////                        logger.info(accessor.getDestination()); //ex: /topic/chat/3434/chat_with/user3797474342423
//                        }
//                    }
//                }
//                return message;
//            }
//        });
//    }
}
