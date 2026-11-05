package com.example.customer.common.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {

		return !request.getRequestURI().startsWith("/api/");
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
			throws ServletException, IOException {
		long start = System.nanoTime();
		int respStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		log.info("REQUEST method={} path={}", request.getMethod(), request.getRequestURI());

		try {
			chain.doFilter(request, response);
			respStatus = response.getStatus();
		} finally {
			long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
			log.info("RESPONSE method={} path={} respStatus={} durationMs={}",
				request.getMethod(), request.getRequestURI(), respStatus, duration);
		}
	}
}
