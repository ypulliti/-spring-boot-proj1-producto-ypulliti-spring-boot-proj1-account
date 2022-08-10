package com.bolsadeideas.springboot.webflux.app.controllers;

import com.bolsadeideas.springboot.webflux.app.models.dao.BankAccountDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class BankAccountController
{
    @Autowired
    private BankAccountDao daoC;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    private static final Logger log = LoggerFactory.getLogger(BankAccountController.class);

    public Mono<BankAccount> getAccount(final String id){
        return this.daoC.findById(id);
    }

    public void saveAccount(BankAccount person)
    {
        daoC.save(person).subscribe();
    }
    public void deleteAccount(final String id){
        daoC.deleteById(id).subscribe();
    }

}
