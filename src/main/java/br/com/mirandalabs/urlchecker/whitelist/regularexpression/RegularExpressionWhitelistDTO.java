package br.com.mirandalabs.urlchecker.whitelist.regularexpression;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class RegularExpressionWhitelistDTO implements Serializable {

	private static final long serialVersionUID = 1L;

    @JsonProperty("client")
    private String client;

    @NotBlank
    @JsonProperty("regex")
    private String regularExpression;

}