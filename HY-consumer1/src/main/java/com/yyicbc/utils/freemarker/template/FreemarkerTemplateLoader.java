package com.yyicbc.utils.freemarker.template;

import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Map;

/**
 *@author vic fu
 */
@Component
public class FreemarkerTemplateLoader implements TemplateLoader{

	private static String DEFAULT_ENCODING = "utf-8";

	static final Logger logger = LoggerFactory.getLogger(FreemarkerTemplateLoader.class);


	private Configuration tplConfig;

	public FreemarkerTemplateLoader()
	{
		tplConfig = new Configuration();
		tplConfig.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		tplConfig.setObjectWrapper(ObjectWrapper.DEFAULT_WRAPPER);
		tplConfig.setTemplateLoader(new UrlTemplateLoader("statics/"));
		tplConfig.setTemplateUpdateDelay(0);
		tplConfig.setDefaultEncoding(DEFAULT_ENCODING);
		tplConfig.setLocale(new java.util.Locale("zh_CN"));
		tplConfig.setNumberFormat("0.##");
		tplConfig.setClassicCompatible(true);
//		tplConfig.setCacheStorage(new freemarker.cache.MruCacheStorage(20,250));
//		tplConfig.setSharedVariable("nds", new NumberDateDisplay());
	}

	@Override
	public void setCharset(String charset) {
		tplConfig.setDefaultEncoding(charset);
	}

	@Override
	public String render(Map<String, Object> data, String template) {
		try
		{
			StringWriter writer = new StringWriter();
			freemarker.template.Template tl = tplConfig.getTemplate(template);//,  tplConfig.getLocale());
			SimpleHash root = new SimpleHash(new DefaultObjectWrapper());
			if (data!=null)
			{
				for (String key : data.keySet())
				{
					root.put(key, data.get(key));
				}
			}
			tl.process(root, writer);
			return writer.toString();
		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new TemplateException(e);
		}
	}

}
