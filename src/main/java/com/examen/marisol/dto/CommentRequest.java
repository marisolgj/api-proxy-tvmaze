package com.examen.marisol.dto;

import lombok.Data;

@Data
public class CommentRequest {
	private Long showId;
	private String comment;
	private Integer rating;
}
