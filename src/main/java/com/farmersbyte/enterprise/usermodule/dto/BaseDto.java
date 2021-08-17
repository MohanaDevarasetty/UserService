package com.farmersbyte.enterprise.usermodule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto {
  private String id;
  private AdminDataDTO adminData;
}
