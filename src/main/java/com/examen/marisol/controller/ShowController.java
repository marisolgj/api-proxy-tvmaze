package com.examen.marisol.controller;

import com.examen.marisol.dto.ShowSearchResponse;
import com.examen.marisol.service.TvMazeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {

	private final TvMazeService tvMazeService;

	public ShowController(TvMazeService tvMazeService) {
		this.tvMazeService = tvMazeService;
	}

	@GetMapping("/search")
	public ResponseEntity<List<ShowSearchResponse>> searchShows(@RequestParam("q") String query) {
		List<ShowSearchResponse> results = tvMazeService.searchShows(query);
		return ResponseEntity.ok(results);
	}
}
