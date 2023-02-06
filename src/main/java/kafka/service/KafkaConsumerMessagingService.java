package kafka.service;

import kafka.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumerMessagingService {

    @Value("${topic.name")
    private String topicName;
    @KafkaListener(topics = "${topic.name}", groupId = "group_id")
    public void consume(Message message) {
        log.info(String.format("message created -> %s", message));
    }
}
