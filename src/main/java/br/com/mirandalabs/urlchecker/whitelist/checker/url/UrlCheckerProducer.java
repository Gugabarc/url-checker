package br.com.mirandalabs.urlchecker.whitelist.checker.url;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mirandalabs.urlchecker.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UrlCheckerProducer {

	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	@Autowired
    private RabbitConfig rabbitConfig;
	
	@Autowired
    private ObjectMapper objectMapper;

    public void send(UrlCheckerResponse urlCheckerResponse){
    	try {
    		log.info("Sending [{}] to [{}] with routing key [{}]", urlCheckerResponse, rabbitConfig.getResponseExchange(), rabbitConfig.getResponseRoutingKey());
    		
			rabbitTemplate.convertAndSend(rabbitConfig.getResponseExchange(), rabbitConfig.getResponseRoutingKey(), objectMapper.writeValueAsString(urlCheckerResponse));
		} catch (Exception e) {
			log.error("Error sending to [{}]", rabbitConfig.getResponseExchange(), e);
		}
    }
}