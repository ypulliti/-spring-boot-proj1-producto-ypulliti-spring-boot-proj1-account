package com.bolsadeideas.springboot.webflux.app.models.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection="bankaccount")
public class BankAccount
{
	@Id
	private String id;
	private String typeAccount;
	private String name;
	private Date createAt;
	private String clientId;
	private String typeClient;


	public BankAccount(String name, String typeProduct, String id, String clientId, String typeClient) {
		this.name = name;
		this.typeAccount = typeProduct;
		this.clientId = clientId;
		this.typeClient = typeClient;
	}
}
