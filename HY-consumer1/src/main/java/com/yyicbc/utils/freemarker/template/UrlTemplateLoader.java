package com.yyicbc.utils.freemarker.template;

import java.net.URL;

/**
 *@author vic fu
 */
public class UrlTemplateLoader extends freemarker.cache.URLTemplateLoader {

	private String baseUrl;

	public UrlTemplateLoader(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}

	@Override
	protected URL getURL(String arg){
	    URL url = null;
	    try {
	    	url = this.getClass().getClassLoader().getResource(baseUrl+arg);
	    } catch (Exception s) {
	        s.printStackTrace();
	    }
	    return url;
	}

	public String getBaseUrl() {
	    return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
	    this.baseUrl = baseUrl;
	}

}
