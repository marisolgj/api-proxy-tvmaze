package com.examen.marisol.service;

import com.examen.marisol.dto.ShowSearchResponse;
import com.examen.marisol.dto.external.TvMazeSearchItem;
import com.examen.marisol.dto.external.TvMazeShow;
import com.examen.marisol.entity.CachedShow;
import com.examen.marisol.repository.CachedShowRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TvMazeService {

	private final RestTemplate restTemplate;
	private final CachedShowRepository cachedShowRepository;
	private final CommentService commentService;
	private static final String TV_MAZE_URL = "https://api.tvmaze.com";

	public TvMazeService(RestTemplate restTemplate,
						 CachedShowRepository cachedShowRepository,
						 CommentService commentService) {
		this.restTemplate = restTemplate;
		this.cachedShowRepository = cachedShowRepository;
		this.commentService = commentService;
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
		Map<String, Object> showData = null;

		Optional<CachedShow> cachedShow = cachedShowRepository.findById(showId);
		if (cachedShow.isPresent()) {
			showData = cachedShow.get().getData();
		} else {
			String url = TV_MAZE_URL + "/shows/" + showId;
			try {
				showData = restTemplate.getForObject(url, Map.class);
				if (showData != null) {
					CachedShow newCache = new CachedShow();
					newCache.setId(showId);
					newCache.setData(showData);
					cachedShowRepository.save(newCache);
				}
			} catch (HttpClientErrorException e) {
				return null;
			}
		}

		if (showData != null) {
			Map<String, Object> responseData = new HashMap<>(showData);
			responseData.put("comments", commentService.getCommentsForShow(showId));
			return responseData;
		}

		return null;
	}

	private ShowSearchResponse mapToSearchResponse(TvMazeShow show) {
		ShowSearchResponse response = new ShowSearchResponse();
		response.setId(show.getId());
		response.setName(show.getName());
		response.setSummary(show.getSummary());
		response.setGenres(show.getGenres());
		response.setChannel(determineChannel(show));
		response.setComments(commentService.getCommentsForShow(show.getId()));
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
