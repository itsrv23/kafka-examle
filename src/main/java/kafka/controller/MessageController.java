package kafka.controller;

import kafka.entity.Message;
import kafka.service.KafkaProducerMessagingService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageController {
    private final KafkaProducerMessagingService service;
    private static final int THREADS = 4;

    @PostMapping
    public String sendMessages(@RequestBody Message message) throws InterruptedException, ExecutionException {
        LocalDateTime start = LocalDateTime.now();
        var es = Executors.newFixedThreadPool(THREADS);
        CountDownLatch countDownLatch =  new CountDownLatch(THREADS);
        Task task = new Task(countDownLatch);
        for (int i = 0; i < THREADS; i++) {
            es.execute(task);
        }
        countDownLatch.await();
        es.shutdown();
        long timeExec = ChronoUnit.MILLIS.between(start, LocalDateTime.now());
        log.info("Total time execute: " + timeExec+ " ms");
        return "fine ^-^ " + timeExec + " ms";
    }

    class Task implements Runnable {
        private static final AtomicLong id = new AtomicLong(1);
        private CountDownLatch countDownLatch;

        public Task(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @SneakyThrows
        @Override
        public void run() {
            log.info("Start sending message, thread: " +  Thread.currentThread().getName());
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            for (int i = 0; i < 100000; i++) {
                var msg = new Message(id.getAndIncrement(), "some message");
                service.sendMessage(msg);
            }
            //Thread.sleep(10000);
            stopWatch.stop();
            log.info("Time execute: " + stopWatch.getTotalTimeMillis() + " ms");
            countDownLatch.countDown();
        }
    }
}
