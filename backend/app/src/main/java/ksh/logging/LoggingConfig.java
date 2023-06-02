package ksh.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * this class is used to configure Spring to allow log interceptor middleware to intercept requests
 */
@Configuration
public class LoggingConfig implements WebMvcConfigurer {
	/** log interceptor singleton */
    @Autowired
    LogInterceptor logInterceptor;

	/**
	 * configure Spring to use log interceptor
	 * (so that it can intercept requests)
	 */
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(this.logInterceptor);
    }
}
