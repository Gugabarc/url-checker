package br.com.mirandalabs.urlchecker.whitelist.regularexpression;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "regular_expression_whitelist")
@Getter @Setter @NoArgsConstructor @ToString
public class RegularExpressionWhitelist implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private String client;
    
    private Boolean isGlobal;

    @NotBlank
    private String regularExpression;

}