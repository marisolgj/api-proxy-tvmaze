package com.examen.marisol.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "shows_cache")
public class CachedShow {
	@Id
	private Long id;

	private Map<String, Object> data;
}
