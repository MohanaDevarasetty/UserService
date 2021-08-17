package com.farmersbyte.enterprise.usermodule.config.service.impl;

import com.authy.AuthyApiClient;
import com.authy.AuthyException;
import com.authy.api.*;
import com.farmersbyte.enterprise.usermodule.config.service.MFAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MFAuthenticationService implements MFAService {
	
	@Autowired private AuthyApiClient client;
	
	@Override
	public int sendOTP(String email, String mobile, String countryCode, String type) throws AuthyException {
		Users users = client.getUsers();
		User user = users.createUser(email, mobile, countryCode);

		if (user.isOk()) {
			System.out.println("User ID: " + user.getId());
		} else {
			System.out.println(user.getError());
		}

		Hash response = null;
		if ("SMS".equalsIgnoreCase(type)) {
			response = users.requestSms(user.getId());
		} else if ("CALL".equalsIgnoreCase(type)) {
			response = users.requestCall(user.getId());
		}

		if (response.isOk()) {
			System.out.println(response.getMessage());
		} else {
			System.out.println(response.getError());
		}
		return user.getId();
	}

	@Override
	public boolean verifyToken(int userId, String token) throws AuthyException {
		boolean isValid = false;
		Tokens tokens = client.getTokens();
		Token response = tokens.verify(userId, token);

		if (response.isOk()) {
			System.out.println(response.toMap());
			isValid = true;
		} else {
			System.out.println(response.getError());
			isValid = false;
		}
		return isValid;
	}

}
