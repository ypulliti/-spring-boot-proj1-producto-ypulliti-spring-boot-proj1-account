package com.bolsadeideas.springboot.webflux.app.controllers;

import com.bolsadeideas.springboot.webflux.app.models.dao.BankAccountDao;
import com.bolsadeideas.springboot.webflux.app.models.dao.MovementDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.BankAccount;
import com.bolsadeideas.springboot.webflux.app.models.documents.Movement;
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
public class MovementRestController
{
	@Autowired
	private MovementDao dao;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	private final String messageOk = "";

	private MovementController cControl = new MovementController();

	private static final Logger log = LoggerFactory.getLogger(MovementRestController.class);

	@GetMapping("showPersonalBankAccounts")
	public Flux<Movement> showPersonalBankAccounts(){

		Flux<Movement> movements = dao.findAll()
				.map(c -> {
					c.setAccountNumber(c.getAccountNumber());
					return c;
				})
				.doOnNext(c -> log.info(c.getAccountNumber()));

		return movements;
	}

	@GetMapping("showPersonalBankAccount/{id}")
	public Mono<Movement> showPersonalBankAccount(@PathVariable String id)
	{
		Flux<Movement> movements = dao.findAll();
		
		Mono<Movement> mov = movements
				.filter(p -> p.getId().equals(id))
				.next()
				.doOnNext(c -> log.info(c.getAccountNumber()));
				
		return mov;
	}

	@GetMapping("showPersonalBankAccountByClient/{clientId}")
	public Mono<BankAccount> showPersonalBankAccountByClient(@PathVariable String clientId){
		Query query = new Query();
		query.addCriteria(Criteria.where("clientId").is(clientId));
		return mongoTemplate.find(query, BankAccount.class).next();
	}

	@PutMapping("insertMovement/{id}/{numAccount}/{movType}/{currentAccount}/{movementAmount}/{finalAmount}")
	public String insertMovement(@PathVariable String id,
								@PathVariable String numAccount,
								@PathVariable String movType,
								@PathVariable Double currentAccount,
								@PathVariable Double movementAmount,
								@PathVariable Double finalAmount)
	{
		Movement movement = new Movement(id, numAccount, movType, currentAccount, movementAmount, finalAmount);
		cControl.saveMovement(movement);
		return "Sucess";
	}

	@PutMapping("updateMovement/{id}/{numAccount}/{movType}/{currentAccount}/{movementAmount}/{finalAmount}")
	public String updateMovement(@PathVariable String id,
								@PathVariable String numAccount,
								@PathVariable String movType,
								@PathVariable Double currentAccount,
								@PathVariable Double movementAmount,
								@PathVariable Double finalAmount)
	{
		Movement movement = new Movement(id, numAccount, movType, currentAccount, movementAmount, finalAmount);
		cControl.saveMovement(movement);
		return "Sucess";
	}

	@DeleteMapping("deleteMovement/{id}")
	public String deleteMovement(@PathVariable String id)
	{
		cControl.deleteMovement(id);
		return "Sucess";
	}

}
