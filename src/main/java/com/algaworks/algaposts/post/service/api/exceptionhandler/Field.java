package com.algaworks.algaposts.post.service.api.exceptionhandler;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Field {
	
	private String name;
	
	private String userMessage;
	
	public Field(String name, String userMessage) {
		super();
		this.name = name;
		this.userMessage = userMessage;
	}

}
