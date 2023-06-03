package ksh.logging;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * singleton Service for logging HTTP request
 */
@Service
public class LoggingService {
	/** Logger of the Spring application */
	Logger logger = LoggerFactory.getLogger(LoggingService.class);

	/**
	 * method used to log request with its body
	 * 
	 * @param httpServletRequest request to be logged
	 * @param body               corresponding body to be logged
	 */
	void logRequest(final HttpServletRequest httpServletRequest, final Object body) {
		final StringBuilder stringBuilder = new StringBuilder();
		final Map<String, String> parameters = this.buildParametersMap(httpServletRequest);

		stringBuilder.append("\033[33mREQUEST\033[0m  ");
		stringBuilder.append("method=[\033[34m").append(httpServletRequest.getMethod()).append("\033[0m] ");
		stringBuilder.append("path=[\033[34m").append(httpServletRequest.getRequestURI()).append("\033[0m] ");
		stringBuilder.append("headers=[").append(this.buildHeadersMap(httpServletRequest)).append("] ");

		if (!parameters.isEmpty()) {
			stringBuilder.append("parameters=[").append(parameters).append("] ");
		}

		if (body != null) {
			stringBuilder.append("body=[" + body + "]");
		}

		this.logger.info(stringBuilder.toString());
	}

	/**
	 * method used to log response with its body
	 * 
	 * @param httpServletRequest  request associated with response
	 * @param httpServletResponse response to be logged
	 * @param body                corresponding body to be logged
	 */
	void logResponse(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final Object body) {
		final StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("\033[36mRESPONSE\033[0m ");
		stringBuilder.append("method=[\033[34m").append(httpServletRequest.getMethod()).append("\033[0m] ");
		stringBuilder.append("path=[\033[34m").append(httpServletRequest.getRequestURI()).append("\033[0m] ");
		stringBuilder.append("responseHeaders=[").append(this.buildHeadersMap(httpServletResponse)).append("] ");
		stringBuilder.append("responseBody=[").append(body).append("] ");

		this.logger.info(stringBuilder.toString());
	}

	/**
	 * method used to build map of request parameters for easier logging
	 * 
	 * @param httpServletRequest request to build parameter map for
	 * @return parameter map of the request
	 */
	private Map<String, String> buildParametersMap(final HttpServletRequest httpServletRequest) {
		final Map<String, String> resultMap = new HashMap<>();
		final Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			final String key = parameterNames.nextElement();
			final String value = httpServletRequest.getParameter(key);
			resultMap.put(key, value);
		}

		return resultMap;
	}

	/**
	 * method used to build map of request headers for easier logging
	 * 
	 * @param request request to build header map for
	 * @return header map of the request
	 */
	private Map<String, String> buildHeadersMap(final HttpServletRequest request) {
		final Map<String, String> map = new HashMap<>();

		final Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			final String key = (String) headerNames.nextElement();
			final String value = request.getHeader(key);
			map.put(key, value);
		}

		return map;
	}

	/**
	 * method used to build map of responses headers for easier logging
	 * 
	 * @param response response to build header map for
	 * @return header map of the response
	 */
	private Map<String, String> buildHeadersMap(final HttpServletResponse response) {
		final Map<String, String> map = new HashMap<>();

		final Collection<String> headerNames = response.getHeaderNames();
		for (final String header : headerNames) {
			map.put(header, response.getHeader(header));
		}

		return map;
	}
}
