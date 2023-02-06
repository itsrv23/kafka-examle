package kafka.controller;

import kafka.entity.Message;
import kafka.service.KafkaProducerMessagingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageController {
    private final KafkaProducerMessagingService service;
    private static final int THREADS = 4;

    @PostMapping
    public String sendMessage(@RequestBody Message message) {
        var es = Executors.newFixedThreadPool(THREADS);
        Task task = new Task();
        for (int i = 0; i < THREADS; i++) {
            es.execute(task);
        }
        es.shutdown();
        return "fine ^-^ ";
    }

    class Task implements Runnable {
        private static final AtomicLong id = new AtomicLong(1);

        @Override
        public void run() {
            log.info("Start sending message, thread: " +  Thread.currentThread().getName());
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            for (int i = 0; i < 100000; i++) {
                var msg = new Message(id.getAndIncrement(), "some message");
                service.sendMessage(msg);
            }
            stopWatch.stop();
            log.info("Time execute: " + stopWatch.getTotalTimeMillis() + " ms");
        }
    }
}
