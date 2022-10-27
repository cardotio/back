package cardio.cardio.service.message;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cardio.cardio.dto.MessageDto;
import cardio.cardio.entity.Message;
import cardio.cardio.entity.User;
import cardio.cardio.exception.NotFoundException;
import cardio.cardio.repository.MessageRepository;
import cardio.cardio.repository.TeamRepository;
import cardio.cardio.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Override
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<MessageDto> getMessages(String sendername, String receivername) {
        User sender = userRepository.findByUsername(sendername);
        User receiver = userRepository.findByUsername(receivername);
        System.out.println("Receiver : " +  receivername);
        List<MessageDto> messages = messageRepository.findAllBySender(sender)
            .orElseThrow(() -> new NotFoundException("메세지를 찾을 수 없습니다1.")).stream()
            .filter(message -> message.getReceiver().getUsername().equals(receivername))
            .map(message -> MessageDto.from(message))
            .collect(Collectors.toList());
        
        List<MessageDto> receiveMessages = messageRepository.findAllBySender(receiver)
        .orElseThrow(() -> new NotFoundException("메세지를 찾을 수 없습니다1.")).stream()
        .filter(message -> message.getReceiver().getUsername().equals(sendername))
        .map(message -> MessageDto.from(message))
        .collect(Collectors.toList());

        messages.addAll(receiveMessages);
        if(messages.size() == 0) throw new NotFoundException("메세지를 찾을 수 없습니다2.");
        return messages;
    }

    //팀 안의 메세지 가져오기
    @Override
    public List<MessageDto> getTeamMessages(Long teamId) {
        
        return messageRepository.findAllByTeam(teamRepository.findById(teamId)
            .orElseThrow(() -> new NotFoundException("팀을 찾을 수 없습니다.")))
            .orElseThrow(() -> new NotFoundException("팀의 메세지를 찾을 수 없습니다.")).stream()
            .map(message -> MessageDto.from(message))
            .collect(Collectors.toList());
    }

}
