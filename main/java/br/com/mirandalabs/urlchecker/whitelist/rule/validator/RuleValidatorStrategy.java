package br.com.mirandalabs.urlchecker.whitelist.rule.validator;

import br.com.mirandalabs.urlchecker.whitelist.rule.RuleTypeEnum;
import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRule;

public interface RuleValidatorStrategy {
	
	public boolean isValid(UrlWhitelistRule urlWhitelistRule);
	
	public boolean supports(RuleTypeEnum ruleTypeEnum);

}
