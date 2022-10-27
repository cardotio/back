package cardio.cardio.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cardio.cardio.dto.MessageDto;
import cardio.cardio.service.message.MessageService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final MessageService messageService;
    @GetMapping("/chattest")
    public String room(Model model) {
        return "/roomdetail";
    }
    @GetMapping("/messages/{sendername}/{receivername}") 
    public ResponseEntity<List<MessageDto>> getMessages(@PathVariable String sendername, @PathVariable String receivername) {
        return ResponseEntity.ok(messageService.getMessages(sendername, receivername));
    }
}
