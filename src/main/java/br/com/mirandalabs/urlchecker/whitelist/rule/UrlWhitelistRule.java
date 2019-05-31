package br.com.mirandalabs.urlchecker.whitelist.rule;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "url_whitelist_rule")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString
@Builder
public class UrlWhitelistRule implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private String client;
    
    private Boolean isGlobal;

    @NotBlank
    private String rule;
    
    @Enumerated(EnumType.STRING)
    private RuleTypeEnum type;

}