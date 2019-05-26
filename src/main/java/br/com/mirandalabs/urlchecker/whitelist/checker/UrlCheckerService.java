package br.com.mirandalabs.urlchecker.whitelist.checker;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mirandalabs.urlchecker.whitelist.regularexpression.RegularExpressionWhitelist;
import br.com.mirandalabs.urlchecker.whitelist.regularexpression.RegularExpressionWhitelistRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UrlCheckerService {

	@Autowired
	private RegularExpressionWhitelistRepository regexWhitelistRepository;

	public UrlCheckerResponse validateUrl(@Valid UrlCheckerRequest urlCheckerRequest) {

		if (regexWhitelistRepository.existsByClient(urlCheckerRequest.getClient())) {
			List<RegularExpressionWhitelist> regularExpressionWhitelists = regexWhitelistRepository.findByClientOrIsGlobal(urlCheckerRequest.getClient(), Boolean.TRUE);

			log.info("Applying {} regular expressions on [{}]", CollectionUtils.size(regularExpressionWhitelists), urlCheckerRequest);

			return checkUrlInRegexWhitelist(regularExpressionWhitelists, urlCheckerRequest);

		} else {
			List<RegularExpressionWhitelist> regularExpressionWhitelists = regexWhitelistRepository.findByIsGlobalTrue();

			log.info("Client not found. Applying {} global regular expressions on [{}]", CollectionUtils.size(regularExpressionWhitelists), urlCheckerRequest);
			
			return checkUrlInRegexWhitelist(regularExpressionWhitelists, urlCheckerRequest);
		}
	}

	private UrlCheckerResponse checkUrlInRegexWhitelist(List<RegularExpressionWhitelist> regularExpressionWhitelists, UrlCheckerRequest urlCheckerRequest) {

		for (RegularExpressionWhitelist regularExpressionWhitelist : regularExpressionWhitelists) {

			if (urlCheckerRequest.getUrl().matches(regularExpressionWhitelist.getRegularExpression())) {

				log.info("URL matches a regular expressions. [{}] [{}]", regularExpressionWhitelists,
						urlCheckerRequest);

				return UrlCheckerResponse.builder()
						.match(true)
						.regularExpression(regularExpressionWhitelist.getRegularExpression())
						.correlationId(urlCheckerRequest.getCorrelationId()).build();
			}

			log.info("URL doesn't match any regular expression [{}]", urlCheckerRequest);
		}

		return UrlCheckerResponse.builder()
				.match(false)
				.regularExpression(null)
				.correlationId(urlCheckerRequest.getCorrelationId())
				.build();
	}

}