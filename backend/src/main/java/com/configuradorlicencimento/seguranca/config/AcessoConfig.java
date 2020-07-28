package com.configuradorlicencimento.seguranca.config;

import com.configuradorlicencimento.configuracao.components.CustomizacaoObjetoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@ComponentScan("com.configuradorlicencimento")
public class AcessoConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomizacaoObjetoMapper customizacaoObjetoMapper;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
					.and().headers().frameOptions().disable()
					.and().csrf().disable()
				.authorizeRequests()
					.antMatchers("**").permitAll();
//				.and()
//				.formLogin()
//					.loginPage("http://sistemas.sema.ap.gov.br/portal-seguranca");
//				.and()
//				.logout()
//					.clearAuthentication(true)
//					.invalidateHttpSession(true)
//					.deleteCookies("JSESSIONID", "io")
//					.logoutSuccessHandler((new Logout()))
//					.logoutUrl("/logout");
	}

	@Bean
	public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {

		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

		mappingJackson2HttpMessageConverter.setObjectMapper(this.customizacaoObjetoMapper);

		return mappingJackson2HttpMessageConverter;
	}

}
