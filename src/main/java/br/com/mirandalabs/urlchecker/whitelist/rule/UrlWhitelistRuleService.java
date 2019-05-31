package br.com.mirandalabs.urlchecker.whitelist.rule;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mirandalabs.urlchecker.template.CrudTemplateService;
import br.com.mirandalabs.urlchecker.whitelist.rule.validator.RuleValidatorFactory;
import br.com.mirandalabs.urlchecker.whitelist.rule.validator.RuleValidatorStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UrlWhitelistRuleService implements CrudTemplateService<UrlWhitelistRule>{

	@Autowired
    private UrlWhitelistRuleRepository urlWhitelistRuleRepository;
	
	@Autowired
	private RuleValidatorFactory ruleValidatorFactory;

	@Override
    public UrlWhitelistRule save(UrlWhitelistRule urlWhitelistRule) {
        try {
        	RuleValidatorStrategy ruleValidator = ruleValidatorFactory.getInstance(urlWhitelistRule.getType());
        	
            if (!ruleValidator.isValid(urlWhitelistRule)) { 
            	log.info("Invalid rule for url whitelist [{}]. Operation aborted.", urlWhitelistRule);
            	return null;
            }
            
            log.info("Rule is valid. Saving. [{}]", urlWhitelistRule);
            
            setIsGlobalAttribute(urlWhitelistRule);
            
			UrlWhitelistRule savedUrlWhitelistRule = this.urlWhitelistRuleRepository.save(urlWhitelistRule);
            
            log.info("Saved rule. [{}]", savedUrlWhitelistRule);
            
            return savedUrlWhitelistRule;

        } catch (Exception e) {
            log.error("An error occurred when trying to save url whitelist rule [{}].", urlWhitelistRule);
        }
        
		return null;
    }
	
	public List<UrlWhitelistRule> findByClient(String client) {
		log.info("Searching rules by client [{}]", client);
		
		if (urlWhitelistRuleRepository.existsByClient(client)) {
			log.info("Client [{}] found", client);
			return urlWhitelistRuleRepository.findByClientOrIsGlobal(client, Boolean.TRUE);
		}

		log.info("Client [{}] not found.", client);
		
		return urlWhitelistRuleRepository.findByIsGlobalTrue();
	}

	private void setIsGlobalAttribute(UrlWhitelistRule regexWhitelistRule) {
		regexWhitelistRule.setIsGlobal(StringUtils.isEmpty(regexWhitelistRule.getClient()));
	}

}