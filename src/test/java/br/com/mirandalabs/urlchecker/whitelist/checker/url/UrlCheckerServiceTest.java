package br.com.mirandalabs.urlchecker.whitelist.checker.url;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit.consumer.UrlCheckerRequestDTO;
import br.com.mirandalabs.urlchecker.whitelist.rule.RuleTypeEnum;
import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRule;
import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRuleService;
import br.com.mirandalabs.urlchecker.whitelist.rule.evaluator.RuleEvaluatorFactory;
import br.com.mirandalabs.urlchecker.whitelist.rule.evaluator.RuleEvaluatorStrategy;

@RunWith(MockitoJUnitRunner.class)
public class UrlCheckerServiceTest {

	@Mock
    private UrlWhitelistRuleService urlWhitelistRuleService;
	
	@Mock
	private RuleEvaluatorFactory ruleEvaluatorFactory;
	
	@Mock
	private RuleEvaluatorStrategy ruleEvaluatorStrategy;
	
    @InjectMocks
    private UrlCheckerService urlCheckerService;

    private List<UrlWhitelistRule> globalRegexWhitelists;
    
    private UrlWhitelistRule ruleOne;

    @Before
    public void setup() {
    	when(ruleEvaluatorFactory.getInstance(any(RuleTypeEnum.class))).thenReturn(ruleEvaluatorStrategy);
    	when(ruleEvaluatorStrategy.match(anyString(), any(UrlWhitelistRule.class))).thenReturn(true);
    	
		ruleOne = UrlWhitelistRule.builder()
									.client(null)
									.rule("http.*")
									.type(RuleTypeEnum.REGULAR_EXPRESSION)
									.build();
        
    	globalRegexWhitelists = new ArrayList<>();
        globalRegexWhitelists.add(ruleOne);
        
        when(urlWhitelistRuleService.findByClient(anyString())).thenReturn(globalRegexWhitelists);
    }
    

    @Test
    public void whenRulesIsNull_thenReturnMatchIsFalse(){
    	when(urlWhitelistRuleService.findByClient(anyString())).thenReturn(null);
    	
    	UrlCheckerRequestDTO urlCheckerRequestDTO = UrlCheckerRequestDTO.builder()
    																	.client("")
    																	.correlationId(123)
    																	.build();
    	
		UrlCheckerResult result = urlCheckerService.validateUrl(urlCheckerRequestDTO);
		
		UrlCheckerResult expected = UrlCheckerResult.builder()
											.match(false)
											.rule(null)
											.correlationId(123)
											.build();
		
		assertThat(result).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void whenRulesIsEmpty_thenReturnMatchIsFalse(){
    	when(urlWhitelistRuleService.findByClient(anyString())).thenReturn(new ArrayList<>());
    	
    	UrlCheckerRequestDTO urlCheckerRequestDTO = UrlCheckerRequestDTO.builder()
    																	.client("")
    																	.correlationId(123)
    																	.build();
    	
		UrlCheckerResult result = urlCheckerService.validateUrl(urlCheckerRequestDTO);
		
		UrlCheckerResult expected = UrlCheckerResult.builder()
											.match(false)
											.rule(null)
											.correlationId(123)
											.build();
		
		assertThat(result).isEqualToComparingFieldByField(expected);
    }
    
    @Test
    public void whenRuleMatch_thenReturnMatchIsTrue(){
    	UrlCheckerRequestDTO urlCheckerRequestDTO = UrlCheckerRequestDTO.builder()
    																	.client("abc")
    																	.url("www.miranda.com.br")
    																	.correlationId(123)
    																	.build();
    	
		UrlCheckerResult result = urlCheckerService.validateUrl(urlCheckerRequestDTO);
		
		UrlCheckerResult expected = UrlCheckerResult.builder()
											.match(true)
											.rule(ruleOne)
											.correlationId(123)
											.build();
		
		assertThat(result).isEqualToComparingFieldByField(expected);
    }
    
    @Test
    public void whenRuleDontMatch_thenReturnMatchIsFalse(){
    	when(ruleEvaluatorStrategy.match(anyString(), any(UrlWhitelistRule.class))).thenReturn(false);
    	
    	UrlCheckerRequestDTO urlCheckerRequestDTO = UrlCheckerRequestDTO.builder()
    																	.client("abc")
    																	.url("www.miranda.com.br")
    																	.correlationId(123)
    																	.build();
    	
		UrlCheckerResult result = urlCheckerService.validateUrl(urlCheckerRequestDTO);
		
		UrlCheckerResult expected = UrlCheckerResult.builder()
											.match(false)
											.rule(null)
											.correlationId(123)
											.build();
		
		assertThat(result).isEqualToComparingFieldByField(expected);
    }

}