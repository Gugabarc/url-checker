package br.com.mirandalabs.urlchecker.whitelist.checker.url;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString
@Builder
public class UrlChecker {

	@NotBlank
    private String client;

	@NotBlank
    private String url;

	@NotNull
    private Integer correlationId;

}