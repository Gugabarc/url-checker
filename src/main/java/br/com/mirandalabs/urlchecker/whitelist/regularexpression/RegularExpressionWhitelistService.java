package br.com.mirandalabs.urlchecker.whitelist.regularexpression;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mirandalabs.urlchecker.template.TemplateService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class RegularExpressionWhitelistService extends TemplateService<RegularExpressionWhitelist>{

	@Autowired
    private RegularExpressionWhitelistRepository regexWhitelistRepository;

	/**
	 * 
	 * @param regexWhitelist
	 */
	@Override
    public RegularExpressionWhitelist save(@Valid RegularExpressionWhitelist regexWhitelist) {
        try {
            validatePattern(regexWhitelist);
            
            setIsGlobalAttribute(regexWhitelist);

            log.info("Saving regular expression. [{}]", regexWhitelist);
            
			RegularExpressionWhitelist savedRegexWhitelist = this.regexWhitelistRepository.save(regexWhitelist);
            
            log.info("Saved regular expression. [{}]", savedRegexWhitelist);
            
            return savedRegexWhitelist;

        } catch (PatternSyntaxException e) {
            log.error("Regex pattern [{}] is invalid.", regexWhitelist.getRegularExpression());
        
        } catch (Exception e) {
            log.error("An error occurred when trying to save regex [{}].", regexWhitelist);
        }
        
		return null;
    }

    /**
     * 
     * @param regexWhitelist
     */
	private void setIsGlobalAttribute(RegularExpressionWhitelist regexWhitelist) {
		if(StringUtils.isEmpty(regexWhitelist.getClient())) {
			regexWhitelist.setIsGlobal(true);
		} else {
			regexWhitelist.setIsGlobal(false);
		}
	}

    /**
     * 
     * @param regexWhitelist
     * @throws PatternSyntaxException
     */
	private void validatePattern(RegularExpressionWhitelist regexWhitelist) throws PatternSyntaxException{
		Pattern.compile(regexWhitelist.getRegularExpression()).pattern();
	}
}