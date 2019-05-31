package br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit.producer;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString
@Builder
public class UrlCheckerResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

    private boolean match;

    private String rule;

    private Integer correlationId;

}