package com.farmersbyte.enterprise.usermodule.role;

import com.farmersbyte.enterprise.usermodule.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@Document(collection = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    @Field private String name;
    @Field private String description;
    @Field private RoleStatus roleStatus;
    @Field private List<UUID> policies;
}
