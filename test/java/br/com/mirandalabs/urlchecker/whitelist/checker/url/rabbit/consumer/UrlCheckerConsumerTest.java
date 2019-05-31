package br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit.consumer;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.mirandalabs.urlchecker.whitelist.checker.url.UrlChecker;
import br.com.mirandalabs.urlchecker.whitelist.checker.url.UrlCheckerResult;
import br.com.mirandalabs.urlchecker.whitelist.checker.url.UrlCheckerService;
import br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit.UrlCheckerMapper;
import br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit.producer.UrlCheckerProducer;
import br.com.mirandalabs.urlchecker.whitelist.rule.RuleTypeEnum;
import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRule;

//@RunWith(MockitoJUnitRunner.class)
public class UrlCheckerConsumerTest {
	
//	@Mock
//	private UrlCheckerService urlCheckerService;
//	
//	@Mock
//	private UrlCheckerProducer urlCheckerProducer;
//	
//	@Mock
//	private UrlCheckerMapper urlCheckerMapper;
//	
//	@InjectMocks
//	private UrlCheckerConsumer urlCheckerConsumer;
//	
//	@Test
//	public void wheReceiveMessage_thenSendToProducer() {
//		UrlCheckerRequestDTO urlCheckerRequestDTO = UrlCheckerRequestDTO.builder()
//																		.client("abc")
//																		.correlationId(123)
//																		.url("www.miranda.com")
//																		.build();
//		
//		UrlChecker urlChecker = UrlChecker.builder()
//											.client("abc")
//											.correlationId(123)
//											.url("www.miranda.com")
//											.build();
//		
//		when(urlCheckerMapper.fromUrlCheckerRequestDTOToUrlChecker(urlCheckerRequestDTO)).thenReturn(urlChecker);
//		
//		UrlWhitelistRule rule = UrlWhitelistRule.builder()
//													.type(RuleTypeEnum.REGULAR_EXPRESSION)
//													.rule("*.")
//													.client("abc")
//													.build();
//		
//		UrlCheckerResult urlCheckerResult = UrlCheckerResult.builder()
//																.match(true)
//																.correlationId(123)
//																.rule(rule)
//																.build();
//		
//		when(urlCheckerService.validateUrl(urlChecker)).thenReturn(urlCheckerResult);
//		
//		
//		
//
//		
//		when(urlCheckerMapper.fromUrlCheckerResultToUrlCheckerResponseDTO(urlCheckerResult));
//		
//		
//		
//		urlCheckerConsumer.consume(urlCheckerRequestDTO);
//	}

}
