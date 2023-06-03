package ksh.logging;

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import jakarta.servlet.http.HttpServletRequest;

/**
 * this class is used to configure Spring controllers to log all requests that contain a request body
 */
@ControllerAdvice
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {
	/** logging service singleton */
    @Autowired
    LoggingService loggingService;

	/** http servlet request singleton */
    @Autowired
    HttpServletRequest httpServletRequest;

	/**
	 * middleware telling controller that all requests are supported (nedded so that processing won't be cancelled)
	 */
    @Override
    public boolean supports(final MethodParameter methodParameter, final Type type, final Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

	/**
	 * middleware telling controller to log body after it's been read and continue processing
	 */
    @Override
    public Object afterBodyRead(final Object body, final HttpInputMessage inputMessage, final MethodParameter parameter, final Type targetType, final Class<? extends HttpMessageConverter<?>> converterType) {
        this.loggingService.logRequest(this.httpServletRequest, body);

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
