package kafka.controller;

import kafka.entity.MessageTest;
import kafka.service.KafkaMessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MessageController {
    private final KafkaMessagingService service;

    @PostMapping()
    public String sendMessage(@RequestBody MessageTest messageTest){
        return service.sendOrder(messageTest);
    }
}
