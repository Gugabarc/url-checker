package br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit.consumer;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@Builder
public class UrlCheckerRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@JsonProperty("client")
    private String client;

	@NotBlank
	@JsonProperty("url")
    private String url;

	@NotNull
	@JsonProperty("correlationId")
    private Integer correlationId;

}