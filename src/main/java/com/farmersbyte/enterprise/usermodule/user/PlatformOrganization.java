
package com.farmersbyte.enterprise.usermodule.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "platformOrganization")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlatformOrganization {
    @Field
    private String system;
    @Field
    private String type;
    @Field
    private ObjectId organizationId;
    @Id
    private ObjectId dbId;
    @Field
    private ObjectId id;
}
