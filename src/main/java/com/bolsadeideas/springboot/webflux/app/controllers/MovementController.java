package com.bolsadeideas.springboot.webflux.app.controllers;

import com.bolsadeideas.springboot.webflux.app.models.dao.BankAccountDao;
import com.bolsadeideas.springboot.webflux.app.models.dao.MovementDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.BankAccount;
import com.bolsadeideas.springboot.webflux.app.models.documents.Movement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class MovementController
{
    @Autowired
    private MovementDao daoC;

    private final String messageOk = "";

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    private static final Logger log = LoggerFactory.getLogger(MovementController.class);

    public Mono<Movement> getMovement(final String id){
        return this.daoC.findById(id);
    }

    public void saveMovement(Movement movement)
    {
        daoC.save(movement).subscribe();
    }
    public void deleteMovement(final String id){
        daoC.deleteById(id).subscribe();
    }

}
