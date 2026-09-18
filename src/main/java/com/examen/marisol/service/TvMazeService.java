package com.examen.marisol.service;

import com.examen.marisol.dto.ShowSearchResponse;
import com.examen.marisol.dto.external.TvMazeSearchItem;
import com.examen.marisol.dto.external.TvMazeShow;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TvMazeService {

	private final RestTemplate restTemplate;
	private static final String TV_MAZE_URL = "https://api.tvmaze.com";

	public TvMazeService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public List<ShowSearchResponse> searchShows(String query) {
		String url = UriComponentsBuilder
				.fromUriString(TV_MAZE_URL + "/search/shows")
				.queryParam("q", query)
				.toUriString();

		TvMazeSearchItem[] response = restTemplate.getForObject(url, TvMazeSearchItem[].class);

		if (response == null) {
			return List.of();
		}

		return Arrays.stream(response)
				.map(item -> mapToSearchResponse(item.getShow()))
				.collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getShowById(Long showId) {
		String url = TV_MAZE_URL + "/shows/" + showId;

		try {
			return restTemplate.getForObject(url, Map.class);
		} catch (HttpClientErrorException e) {
			return null;
		}
	}

	private ShowSearchResponse mapToSearchResponse(TvMazeShow show) {
		ShowSearchResponse response = new ShowSearchResponse();
		response.setId(show.getId());
		response.setName(show.getName());
		response.setSummary(show.getSummary());
		response.setGenres(show.getGenres());
		response.setChannel(determineChannel(show));
		return response;
	}

	private String determineChannel(TvMazeShow show) {
		if (show.getNetwork() != null && show.getNetwork().getName() != null) {
			return show.getNetwork().getName();
		}
		if (show.getWebChannel() != null && show.getWebChannel().getName() != null) {
			return show.getWebChannel().getName();
		}
		return "Unknown";
	}
}
