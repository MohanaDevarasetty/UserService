package com.farmersbyte.enterprise.usermodule.role;

import com.farmersbyte.enterprise.usermodule.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@ApiModel("Role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto extends BaseDto {
    @Field private String name;
    @Field private String description;
    @Field private RoleStatus roleStatus;
    @Field private List<UUID> policies;
}
