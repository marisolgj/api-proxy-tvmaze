package com.examen.marisol.service;

import com.examen.marisol.dto.CommentRequest;
import com.examen.marisol.dto.CommentResponse;
import com.examen.marisol.entity.ShowComment;
import com.examen.marisol.repository.ShowCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

	private final ShowCommentRepository commentRepository;

	public CommentService(ShowCommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	public boolean saveComment(CommentRequest request) {
		if (request.getRating() == null || request.getRating() < 0 || request.getRating() > 5) {
			return false;
		}

		ShowComment comment = new ShowComment();
		comment.setShowId(request.getShowId());
		comment.setComment(request.getComment());
		comment.setRating(request.getRating());

		commentRepository.save(comment);
		return true;
	}

	public List<CommentResponse> getCommentsForShow(Long showId) {
		return commentRepository.findByShowId(showId).stream()
				.map(c -> {
					CommentResponse dto = new CommentResponse();
					dto.setComment(c.getComment());
					dto.setRating(c.getRating());
					return dto;
				})
				.collect(Collectors.toList());
	}
}
