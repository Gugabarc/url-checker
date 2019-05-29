package br.com.mirandalabs.urlchecker.whitelist.rule.rabbit;

import org.mapstruct.Mapper;

import br.com.mirandalabs.urlchecker.whitelist.rule.UrlWhitelistRule;
import br.com.mirandalabs.urlchecker.whitelist.rule.rabbit.regex.RegexWhitelistRuleRequestDTO;

@Mapper(componentModel = "spring")
public interface UrlWhitelistRuleMapper {
	
	UrlWhitelistRule fromRegexToUrlWhitelistUrl(RegexWhitelistRuleRequestDTO regexRequestDTO);

}