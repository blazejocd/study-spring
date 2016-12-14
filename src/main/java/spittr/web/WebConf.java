package spittr.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@Import(ThymeleafConfig.class)
@ComponentScan("spittr.web")
public class WebConf extends WebMvcConfigurerAdapter
{
	
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
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource());
		return validator;
	}
	
	public Validator getValidator()
	{
		return validator();
	}
}
