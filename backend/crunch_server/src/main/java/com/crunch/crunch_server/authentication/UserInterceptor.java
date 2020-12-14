package com.crunch.crunch_server.authentication;

import java.util.LinkedList;
import java.util.Map;

import com.crunch.crunch_server.authentication.dto.User;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;

public class UserInterceptor extends ChannelInterceptorAdapter {

    @Override
       public Message<?> preSend(Message<?> message, MessageChannel channel) {

           StompHeaderAccessor accessor =
                   MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

           if (StompCommand.CONNECT.equals(accessor.getCommand())) {
               Object raw = message
                       .getHeaders()
                       .get(SimpMessageHeaderAccessor.NATIVE_HEADERS);

               if (raw instanceof Map) {
                   Object username = ((Map) raw).get("username");

                   if (username instanceof LinkedList) {
                       accessor.setUser(new User(((LinkedList) username).get(0).toString()));
                   }
               }
           }
           return message;
       }
    
}