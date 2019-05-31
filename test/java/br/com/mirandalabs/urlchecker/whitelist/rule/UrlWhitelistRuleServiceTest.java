package br.com.mirandalabs.urlchecker.whitelist.rule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.mirandalabs.urlchecker.whitelist.rule.validator.RuleValidatorFactory;
import br.com.mirandalabs.urlchecker.whitelist.rule.validator.RuleValidatorStrategy;

@RunWith(MockitoJUnitRunner.class)
public class UrlWhitelistRuleServiceTest {

	@Mock
    private UrlWhitelistRuleRepository urlWhitelistRuleRepository;
	
	@Mock
	private RuleValidatorFactory ruleValidatorFactory;
	
	@Mock
	private RuleValidatorStrategy ruleValidatorStrategy;
	
    @InjectMocks
    private UrlWhitelistRuleService urlWhitelistRuleService;
    
    @Before
    public void setup() {
    	when(ruleValidatorFactory.getInstance(any(RuleTypeEnum.class))).thenReturn(ruleValidatorStrategy);
    	when(ruleValidatorStrategy.isValid(any(UrlWhitelistRule.class))).thenReturn(true);
    }
    
    @Test
    public void whenRuleIsInvalid_thenReturnNullAndDoNothing() {
    	when(ruleValidatorStrategy.isValid(any(UrlWhitelistRule.class))).thenReturn(false);
    	
    	UrlWhitelistRule urlWhitelist = UrlWhitelistRule.builder()
															.rule("*.")
															.type(RuleTypeEnum.REGULAR_EXPRESSION)
															.build();
    	
    	UrlWhitelistRule result = urlWhitelistRuleService.save(urlWhitelist);
    	
    	assertThat(result).isNull();
    	verify(urlWhitelistRuleRepository, times(0)).save(any());
    }
    
    @Test
    public void whenRuleIsValidAndClientIsNull_thenSaveAndReturnSavedObject() {
    	UrlWhitelistRule urlWhitelist = UrlWhitelistRule.builder()
															.rule(".*")
															.type(RuleTypeEnum.REGULAR_EXPRESSION)
															.build();
    	
    	when(urlWhitelistRuleRepository.save(any(UrlWhitelistRule.class))).thenReturn(urlWhitelist);
    	
    	UrlWhitelistRule expected = UrlWhitelistRule.builder()
													.client(null)
									    			.rule(".*")
									    			.type(RuleTypeEnum.REGULAR_EXPRESSION)
									    			.isGlobal(true)
									    			.build();
    	
    	UrlWhitelistRule savedRegexWhitelist = urlWhitelistRuleService.save(urlWhitelist);
    	
    	assertThat(savedRegexWhitelist).isEqualToComparingFieldByField(expected);
    }
    
    @Test
    public void whenRuleIsValidAndHasClient_thenSaveAndReturnSavedObject() {
    	UrlWhitelistRule urlWhitelist = UrlWhitelistRule.builder()
															.rule(".*")
															.client("client")
															.type(RuleTypeEnum.REGULAR_EXPRESSION)
															.build();
    	
    	when(urlWhitelistRuleRepository.save(any(UrlWhitelistRule.class))).thenReturn(urlWhitelist);
    	
    	UrlWhitelistRule expected = UrlWhitelistRule.builder()
													.client("client")
									    			.rule(".*")
									    			.type(RuleTypeEnum.REGULAR_EXPRESSION)
									    			.isGlobal(false)
									    			.build();
    	
    	UrlWhitelistRule savedRegexWhitelist = urlWhitelistRuleService.save(urlWhitelist);
    	
    	assertThat(savedRegexWhitelist).isEqualToComparingFieldByField(expected);
    }

}