package br.com.mirandalabs.urlchecker.whitelist.rule.evaluator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mirandalabs.urlchecker.whitelist.rule.RuleTypeEnum;

@Component
public class RuleEvaluatorFactory {
	
	@Autowired
    private DefaultRuleEvaluatorStrategy defaultRuleEvaluatorStrategy;
	
	@Autowired
    private List<RuleEvaluatorStrategy> ruleEvaluatorStrategies;
	
	public RuleEvaluatorStrategy getInstance(RuleTypeEnum ruleTypeEnum) {
		return ruleEvaluatorStrategies
				                .stream()
				                .filter(evaluator -> evaluator.supports(ruleTypeEnum))
				                .findFirst()
				                .orElse(defaultRuleEvaluatorStrategy);

    }

}
