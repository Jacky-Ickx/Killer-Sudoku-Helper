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

@Service
public class LoggingService {
    Logger logger = LoggerFactory.getLogger(LoggingService.class);

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

    void logResponse(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final Object body) {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("\033[36mRESPONSE\033[0m ");
        stringBuilder.append("method=[\033[34m").append(httpServletRequest.getMethod()).append("\033[0m] ");
        stringBuilder.append("path=[\033[34m").append(httpServletRequest.getRequestURI()).append("\033[0m] ");
        stringBuilder.append("responseHeaders=[").append(this.buildHeadersMap(httpServletResponse)).append("] ");
        stringBuilder.append("responseBody=[").append(body).append("] ");

        this.logger.info(stringBuilder.toString());
    }

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

    private Map<String, String> buildHeadersMap(final HttpServletResponse response) {
        final Map<String, String> map = new HashMap<>();

        final Collection<String> headerNames = response.getHeaderNames();
        for (final String header : headerNames) {
            map.put(header, response.getHeader(header));
        }

        return map;
    }
}
