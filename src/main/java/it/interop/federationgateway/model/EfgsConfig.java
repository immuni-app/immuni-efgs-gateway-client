package it.interop.federationgateway.model;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class EfgsConfig implements Serializable {
	private static final long serialVersionUID = 4109344991600622228L;
	private static String COUNTRY_REG_EXP = "[A-Z]{2}";

	private List<String> skippedCountries; 

	
	public static boolean isValidCountries(List<String> countries) {
		if (countries != null) {
			for (String country:countries) {
				if (!isValidCountry(country)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean isValidCountry(String country) {
		boolean esito = !StringUtils.isEmpty(country);
		if (esito) {
			Pattern p = Pattern.compile(COUNTRY_REG_EXP);
			esito = p.matcher(country).matches();
		}
		return esito;
	}

	
}
