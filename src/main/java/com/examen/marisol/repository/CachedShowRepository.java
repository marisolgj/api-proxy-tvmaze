package com.examen.marisol.repository;

import com.examen.marisol.entity.CachedShow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CachedShowRepository extends MongoRepository<CachedShow, Long> {
}
