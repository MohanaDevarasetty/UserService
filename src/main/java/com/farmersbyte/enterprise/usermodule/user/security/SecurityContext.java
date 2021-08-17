package com.farmersbyte.enterprise.usermodule.user.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityContext {

    public static final long TOKEN_VALIDITY = 3600;
    private int refreshTokenValidity;
    private long tokenValidity;

    private String nonce;
    private String userName;
    private String userId;
    private String organizationHostName;
    private String organizationId;
    private String zid;
    private String aid;
    private String locale;
    private String language;

    private int logLevel = 1;
}
