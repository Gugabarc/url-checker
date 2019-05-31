package br.com.mirandalabs.urlchecker.whitelist.rule.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import br.com.mirandalabs.urlchecker.whitelist.rule.RuleTypeEnum;
import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRule;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RegexRuleValidatorStrategy implements RuleValidatorStrategy{

	@Override
	public boolean isValid(UrlWhitelistRule urlWhitelistRule) {
		try {
			Pattern.compile(urlWhitelistRule.getRule()).pattern();
			return true;
		} catch (Exception e) {
			log.warn("Error when trying to compile expression language [{}]", urlWhitelistRule);
			return false;
		}
	}
	
	@Override
	public boolean supports(RuleTypeEnum ruleTypeEnum) {
		return RuleTypeEnum.REGULAR_EXPRESSION == ruleTypeEnum;
	}
}
