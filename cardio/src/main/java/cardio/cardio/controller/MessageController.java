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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.Header;

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
    public void enter(MessageDto messageDto, @Header(name = "Authorization") String token) {
        User user = userService.getUserByUserId(tokenProvider.getUserId(token));
        Team team = teamService.getTeamByTeamId(messageDto.getTeamId());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


        MessageDto message = MessageDto.builder()
                .content(messageDto.getContent())
                .createdDate(now.format(formatter))
                .sender(user.getUsername())
                .senderDisplayname(user.getDisplayname())
                .receiver(messageDto.getReceiver())
                .teamId(team.getTeamId())
                .type(INDIVISUAL)
                .build();

        messageService.createMessage(Message.builder()
            .content(messageDto.getContent())
            .isRead(false)
            .type(messageDto.getType())
            .createdDate(now)
            .sender(user)
            .receiver(userService.getSimpleUserByUsername(messageDto.getReceiver()))
            .team(team)
            .build());

        if(messageDto.getType().equals(GROUP)) {
            sendingOperations.convertAndSend("/sub/chat/group", message);
        } else {
            sendingOperations.convertAndSend("/sub/chat/users/" + user.getUsername(), message);
            sendingOperations.convertAndSend("/sub/chat/users/" + messageDto.getReceiver(), message);
        }   
    }
}