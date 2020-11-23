package com.yyicbc.utils.freemarker.template;
/**
 *@author vic fu
 */
public class TemplateException extends RuntimeException{

	private static final long serialVersionUID = 1L;


	public TemplateException(String message)
	{
		super(message);
	}

	public TemplateException(Exception e)
	{
		super(e.getMessage());
	}

}
