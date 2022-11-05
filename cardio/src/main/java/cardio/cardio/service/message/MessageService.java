package cardio.cardio.service.message;

import java.util.List;

import cardio.cardio.dto.MessageDto;
import cardio.cardio.entity.Message;

public interface MessageService {
    Message createMessage(Message message);
    List<MessageDto> getMessages(String sendername, String receivername);
    List<MessageDto> getTeamMessages(Long teamId);

    void readMessages(Long teamId, String user1, String user2);
}
