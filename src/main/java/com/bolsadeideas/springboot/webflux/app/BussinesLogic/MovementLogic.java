package com.bolsadeideas.springboot.webflux.app.BussinesLogic;

import com.bolsadeideas.springboot.webflux.app.models.documents.BankAccount;
import com.bolsadeideas.springboot.webflux.app.models.documents.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class MovementLogic
{
    @Autowired
    ReactiveMongoTemplate mongoTemplate;
    public final String messageOk = "";

    public Flux<Movement> getMovementsPerClient(Movement movement)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("clientId").is(movement.getClientId()));
        return mongoTemplate.find(query, Movement.class);
    }

    public Flux<Movement> getMovementsPerClientPerAccount(Movement movement)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("clientId").is(movement.getClientId()).and("accountNumber").is(movement.getAccountNumber()));
        return mongoTemplate.find(query, Movement.class);
    }

    public String InsertMovement(Movement movement)
    {
        String result = messageOk;
        Flux<Movement> movementsPerClient = getMovementsPerClient(movement);
        Flux<Movement> movementsPerClientPerAccount = getMovementsPerClientPerAccount(movement);
        long moves = movementsPerClientPerAccount.count().block();

        Query query2 = new Query();
        query2.addCriteria(Criteria.where("clientId").is(movement.getAccountNumber()));
        Flux<BankAccount> queryResult2 = mongoTemplate.find(query2, BankAccount.class);

        BankAccount bCount = queryResult2.next().block();

        if (movement.getClientType().equals("personal"))
        {
            if(movement.getMovementType() == "retiro" && bCount.getCurrentAmount() < bCount.getCurrentAmount() - movement.getMovementAmount())
                return "No hay saldo suficiente en la cuenta";

            switch (movement.getAccountType())
            {
                case "ahorro":
                    // por ahora el límite es 30 movimvientos para todas las cuentas de ahorro
                    if(moves >= 30)
                        return "no se permiten mas movimientos";

                    break;

                case "cuenta corriente":
                    // no hay límite de movimvientos
                    break;

                case "plazo fijo":
                    // sólo permite un movimviento, por ahora voy a ignorar la condiciónd e un día específico
                    if(moves > 1)
                        return "el cliente ya realizo un movimiento";

                    break;

                case "credito personal":
                    if(movement.getMovementType() == "retiro" && bCount.getCurrentAmount() < bCount.getCurrentAmount() - movement.getMovementAmount())
                        return "No hay saldo suficiente en la cuenta";

                    Flux<Movement>queryR2 = movementsPerClient.filter(c -> c.getAccountType() != "credito personal");

                    if (queryR2.count().block() > 0)
                        result = "el cliente ya tiene una cuenta bancaria";

                    break;

                default:
                    result = "Tipo de cuenta no valido";
            }
        }
        else
        {
            switch (movement.getAccountType())
            {
                case "cuenta corriente":
                    // el cliente puede tener una o varias cuentas de este tipo, no hay límite de movimientos
                    break;

                case "cuenta empresarial":
                    if(movement.getMovementType() == "retiro" && bCount.getCurrentAmount() < bCount.getCurrentAmount() - movement.getMovementAmount())
                        return "No hay saldo suficiente en la cuenta";

                    break;

                case "credito personal":
                case "ahorro":
                case "plazo fijo":
                    result = "El cliente no puede tener este tipo de cuenta";
                    break;

                default:
                    result = "Tipo de cuenta no valido";
            }
        }


        return result;
    }

}
