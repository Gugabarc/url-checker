package br.com.mirandalabs.urlchecker.whitelist.rule.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import br.com.mirandalabs.urlchecker.whitelist.rule.RuleTypeEnum;
import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRule;

@Component
public class DefaultRuleValidatorStrategy implements RuleValidatorStrategy{

	@Override
	public boolean isValid(UrlWhitelistRule urlWhitelistRule) {
		return StringUtils.isNotBlank(urlWhitelistRule.getRule());
	}
	
	@Override
	public boolean supports(RuleTypeEnum ruleTypeEnum) {
		return false;
	}
}
