package br.com.mirandalabs.urlchecker.whitelist.rule.evaluator;

import br.com.mirandalabs.urlchecker.whitelist.rule.RuleTypeEnum;
import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRule;

public interface RuleEvaluatorStrategy {
	
	public boolean match(String url, UrlWhitelistRule urlWhitelistRule);
	
	public boolean supports(RuleTypeEnum ruleTypeEnum);

}
