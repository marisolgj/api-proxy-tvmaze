package com.examen.marisol.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShowSearchResponse {
	private Long id;
	private String name;
	private String channel;
	private String summary;
	private List<String> genres;
}
