package com.farmersbyte.enterprise.usermodule.model;

import lombok.Data;

@Data
public class TwilioModel {
	private Integer userId;
	private boolean validToken;
	private String token;
}
