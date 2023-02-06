package kafka.service;

import kafka.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class KafkaProducerMessagingService {

    @Value("${topic.name}")
    private String topicName;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaProducerMessagingService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String sendMessage(Message message) {
        ListenableFuture<SendResult<String, Object>> send = kafkaTemplate.send(topicName, message);
        return send.toString();
    }
}