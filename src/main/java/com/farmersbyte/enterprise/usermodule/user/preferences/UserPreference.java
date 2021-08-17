package com.farmersbyte.enterprise.usermodule.user.preferences;

import com.farmersbyte.enterprise.usermodule.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "userpreference")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreference extends BaseEntity {
    private UUID userId;
    private String theme;
    private String language;
    private String timezone;
    private Boolean time24H;
    private String visibility;
    private Boolean notifications;
    private Boolean productUpdates;
    private Boolean membershipPolicy;
}
