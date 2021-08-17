package com.farmersbyte.enterprise.usermodule.user.family;

import com.farmersbyte.enterprise.usermodule.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    @Field
    private String name;
    @Field private Date dob;
    @Field private String gender;
    @Field private String bloodGroup;
    @Field private Boolean staysWithUser;
    @Field private Address address;
}
