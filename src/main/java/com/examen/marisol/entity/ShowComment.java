package com.examen.marisol.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "show_comments")
public class ShowComment {
	@Id
	private String id;
	private Long showId;
	private String comment;
	private Integer rating;
}
