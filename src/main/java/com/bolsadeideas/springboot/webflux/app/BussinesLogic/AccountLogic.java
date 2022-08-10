package com.bolsadeideas.springboot.webflux.app.BussinesLogic;

import com.bolsadeideas.springboot.webflux.app.models.documents.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;

import java.util.List;

public class AccountLogic
{
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;
    private final String messageOk = "";

    public String InsertAccont(BankAccount bAccount)
    {
        String result = messageOk;

        Query query = new Query();
        query.addCriteria(Criteria.where("clientId").is(bAccount.getClientId()));
        Flux<BankAccount> queryResult = mongoTemplate.find(query, BankAccount.class);

        if (bAccount.getTypeClient().equals("personal"))
        {
            switch (bAccount.getTypeAccount())
            {
                case "cuenta corriente":
                case "ahorro":
                case "plazo fijo":

                    

                    if(queryResult.count().block() > 0)
                        result = "el cliente ya tiene una cuenta bancaria";

                    break;

                case "credito personal":
                    Flux<BankAccount>queryR2 = queryResult.filter(c -> c.getTypeAccount() != "credito personal");

                    if (queryR2.count().block() > 0)
                        result = "el cliente ya tiene una cuenta bancaria";

                    break;

                default:
                    result = "Tipo de cuenta no valido";
            }
        }
        else
        {
            switch (bAccount.getTypeAccount())
            {
                case "cuenta corriente":
                    // el cliente puede tener una o varias cuentas de este tipo
                    break;

                case "cuenta empresarial":

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
