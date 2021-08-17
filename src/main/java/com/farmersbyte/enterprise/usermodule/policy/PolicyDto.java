package com.farmersbyte.enterprise.usermodule.policy;

import com.farmersbyte.enterprise.usermodule.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@ApiModel("User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyDto extends BaseDto {
    @Field private String name;
    @Field private String description;
    @Field private PolicyType type;
    @Field private List<String> statements;
}
