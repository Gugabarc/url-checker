package br.com.mirandalabs.urlchecker.template;

import javax.validation.Valid;

public abstract class TemplateService<T> {

    public abstract T save(@Valid T regexWhitelist);
    
}