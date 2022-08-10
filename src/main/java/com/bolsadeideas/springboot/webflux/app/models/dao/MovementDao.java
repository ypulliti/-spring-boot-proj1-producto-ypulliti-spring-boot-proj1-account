package com.bolsadeideas.springboot.webflux.app.models.dao;

import com.bolsadeideas.springboot.webflux.app.models.documents.Movement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface MovementDao extends ReactiveMongoRepository<Movement, String> {

    Mono<Movement> findByCode(String code);

}
