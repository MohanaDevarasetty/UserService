package com.farmersbyte.enterprise.usermodule.user.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationContext {
    private String hostName;
    private String organizationId;
    private UUID userId;
    private String language;
    private String locale;
}
