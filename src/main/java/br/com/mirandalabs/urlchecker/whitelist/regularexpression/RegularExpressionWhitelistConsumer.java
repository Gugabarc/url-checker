package br.com.mirandalabs.urlchecker.whitelist.regularexpression;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RegularExpressionWhitelistConsumer {
	
	@Autowired
	private RegularExpressionWhitelistMapper regexMapper;
	
	@Autowired
	private RegularExpressionWhitelistService regexService;

	/**
	 * 
	 * @param regexWhitelistDTO
	 */
    @RabbitListener(queues = "${insertion.queue}")
    public void consume(RegularExpressionWhitelistDTO regexWhitelistDTO) {
        try {
        	log.info("Received message from queue with content [{}]", regexWhitelistDTO);
        	
        	RegularExpressionWhitelist regexWhitelist = regexMapper.fromDtoToRegexWhitelist(regexWhitelistDTO);
        	
        	log.info("Saving regular expression in whitelist [{}]", regexWhitelist);
        	
        	regexService.save(regexWhitelist);

        } catch (Exception e) {
            log.error("An error has been occurred", e);
        }
    }
}
