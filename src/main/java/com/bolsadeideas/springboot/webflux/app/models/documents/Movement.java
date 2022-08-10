package com.bolsadeideas.springboot.webflux.app.models.documents;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@ToString
@Document(collection="movement")
public class Movement
{
    @Id
    private String id;
    private String accountNumber;
    private Date dateRegister;
    private String movementType;
    private double currentAmount;
    private double movementAmount;
    private double finalAmount;
}
