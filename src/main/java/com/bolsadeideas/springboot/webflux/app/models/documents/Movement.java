package com.bolsadeideas.springboot.webflux.app.models.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@Document(collection="movement")
public class Movement
{
    @Id
    private String id;
    private String accountNumber;
    private Date dateRegister;
    private String movementType;

    private double movementAmount;

    private String accountType;

    private String clientId;
    private String clientType;

    public Movement(     String id,
                         String clientId,
                         String clientType,
                         String accountNumber,
                         String accountType,
                         String movementType,
                         double movementAmount
                         )
    {
        this.id = id;
        this.clientId = clientId;
        this.accountNumber = accountNumber;
        this.movementType = movementType;
        this.movementAmount = movementAmount;

        this.accountType = accountType;
        this.clientType = clientType;
    }

}
