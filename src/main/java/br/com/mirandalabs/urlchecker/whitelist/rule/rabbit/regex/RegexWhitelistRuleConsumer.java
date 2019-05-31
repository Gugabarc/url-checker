package br.com.mirandalabs.urlchecker.whitelist.rule.rabbit.regex;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRule;
import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRuleService;
import br.com.mirandalabs.urlchecker.whitelist.rule.rabbit.UrlWhitelistRuleMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RegexWhitelistRuleConsumer {
	
	@Autowired
	private UrlWhitelistRuleMapper urlWhitelistRuleMapper;
	
	@Autowired
	private UrlWhitelistRuleService urlWhitelistRuleService;

    @RabbitListener(queues = "${insertion.queue}")
    public void consume(RegexWhitelistRuleRequestDTO regexWhitelistDTO) {
        try {
        	log.info("Received regex rule from queue with content [{}]", regexWhitelistDTO);
        	
        	UrlWhitelistRule regexWhitelist = urlWhitelistRuleMapper.fromRegexToUrlWhitelistUrl(regexWhitelistDTO);
        	
        	log.info("Saving regular expression in whitelist [{}]", regexWhitelist);
        	
        	urlWhitelistRuleService.save(regexWhitelist);

        } catch (Exception e) {
            log.error("An error has been occurred. Aborting operation.", e);
            throw e;
        }
    }
}
