package spittr.config;

import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@ComponentScan(
		basePackages={"spittr.data"},
		excludeFilters={
			@Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)
		}
)
public class RootConf 
{
}
