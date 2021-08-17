package com.farmersbyte.enterprise.usermodule.policy;

import com.farmersbyte.enterprise.usermodule.model.BaseEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "policy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy extends BaseEntity {
    @Field private String name;
    @Field private String description;
    @Field private PolicyType type;
    @Field private List<String> statements;
}
