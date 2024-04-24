package com.rzyplat.util;


import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class Messages {

	@Autowired
	private MessageSource source;
	
	public String get(String code) {
		return source.getMessage(code, null, null);
	}
	
	public String get(String code,Object [] args) {
		return source.getMessage(code, args, null);
	}
	
	public String get(String code,Object [] args,Locale locale) {
		return source.getMessage(code, args, locale);
	}

}

