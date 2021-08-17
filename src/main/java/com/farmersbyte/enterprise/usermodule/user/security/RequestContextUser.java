package com.farmersbyte.enterprise.usermodule.user.security;

import com.farmersbyte.enterprise.usermodule.model.RequestContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class RequestContextUser {

	@Autowired
	private ObjectMapper objectMapper;

	public RequestContext getRequestContext() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext.getAuthentication() != null) {
			Authentication authentication = securityContext.getAuthentication();
			System.out.println(authentication.getPrincipal().toString());
			try {
				return objectMapper.readValue(authentication.getPrincipal().toString(), RequestContext.class);
			} catch (JsonProcessingException e) {
				throw new SecurityContextNotFoundException("Json processing exception at requestcontext");
			}
		} else {
			throw new SecurityContextNotFoundException("Security Context is invalid");
		}
	}

//  public void setRequestContext(RequestContext requestContext) {
//    SecurityContext securityContext = SecurityContextHolder.getContext();
//    PreAuthenticatedAuthenticationToken authentication =
//        new PreAuthenticatedAuthenticationToken(requestContext.getUserId(), null);
//    authentication.setDetails(requestContext);
//    authentication.setAuthenticated(true);
//    SecurityContextHolder.getContext().setAuthentication(authentication);
//    securityContext.setAuthentication(authentication);
//  }
}
