package br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit.producer;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder @NoArgsConstructor @AllArgsConstructor
public class UrlCheckerResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

    private boolean match;

    private String rule;

    private Integer correlationId;

	@Override
	public String toString() {
		return "UrlCheckerResponse [match=" + match + ", rule=" + rule + ", "
				+ "correlationId=" + correlationId + "]";
	}

}