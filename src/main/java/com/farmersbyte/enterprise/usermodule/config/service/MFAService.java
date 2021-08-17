package com.farmersbyte.enterprise.usermodule.config.service;

import com.authy.AuthyException;


public interface MFAService {
	int sendOTP(String email, String mobile, String countryCode, String type) throws AuthyException;
	boolean verifyToken(int userId, String token) throws AuthyException;
}
