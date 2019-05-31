package br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.mirandalabs.urlchecker.whitelist.checker.url.UrlChecker;
import br.com.mirandalabs.urlchecker.whitelist.checker.url.UrlCheckerResult;
import br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit.consumer.UrlCheckerRequestDTO;
import br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit.producer.UrlCheckerResponseDTO;

@Mapper(componentModel = "spring")
public interface UrlCheckerMapper {
	
	UrlCheckerMapper INSTANCE = Mappers.getMapper(UrlCheckerMapper.class);
	
	UrlChecker fromUrlCheckerRequestDTOToUrlChecker(UrlCheckerRequestDTO urlCheckerRequestDTO);
	
	UrlCheckerResponseDTO fromUrlCheckerResultToUrlCheckerResponseDTO(UrlCheckerResult urlCheckerResult);

}