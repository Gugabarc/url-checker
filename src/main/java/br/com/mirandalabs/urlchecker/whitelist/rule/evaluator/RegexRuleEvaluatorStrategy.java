package br.com.mirandalabs.urlchecker.whitelist.rule.evaluator;

import org.springframework.stereotype.Component;

import br.com.mirandalabs.urlchecker.whitelist.rule.RuleTypeEnum;
import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRule;

@Component
public class RegexRuleEvaluatorStrategy implements RuleEvaluatorStrategy{
	
	@Override
	public boolean match(String url, UrlWhitelistRule urlWhitelistRule) {
		return url.matches(urlWhitelistRule.getRule());
	}

	@Override
	public boolean supports(RuleTypeEnum ruleTypeEnum) {
		return RuleTypeEnum.REGULAR_EXPRESSION == ruleTypeEnum;
	}
}
