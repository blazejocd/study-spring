package spittr.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import spittr.web.WebConf;

public class SpittrWebAppInitializer 
extends AbstractAnnotationConfigDispatcherServletInitializer
{
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses()
	{
		return new Class<?>[] {RootConf.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConf.class};
	}
}
