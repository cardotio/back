package cardio.cardio.dto;


import java.time.format.DateTimeFormatter;

import cardio.cardio.entity.Message;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private Long teamId;
    private String content;
    private String sender;
    private String senderDisplayname;
    private String receiver;
    private String createdDate;
    
    private Boolean type; // false = 1대1, true = main 

    public static MessageDto from(Message message) {
        return MessageDto.builder()
            .content(message.getContent())
            .sender(message.getSender().getUsername())
            .senderDisplayname(message.getSender().getDisplayname())
            .receiver(message.getReceiver().getUsername())
            .createdDate(message.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .teamId(message.getTeam().getTeamId())
            .type(message.getType())
            .build();
    }
}
