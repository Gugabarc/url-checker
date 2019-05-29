package br.com.mirandalabs.urlchecker.template;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

@Validated
public interface CrudTemplateService<T> {

    T save(@NotNull @Valid T regexWhitelist);
    
}