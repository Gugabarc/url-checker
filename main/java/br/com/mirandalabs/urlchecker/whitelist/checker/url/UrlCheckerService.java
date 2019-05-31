package br.com.mirandalabs.urlchecker.whitelist.checker.url;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRule;
import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRuleService;
import br.com.mirandalabs.urlchecker.whitelist.rule.evaluator.RuleEvaluatorFactory;
import br.com.mirandalabs.urlchecker.whitelist.rule.evaluator.RuleEvaluatorStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Validated
public class UrlCheckerService {

	@Autowired
	private UrlWhitelistRuleService urlWhitelistService;
	
	@Autowired
	private RuleEvaluatorFactory ruleEvaluatorFactory;

	public UrlCheckerResult validateUrl(@NotNull @Valid UrlChecker urlChecker) {
		List<UrlWhitelistRule> urlWhitelistRules = urlWhitelistService.findByClient(urlChecker.getClient());

		log.info("Applying {} rules on [{}]", CollectionUtils.size(urlWhitelistRules), urlChecker);

		return checkUrlInWhitelist(urlWhitelistRules, urlChecker);
	}

	private UrlCheckerResult checkUrlInWhitelist(List<UrlWhitelistRule> urlWhitelistRules, UrlChecker urlChecker) {

		for (UrlWhitelistRule urlWhitelistRule : CollectionUtils.emptyIfNull(urlWhitelistRules)) {
			RuleEvaluatorStrategy ruleEvaluatorStrategy = ruleEvaluatorFactory.getInstance(urlWhitelistRule.getType());
			
			if (ruleEvaluatorStrategy.match(urlChecker.getUrl(), urlWhitelistRule)) {

				log.info("URL matches a rule. [{}] [{}]", urlWhitelistRules, urlChecker);

				return UrlCheckerResult.builder()
											.match(true)
											.rule(urlWhitelistRule)
											.correlationId(urlChecker.getCorrelationId()).build();
			}
		}
		
		log.info("URL doesn't match any rule [{}]", urlChecker);

		return UrlCheckerResult.builder()
									.match(false)
									.rule(null)
									.correlationId(urlChecker.getCorrelationId())
									.build();
	}

}