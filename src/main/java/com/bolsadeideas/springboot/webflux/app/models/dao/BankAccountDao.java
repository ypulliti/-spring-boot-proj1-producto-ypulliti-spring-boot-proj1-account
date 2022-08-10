package com.bolsadeideas.springboot.webflux.app.models.dao;

import com.bolsadeideas.springboot.webflux.app.models.documents.BankAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BankAccountDao extends ReactiveMongoRepository<BankAccount, String>{

}
