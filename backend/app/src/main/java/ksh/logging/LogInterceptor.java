package ksh.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * this class is used to configure Spring controllers to log all GET requests
 */
@Component
public class LogInterceptor implements HandlerInterceptor {
	/** logging service singleton */
    @Autowired
    LoggingService loggingService;

	/**
	 * middleware used to intercept requests before they are handled and log them if it's a GET request then continue processing
	 */
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name()) && request.getMethod().equals(HttpMethod.GET.name())) {
            this.loggingService.logRequest(request, null);
        }

        return true;
    }
}
