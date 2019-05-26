package br.com.mirandalabs.urlchecker.whitelist.regularexpression;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegularExpressionWhitelistMapper {
	
	RegularExpressionWhitelist fromDtoToRegexWhitelist(RegularExpressionWhitelistDTO regex);

}