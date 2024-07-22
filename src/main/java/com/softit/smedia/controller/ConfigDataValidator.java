package com.softit.smedia.controller;

import com.softit.smedia.model.ConfigData;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ConfigDataValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return ConfigData.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }

	/*	public void validate(Object target, Errors errors) {
			ConfigData form = (ConfigData)target;
			
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "smsTimer",
					"","Le timer est requis !");		
			
			if(!StringUtils.isNumeric(""+form.getSmsTimer())){
				errors.rejectValue( "smsTimer", "","Le timer doit etre un entier !");
			}
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPosition",
					"","La position du client est requise !");
			
			if(!StringUtils.isNumeric(""+form.getCustomerPosition())){
				errors.rejectValue( "customerPosition",
						"","La position doit etre un entier !");
			}
				
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "smsSourcePhone",
					"","Le numero emetteur est requis !");
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "smsTemplateEn",
					"","Le template du SMS est requis !");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "smsTemplateFr",
					"","Le template du SMS est requis !");
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apiGatewayBaseUrl",
					"", "L'url de l'API Gateway est requis !");		
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apiGatewayToken",
					"","Le token est requis !");
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oracleHost",
					"","Le host orchestra oracle est requis !");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oraclePort",
					"","Le port orchestra oracle est requis !");
			
			if(!StringUtils.isNumeric(""+form.getOraclePort())){
				errors.rejectValue("oraclePort", 
						"","Le port orchestra oracle doit etre un entier !");
			}
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oracleSid",
					"","L'ID de la connexion orchestra oracle est requis !");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oracleUsername",
					"","Le username orchestra oracle est requis !");
			
					
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerdbHost",
					"","Le host de la base de donnees oracle client est requis !");
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerdbPort",
					"","Le port de la base de donnees oracle client est requis !");
			
			if(!StringUtils.isNumeric(""+form.getCustomerdbPort())){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerdbPort",
						"","Le port de la base de donnees oracle client doit etre un entier !");
			}
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerdbSid",
					"","L'ID de la connexion a la base de donnees oracle client est requis !");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerdbUsername",
					"","Le username de la base de donnees oracle client est requis !");
			
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "smppHost",
					"","Le host du SMPP est requis !");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "smppPort",
					"","Le port du SMPP est requis !");
			
			if(!StringUtils.isNumeric(""+form.getSmppPort())){
				errors.rejectValue("smppPort", 
						"","Le port du SMPP doit etre un entier !");
			}
			
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "smppUsername",
					"","Le username du SMPP est requis !");
			
						
		}	
		*/

}
