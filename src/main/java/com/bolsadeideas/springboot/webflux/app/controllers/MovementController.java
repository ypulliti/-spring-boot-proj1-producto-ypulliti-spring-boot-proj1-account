package com.bolsadeideas.springboot.webflux.app.controllers;

import com.bolsadeideas.springboot.webflux.app.BussinesLogic.AccountLogic;
import com.bolsadeideas.springboot.webflux.app.BussinesLogic.MovementLogic;
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

    private MovementLogic aclogic = new MovementLogic();

    private static final Logger log = LoggerFactory.getLogger(MovementController.class);

    public Mono<Movement> getMovement(final String id){
        return this.daoC.findById(id);
    }

    public String saveMovement(Movement movement)
    {
        String respuesta = aclogic.InsertMovement(movement);

        if (respuesta == aclogic.messageOk)
            daoC.save(movement).subscribe();

        if (respuesta.isEmpty())
            return "Sucess";
        else
            return respuesta;
    }
    public void deleteMovement(final String id){
        daoC.deleteById(id).subscribe();
    }

}
