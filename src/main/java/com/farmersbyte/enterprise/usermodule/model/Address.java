package com.farmersbyte.enterprise.usermodule.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Field private String street;
    @Field private String  city;
    @Field private String  state;
    @Field private String  country;
    @Field private String pincode;
    @Field private String  notes;
    @Field private String  village;
    @Field private String  mandal;
    @Field private String  district;
    @Field private String latitude;
    @Field private String longitude;
    @Field private String  type;
    @Field private String  title;
}
