package cardio.cardio.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import cardio.cardio.dto.MessageDto;

import cardio.cardio.entity.Message;
import cardio.cardio.entity.Team;
import cardio.cardio.entity.User;
import cardio.cardio.jwt.TokenProvider;
import cardio.cardio.service.message.MessageService;
import cardio.cardio.service.team.TeamService;
import cardio.cardio.service.user.UserService;
import lombok.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.Header;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class ReadedMessagesInfo {
    private Long teamId;
    private String sender;
    private String receiver;
}

@RestController
public class MessageController {
    private final SimpMessageSendingOperations sendingOperations;
    private final TokenProvider tokenProvider;
    private final MessageService messageService;
    private final UserService userService;
    private final TeamService teamService;
    public MessageController(
        @Lazy SimpMessageSendingOperations sendingOperations, 
        @Lazy TokenProvider tokenProvider,
        @Lazy MessageService messageService, 
        @Lazy UserService userService, 
        @Lazy TeamService teamService) {
        this.sendingOperations = sendingOperations;
        this.tokenProvider = tokenProvider;
        this.messageService = messageService;
        this.userService = userService;
        this.teamService = teamService;
    }
    
    private final Boolean GROUP = true;
    private final Boolean INDIVISUAL = false;
    
    @MessageMapping("/message")
    public void handleMessages(MessageDto messageDto, @Header(name = "Authorization") String token) {
        User user = userService.getUserByUserId(tokenProvider.getUserId(token));
        Team team = teamService.getTeamByTeamId(messageDto.getTeamId());
        LocalDateTime now = LocalDateTime.now();
        
        Message message = Message.builder()
            .content(messageDto.getContent())
            .unread(1L)
            .type(messageDto.getType())
            .createdDate(now)
            .sender(user)
            .receiver(userService.getSimpleUserByUsername(messageDto.getReceiver()))
            .team(team)
            .build();

        messageService.createMessage(message);

        if(messageDto.getType().equals(GROUP)) {
            sendingOperations.convertAndSend("/sub/chat/group", MessageDto.from(message));
        } else {
            sendingOperations.convertAndSend("/sub/chat/users/" + user.getUsername(), MessageDto.from(message));
            sendingOperations.convertAndSend("/sub/chat/users/" + messageDto.getReceiver(), MessageDto.from(message));
        }   
    }

   

    @MessageMapping("/message/read")
    public void readMessage(ReadedMessagesInfo info, @Header(name = "Authorization") String token) {
        messageService.readMessages(info.getTeamId(), info.getSender(), info.getReceiver());
        sendingOperations.convertAndSend("/sub/chat/read/" + info.getReceiver(), info);
    }

    
}