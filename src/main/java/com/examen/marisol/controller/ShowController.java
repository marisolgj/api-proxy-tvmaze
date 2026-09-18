package com.examen.marisol.controller;

import com.examen.marisol.dto.CommentRequest;
import com.examen.marisol.dto.ShowSearchResponse;
import com.examen.marisol.service.CommentService;
import com.examen.marisol.service.TvMazeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {

	private final TvMazeService tvMazeService;
	private final CommentService commentService;

	public ShowController(TvMazeService tvMazeService, CommentService commentService) {
		this.tvMazeService = tvMazeService;
		this.commentService = commentService;
	}

	@GetMapping("/search")
	public ResponseEntity<List<ShowSearchResponse>> searchShows(@RequestParam("q") String query) {
		List<ShowSearchResponse> results = tvMazeService.searchShows(query);
		return ResponseEntity.ok(results);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getShowById(@PathVariable("id") Long showId) {
		Map<String, Object> show = tvMazeService.getShowById(showId);

		if (show == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(show);
	}

	@PostMapping("/comments")
	public ResponseEntity<String> addComment(@RequestBody CommentRequest request) {
		boolean isSaved = commentService.saveComment(request);

		if (isSaved) {
			return ResponseEntity.ok().body("{\"status\": \"success\", \"message\": \"Comentario guardado\"}");
		} else {
			return ResponseEntity.badRequest().body("{\"status\": \"error\", \"message\": \"Rating inválido. Debe ser de 0 a 5.\"}");
		}
	}
}
