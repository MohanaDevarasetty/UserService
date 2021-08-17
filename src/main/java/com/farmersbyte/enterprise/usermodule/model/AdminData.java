package com.farmersbyte.enterprise.usermodule.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminData {
  @Field private UUID createdBy;
  @Field private Instant createdOn;
  @Field private UUID updatedBy;
  @Field private Instant updatedOn;
}
