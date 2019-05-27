package br.com.mirandalabs.urlchecker.whitelist.checker.url;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class UrlCheckerRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank
    private String client;

	@NotBlank
    private String url;

	@NotNull
    private Integer correlationId;

}