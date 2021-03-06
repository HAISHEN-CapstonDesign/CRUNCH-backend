package com.crunch.crunch_server.domain.community.controller;

import java.util.List;

import com.crunch.crunch_server.domain.community.dto.ChatMessageDTO;
import com.crunch.crunch_server.domain.community.dto.SocketDTO;
import com.crunch.crunch_server.domain.community.dto.TagNameDTO;
import com.crunch.crunch_server.domain.community.service.ChatService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(SocketController.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private SimpMessagingTemplate messageTemplate;

    @Autowired
    private ChatService chatService;
  
    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable String roomId, @Payload SocketDTO chatMessage) {
      messagingTemplate.convertAndSend("/channel/"+ roomId, chatMessage);

      //have to save all
      chatService.saveChat(roomId, chatMessage);
      
      // //have to check tag name and save in notice
      // //before that send just check message to specific user
      // List<TagNameDTO> tagNames = chatMessage.getTagName();
      // String senderName = chatMessage.getUserName();
      // System.out.println("==========="+senderName+"============");

      // for(int i =0; i<tagNames.size(); i++){
      //   String username = tagNames.get(i).getName();
      //   System.out.println("==============="+username+"==========");
      //   messageTemplate.convertAndSendToUser(username, "/queue", senderName+"tag you" +username);
      // }

    }

    // @MessageMapping("/singleShout")
    // public void singleUser(StompHeaderAccessor stompHeaderAccessor) {
    //     String message = shout.getMessage();
    //              LOGGER.info("received message:" + message);
    //     Principal user = stompHeaderAccessor.getUser();
    //     simpMessageSendingOperations.convertAndSendToUser(user.getName(), "/queue/shouts", shout);
    // }

  
    @MessageMapping("/chat/{roomId}/addUser")
    public void addUser(@DestinationVariable String roomId, @Payload ChatMessageDTO chatMessage,
        SimpMessageHeaderAccessor headerAccessor) {
      String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
      
    //we don't leave
    //   if (currentRoomId != null) {
    //     ChatMessage leaveMessage = new ChatMessage();
    //     leaveMessage.setType(MessageType.LEAVE);
    //     leaveMessage.setSender(chatMessage.getSender());
    //     messagingTemplate.convertAndSend(format("/channel/%s", currentRoomId), leaveMessage);
    //   }
      headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    //  messagingTemplate.convertAndSend("/channel/"+ roomId, chatMessage);
    }

    
}

