package com.algaworks.algamoney.api.events;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class ResourceCreatedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private HttpServletResponse httpServletResponse;
	private Long id;

	public ResourceCreatedEvent(Object source, HttpServletResponse httpServletResponse, Long id) {
		super(source);
		this.httpServletResponse = httpServletResponse;
		this.id = id;
	}

	public HttpServletResponse getHttpServletResponse() {
		return httpServletResponse;
	}

	public Long getId() {
		return id;
	}
}
