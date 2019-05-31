package br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mirandalabs.urlchecker.whitelist.checker.url.UrlChecker;
import br.com.mirandalabs.urlchecker.whitelist.checker.url.UrlCheckerResult;
import br.com.mirandalabs.urlchecker.whitelist.checker.url.UrlCheckerService;
import br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit.UrlCheckerMapper;
import br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit.producer.UrlCheckerProducer;
import br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit.producer.UrlCheckerResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UrlCheckerConsumer {
	
	@Autowired
	private UrlCheckerService urlCheckerService;
	
	@Autowired
	private UrlCheckerProducer urlCheckerProducer;
	
	@Autowired
	private UrlCheckerMapper urlCheckerMapper;

	@RabbitListener(queues = "${validation.queue}")
    public void consume(UrlCheckerRequestDTO urlCheckerRequestDTO) throws Exception {
        try {
        	log.info("Received message from queue with content [{}]. Mapping to business object...", urlCheckerRequestDTO);
        	
        	UrlChecker urlChecker = urlCheckerMapper.fromUrlCheckerRequestDTOToUrlChecker(urlCheckerRequestDTO);
        	
        	log.info("Mapped from [{}] to [{}]", urlCheckerRequestDTO, urlChecker);
        	
        	UrlCheckerResult urlCheckerResult = urlCheckerService.validateUrl(urlChecker);
        	
        	log.info("Validation of [{}] returned [{}]", urlChecker, urlCheckerResult);

        	UrlCheckerResponseDTO urlCheckerResponseDTO = urlCheckerMapper.fromUrlCheckerResultToUrlCheckerResponseDTO(urlCheckerResult);
        	
        	log.info("Mapped from [{}] to [{}]", urlCheckerResult, urlCheckerResponseDTO);
        	
        	urlCheckerProducer.send(urlCheckerResponseDTO);
        	
        	log.info("Finished process for correlationId [{}]", urlCheckerRequestDTO.getCorrelationId());

        } catch (Exception e) {
            log.error("An error has been occurred. Aborting processo. Message: [{}]", urlCheckerRequestDTO, e);
        }
    }
}
