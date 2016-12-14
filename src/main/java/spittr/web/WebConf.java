package spittr.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.*;

@Configuration
@EnableWebMvc
@ComponentScan("spittr.web")
public class WebConf extends WebMvcConfigurerAdapter
{
	@Bean
	public TilesConfigurer tilesConfigurer()
	{
		TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions(new String[]{"/WEB-INF/layout/tiles.xml"});
		configurer.setCheckRefresh(true);
		return configurer;
	}
	
	@Bean
	public ViewResolver viewResolver()
	{
		return new TilesViewResolver();
	}
	
	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer)
	{
		configurer.enable();
	}
	
	@Bean
	public MessageSource messageSource()
	{
		ResourceBundleMessageSource source =
				new ResourceBundleMessageSource();
		source.setBasenames("i18n/messages","i18n/ValidationMessages");
		
		return source;
	}
	
	@Bean
	public LocalValidatorFactoryBean validator()
	{
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}
	
	public Validator getValidator()
	{
		return validator();
	}
}
