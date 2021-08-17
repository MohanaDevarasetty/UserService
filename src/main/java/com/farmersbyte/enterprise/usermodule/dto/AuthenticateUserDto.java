package com.farmersbyte.enterprise.usermodule.dto;

import lombok.Data;

@Data
public class AuthenticateUserDto {
	
	private String username;
	private String password;
	private String grant_type;

}
