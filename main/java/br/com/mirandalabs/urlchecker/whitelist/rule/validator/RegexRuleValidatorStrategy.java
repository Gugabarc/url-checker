package br.com.mirandalabs.urlchecker.whitelist.rule.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import br.com.mirandalabs.urlchecker.whitelist.rule.RuleTypeEnum;
import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRule;

@Component
public class RegexRuleValidatorStrategy implements RuleValidatorStrategy{

	@Override
	public boolean isValid(UrlWhitelistRule urlWhitelistRule) {
		try {
			Pattern.compile(urlWhitelistRule.getRule()).pattern();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public boolean supports(RuleTypeEnum ruleTypeEnum) {
		return RuleTypeEnum.REGULAR_EXPRESSION == ruleTypeEnum;
	}
}
