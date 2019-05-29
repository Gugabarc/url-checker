package br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit;

import org.mapstruct.Mapper;

import br.com.mirandalabs.urlchecker.whitelist.checker.url.UrlChecker;
import br.com.mirandalabs.urlchecker.whitelist.checker.url.UrlCheckerResult;
import br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit.consumer.UrlCheckerRequestDTO;
import br.com.mirandalabs.urlchecker.whitelist.checker.url.rabbit.producer.UrlCheckerResponseDTO;

@Mapper(componentModel = "spring")
public interface UrlCheckerMapper {
	
	UrlChecker fromUrlCheckerRequestDTOToUrlChecker(UrlCheckerRequestDTO urlCheckerRequestDTO);
	
	UrlCheckerResponseDTO fromUrlCheckerResultToUrlCheckerResponseDTO(UrlCheckerResult urlCheckerResult);

}