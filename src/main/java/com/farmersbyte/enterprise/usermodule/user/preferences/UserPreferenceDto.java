package com.farmersbyte.enterprise.usermodule.user.preferences;

import com.farmersbyte.enterprise.usermodule.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferenceDto extends BaseDto {
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
