package com.farmersbyte.enterprise.usermodule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AdminDataDTO {
  private UUID createdBy;
  private String createdOn;
  private UUID updatedBy;
  private String updatedOn;
}
