package com.securewebservice.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthoHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
			throws IOException, ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
//			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//			response.setContentType("application/json");
//			response.getWriter().write("{ \"message\": \"" + "User: " + auth.getName()
//					+ " attempted to access the protected URL: " + request.getRequestURI() + "\" }");
			
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);

			final Map<String, Object> body = new HashMap<>();
			body.put("status", HttpServletResponse.SC_FORBIDDEN);
			body.put("error", "Forbidden");
			body.put("message", e.getMessage());
			String details= "User: " + auth.getName()+ " attempted to access the protected URL: " + request.getRequestURI();
			body.put("details",details);
			body.put("path", request.getServletPath());

			final ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getOutputStream(), body);		
			
		}

	}

}
