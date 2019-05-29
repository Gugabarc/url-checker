package br.com.mirandalabs.urlchecker.whitelist.checker.url;

import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRule;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class UrlCheckerResult  {

	private boolean match;

    private UrlWhitelistRule rule;

    private Integer correlationId;

	@Override
	public String toString() {
		return "UrlCheckerResponse [match=" + match + ", rule=" + rule + ", "
				+ "correlationId=" + correlationId + "]";
	}

}