package com.farmersbyte.enterprise.usermodule.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RequestContext {
  private String authToken;
  private String userId;
  private String userName;
  private String organizationId;
  private String requestId;
  private String b3TraceId;
  private Locale locale;
  private String language;
  private String mail;
  private String phone;
}
