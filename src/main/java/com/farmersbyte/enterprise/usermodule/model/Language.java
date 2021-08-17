package com.farmersbyte.enterprise.usermodule.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Language {
    @Field private String english;
    @Field private String hindi;
    @Field private String telugu;
    @Field private String tamil;
    @Field private String kannada;
    @Field private String malayalam;
    @Field private String french;
}
