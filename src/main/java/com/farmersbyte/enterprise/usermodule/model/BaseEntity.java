package com.farmersbyte.enterprise.usermodule.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
  @Setter @Getter(AccessLevel.NONE) @Field private ObjectId organizationId;
  @Field private AdminData adminData;
  @Id private ObjectId dbId;
  @Setter @Getter private UUID id;
}
