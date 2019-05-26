package br.com.mirandalabs.urlchecker;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RabbitConsumer {

    @RabbitListener(queues = "${test.queue}")
    public void consume(String abc) {

        try {
            log.info("Received message from queue with content: {}", abc);

        } catch (Exception e) {
            log.error("An error has been occurred", e);
        }
    }
}
