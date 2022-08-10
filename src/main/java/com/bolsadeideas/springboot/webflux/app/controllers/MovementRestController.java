package com.bolsadeideas.springboot.webflux.app.controllers;

import com.bolsadeideas.springboot.webflux.app.models.dao.BankAccountDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/apis")
public class BankAccountRestController
{
	@Autowired
	private BankAccountDao dao;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	private final String messageOk = "";

	private BankAccountController cControl = new BankAccountController();

	private static final Logger log = LoggerFactory.getLogger(BankAccountRestController.class);

	@GetMapping("showPersonalBankAccounts")
	public Flux<BankAccount> showPersonalBankAccounts(){

		Flux<BankAccount> productos = dao.findAll()
				.map(producto -> {
					producto.setName(producto.getName().toUpperCase());
					return producto;
				})
				.doOnNext(prod -> log.info(prod.getName()));

		return productos;
	}

	@GetMapping("showPersonalBankAccount/{id}")
	public Mono<BankAccount> showPersonalBankAccount(@PathVariable String id)
	{
		Flux<BankAccount> productos = dao.findAll();
		
		Mono<BankAccount> producto = productos
				.filter(p -> p.getId().equals(id))
				.next()
				.doOnNext(prod -> log.info(prod.getName()));
				
		return producto;
	}

	@GetMapping("showPersonalBankAccountByClient/{clientId}")
	public Mono<BankAccount> showPersonalBankAccountByClient(@PathVariable String clientId){
		Query query = new Query();
		query.addCriteria(Criteria.where("clientId").is(clientId));
		return mongoTemplate.find(query, BankAccount.class).next();
	}

	@PutMapping("insertPersonalAccount/{id}/{nombre}/{tipoCuentaBancaria}/{clienteId}/{typeClient}")
	public String insertAccount(@PathVariable String id,
								@PathVariable String nombre,
								@PathVariable String tipoCuentaBancaria,
								@PathVariable String clienteId,
								@PathVariable String typeClient)
	{
		BankAccount bClient = new BankAccount(nombre, tipoCuentaBancaria, id, clienteId, typeClient);
		cControl.saveAccount(bClient);
		return "Sucess";
	}

	@PutMapping("updatePersonalAcocunt/{id}/{nombre}/{tipoCuentaBancaria}/{clienteId}/{typeClient}")
	public String updateAcocunt(@PathVariable String id,
								@PathVariable String nombre,
								@PathVariable String tipoCuentaBancaria,
								@PathVariable String clienteId,
								@PathVariable String typeClient)
	{
		BankAccount bClient = new BankAccount(nombre, tipoCuentaBancaria, id, clienteId, typeClient);
		cControl.saveAccount(bClient);
		return "Sucess";
	}

	@DeleteMapping("deletePersonalAccount/{id}")
	public String deleteAccount(@PathVariable String id)
	{
		cControl.deleteAccount(id);
		return "Sucess";
	}

}
