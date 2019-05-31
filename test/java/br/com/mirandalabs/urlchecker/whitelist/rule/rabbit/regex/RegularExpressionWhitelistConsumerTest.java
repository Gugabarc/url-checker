package br.com.mirandalabs.urlchecker.whitelist.rule.rabbit.regex;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRule;
import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRuleService;
import br.com.mirandalabs.urlchecker.whitelist.rule.rabbit.UrlWhitelistRuleMapper;

@RunWith(MockitoJUnitRunner.class)
public class RegularExpressionWhitelistConsumerTest {
	
	@Mock
	private UrlWhitelistRuleService urlWhitelistRuleService;
	
	@Mock
	private UrlWhitelistRuleMapper urlWhitelistRuleMapper;

    @InjectMocks
    private RegexWhitelistRuleConsumer regexWhitelistConsumer;
    
    @Test
    public void whenReceiveMessage_thenSave(){
    	RegexWhitelistRuleRequestDTO requestDTO = RegexWhitelistRuleRequestDTO.builder()
																		.client("client one")
																		.rule("www.*")
																		.build();
    	
    	UrlWhitelistRule urlWhitelistRule = UrlWhitelistRule.builder()
															.client("client one")
															.rule("www.*")
															.build();

    	when(urlWhitelistRuleMapper.fromRegexToUrlWhitelistUrl(requestDTO)).thenReturn(urlWhitelistRule);
    	
        regexWhitelistConsumer.consume(requestDTO);
        
        verify(urlWhitelistRuleService).save(urlWhitelistRule);
    }
}