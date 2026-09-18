package com.examen.marisol.service;

import com.examen.marisol.dto.CommentRequest;
import com.examen.marisol.entity.ShowComment;
import com.examen.marisol.repository.ShowCommentRepository;
import org.springframework.stereotype.Service;

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
}
