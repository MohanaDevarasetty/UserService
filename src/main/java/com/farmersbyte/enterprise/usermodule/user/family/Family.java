package com.farmersbyte.enterprise.usermodule.user.family;

import com.farmersbyte.enterprise.usermodule.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@Document(collection = "userfamily")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Family extends BaseEntity {
    @Field
    private UUID userId;
    @Field private Person spouse;
    @Field private Person father;
    @Field private Person mother;
    @Field private List<Person> kids;
    @Field private List<Person>  siblings;
}
