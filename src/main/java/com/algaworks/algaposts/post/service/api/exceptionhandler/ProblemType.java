package com.algaworks.algaposts.post.service.api.exceptionhandler;

public enum ProblemType {

	BAD_REQUEST("/bad-request", "Bad Request"),
	ENTITY_NOT_FOUND("/entity-not-found", "Entity Not Found");

	private String uri;
	private String title;
	
	private ProblemType(String uri, String title) {
		this.uri = "https://algaposts.com.br" +  uri;
		this.title = title;
	}

	public String getUri() {
		return uri;
	}

	public String getTitle() {
		return title;
	}

}
