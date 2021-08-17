package com.farmersbyte.enterprise.usermodule.user.security;

import com.farmersbyte.enterprise.usermodule.exception.BaseException;

public class SecurityContextNotFoundException extends BaseException {

  public SecurityContextNotFoundException(String message) {
    super(message);
  }
}
