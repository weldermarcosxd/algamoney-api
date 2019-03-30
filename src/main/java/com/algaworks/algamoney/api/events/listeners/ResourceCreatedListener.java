package com.algaworks.algamoney.api.events.listeners;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algamoney.api.events.ResourceCreatedEvent;

@Component
public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent> {

	@Override
	public void onApplicationEvent(ResourceCreatedEvent event) {
		HttpServletResponse httpServletResponse = event.getHttpServletResponse();
		long id = event.getId();
		addHeader(httpServletResponse, id);
	}

	private void addHeader(HttpServletResponse httpServletResponse, long id) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
		httpServletResponse.setHeader("Location", uri.toASCIIString());
	}

}
