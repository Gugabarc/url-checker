package br.com.mirandalabs.urlchecker.whitelist.checker.url;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@Builder
public class UrlCheckerResponse implements Serializable {

	private static final long serialVersionUID = 1L;

    private boolean match;

    private String regularExpression;

    private Integer correlationId;

}