package com.bolsadeideas.springboot.webflux.app.models.documents;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@ToString
@Document(collection="bankaccount")
public class BankAccount
{
	@Id
	private String id;
	private String porductID;
	private String name;
	private Date createAt;
	private String clientId;
	private String typeClient;
	private double currentAmount;
	private double finalAmount;


	public BankAccount(String name,
					   String productID,
					   String id,
					   String clientId,
					   String typeClient,
					   double currentAmount,
					   double finalAmount) {
		this.name = name;
		this.porductID = productID;
		this.clientId = clientId;
		this.typeClient = typeClient;
		this.finalAmount = finalAmount;
		this.currentAmount = currentAmount;
	}
}
