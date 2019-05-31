package br.com.mirandalabs.urlchecker.whitelist.rule.rabbit.regex;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.mirandalabs.urlchecker.whitelist.rule.RuleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class RegexWhitelistRuleRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

    @JsonProperty("client")
    private String client;

    @NotBlank
    @JsonProperty("regex")
    private String rule;
    
    @JsonIgnore
    @Builder.Default
    private RuleTypeEnum type = RuleTypeEnum.REGULAR_EXPRESSION;

	@Override
	public String toString() {
		return "RegularExpressionWhitelistDTO [client=" + client + ", rule=" + rule + ", type=" + type + "]";
	}

}