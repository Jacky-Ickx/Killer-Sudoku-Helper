package ksh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Cors Configuration for Spring application
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

	/**
	 * allows all origins
	 */
	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry.addMapping("/**");
	}
}