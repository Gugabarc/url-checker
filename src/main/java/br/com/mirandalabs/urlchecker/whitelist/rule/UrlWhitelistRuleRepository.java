package br.com.mirandalabs.urlchecker.whitelist.rule;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlWhitelistRuleRepository extends CrudRepository<UrlWhitelistRule, Integer> {

	boolean existsByClient(String client);

	List<UrlWhitelistRule> findByClientOrIsGlobal(String client, Boolean isGlobal);

	List<UrlWhitelistRule> findByIsGlobalTrue();
	
}