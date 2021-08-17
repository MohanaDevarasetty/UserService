package com.farmersbyte.enterprise.usermodule.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Data
public class UserTokenDto {
	private static final long serialVersionUID = 1L;
	@Field private UUID userId;
	@Field private String accessToken;
	@Field private String tokenType;
	@Field private String refreshToken;
	@Field private Integer expiresIn;
	@Field private String scope;
}
