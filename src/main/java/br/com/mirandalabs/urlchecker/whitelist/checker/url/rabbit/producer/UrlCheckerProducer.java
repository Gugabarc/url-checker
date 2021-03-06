package br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit.producer;

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

    public void send(UrlCheckerResponseDTO urlCheckerResponseDTO) throws Exception{
    	try {
    		String responseAsStringJson = convertToStringJson(urlCheckerResponseDTO);

    		log.info("Sending to [{}] with routing key [{}]. Content: [{}]", rabbitConfig.getResponseExchange()
    															, rabbitConfig.getResponseRoutingKey()
    															, responseAsStringJson);
    		
			rabbitTemplate.convertAndSend(rabbitConfig.getResponseExchange()
											, rabbitConfig.getResponseRoutingKey()
											, responseAsStringJson);
		
    	} catch (Exception e) {
			log.error("Error sending to [{}]. Aborting operation.", rabbitConfig.getResponseExchange(), e);
			throw e;
		}
    }

	private String convertToStringJson(UrlCheckerResponseDTO urlCheckerResponseDTO) throws Exception {
		try {
			return objectMapper.writeValueAsString(urlCheckerResponseDTO);
		} catch (Exception e) {
			log.error("Error converting object to string json. Object: [{}]. Aborting operation.", urlCheckerResponseDTO, e);
			throw e;
		}
	}
}