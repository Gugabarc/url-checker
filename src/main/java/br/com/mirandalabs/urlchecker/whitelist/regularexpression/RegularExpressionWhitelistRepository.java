package br.com.mirandalabs.urlchecker.whitelist.regularexpression;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RegularExpressionWhitelistRepository extends CrudRepository<RegularExpressionWhitelist, Integer> {

	boolean existsByClient(String client);

	List<RegularExpressionWhitelist> findByClientOrIsGlobal(String client, Boolean true1);

	List<RegularExpressionWhitelist> findByIsGlobalTrue();

}