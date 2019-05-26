package br.com.mirandalabs.urlchecker.whitelist.checker;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UrlCheckerConsumer {
	
	@Autowired
	private UrlCheckerService urlCheckerService;

	/**
	 * 
	 * @param regexWhitelistDTO
	 */
	@RabbitListener(queues = "${validation.queue}")
    public void consume(UrlCheckerRequest urlCheckerRequest) {
        try {
        	log.info("Received message from queue with content [{}]", urlCheckerRequest);
        	
        	urlCheckerService.validateUrl(urlCheckerRequest);

        } catch (Exception e) {
            log.error("An error has been occurred", e);
        }
    }
}
