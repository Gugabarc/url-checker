package br.com.mirandalabs.urlchecker.whitelist.checker.url;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UrlChecker {

	@NotBlank
    private String client;

	@NotBlank
    private String url;

	@NotNull
    private Integer correlationId;

	@Override
	public String toString() {
		return "UrlCheckerRequest [client=" + client + ", url=" + url + ", correlationId=" + correlationId + "]";
	}
	
}