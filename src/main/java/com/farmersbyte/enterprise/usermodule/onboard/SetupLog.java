package com.farmersbyte.enterprise.usermodule.onboard;

import com.farmersbyte.enterprise.usermodule.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "setupLog")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetupLog extends BaseEntity {
    @Field private String contentType;
    @Field private String hash;
    @Field private Instant deployedOn;
}
