
package com.farmersbyte.enterprise.usermodule.onboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FarmerImportDto {
    private String id;
    private String organizationId;
    private String firstName;
    private String lastName;
    private String name;
    private String gender;
    private String picture;
    private String phone;
    private String village;
    private String mandal;
    private String district;
    private String state;
    private String pincode;
}
