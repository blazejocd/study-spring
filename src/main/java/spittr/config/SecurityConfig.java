package spittr.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	public DataSource dataSource;
	
	/*protected void configure(HttpSecurity http) throws Exception
	{
		//http.csrf().disable();
	}*/
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		
		System.out.println(new StandardPasswordEncoder("1234").encode("alibaba"));
		auth
			.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("SELECT username,password,true"
					+ " FROM users WHERE username=?")
			.authoritiesByUsernameQuery("SELECT username,'ROLE_USER'"
					+ " FROM users WHERE username=?")
			.passwordEncoder(new StandardPasswordEncoder("1234"));
	}
	
	protected void configure(HttpSecurity http) throws Exception
	{
		http
			.formLogin()
			.and()
			.authorizeRequests()
				.antMatchers("/spitter/register").permitAll()
				.antMatchers("/spitter/**").authenticated()
				.anyRequest().permitAll();
	}
}
