package com.algaworks.algaposts.post.service.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Problem {

	private Integer status;
	
	private OffsetDateTime timestamp;
	
	private String type;
	
	private String title;
	
	private String detail;
	
	private String userMessage;
	
	private List<Field> fields;

}
