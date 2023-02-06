package kafka.service;

import kafka.entity.MessageTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class KafkaMessagingService {

    @Value("${topic.name.producer}")
    private String topicName;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaMessagingService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String sendOrder(MessageTest messageTest) {
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(topicName, messageTest.toString());
        return send.toString();
    }
}