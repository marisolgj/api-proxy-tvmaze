package com.examen.marisol.dto.external;

import lombok.Data;

import java.util.List;

@Data
public class TvMazeShow {
	private Long id;
	private String name;
	private String summary;
	private List<String> genres;
	private Network network;
	private WebChannel webChannel;

	@Data
	public static class Network {
		private String name;
	}

	@Data
	public static class WebChannel {
		private String name;
	}
}
