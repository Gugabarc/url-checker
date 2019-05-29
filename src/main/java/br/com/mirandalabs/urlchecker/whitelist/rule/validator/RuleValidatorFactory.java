package br.com.mirandalabs.urlchecker.whitelist.rule.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mirandalabs.urlchecker.whitelist.rule.RuleTypeEnum;

@Component
public class RuleValidatorFactory {
	
	@Autowired
    private DefaultRuleValidatorStrategy defaultRuleValidatorStrategy;
	
	@Autowired
    private List<RuleValidatorStrategy> ruleValidatorStrategies;
	
	public RuleValidatorStrategy getInstance(RuleTypeEnum ruleTypeEnum) {
		return ruleValidatorStrategies
				                .stream()
				                .filter(validator -> validator.supports(ruleTypeEnum))
				                .findFirst()
				                .orElse(defaultRuleValidatorStrategy);

    }

}
