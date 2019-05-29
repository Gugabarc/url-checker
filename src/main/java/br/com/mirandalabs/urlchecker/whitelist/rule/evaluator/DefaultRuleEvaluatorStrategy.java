package br.com.mirandalabs.urlchecker.whitelist.rule.evaluator;

import org.springframework.stereotype.Component;

import br.com.mirandalabs.urlchecker.whitelist.rule.RuleTypeEnum;
import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRule;

@Component
public class DefaultRuleEvaluatorStrategy implements RuleEvaluatorStrategy{

	@Override
	public boolean match(String url, UrlWhitelistRule urlWhitelistRule) {
		return false;
	}
	
	@Override
	public boolean supports(RuleTypeEnum ruleTypeEnum) {
		return false;
	}

}
