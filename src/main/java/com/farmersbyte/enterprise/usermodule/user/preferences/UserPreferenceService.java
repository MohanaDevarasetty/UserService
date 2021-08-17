package com.farmersbyte.enterprise.usermodule.user.preferences;

import com.farmersbyte.enterprise.usermodule.model.AdminData;
import com.farmersbyte.enterprise.usermodule.model.BaseEntity;
import com.farmersbyte.enterprise.usermodule.user.security.RequestContextUser;
import com.fasterxml.uuid.Generators;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserPreferenceService {
    @Autowired private UserPreferenceMapper userPreferenceMapper;
    @Autowired private UserPreferenceRepository userPreferenceRepository;
    @Autowired private RequestContextUser requestContextUserPreference;

    public UserPreferenceDto create(final UserPreferenceDto userPreferenceDto) {
        UserPreference userPreference = userPreferenceMapper.dtoToEntity(userPreferenceDto);
        final UUID uuid = Generators.timeBasedGenerator().generate();
        userPreference.setId(uuid);
        userPreference.setOrganizationId(new ObjectId(requestContextUserPreference.getRequestContext().getOrganizationId()));
        setAdminData(userPreference);
        userPreference = userPreferenceRepository.save(userPreference);
        userPreferenceDto.setId(userPreference.getId().toString());

        return userPreferenceDto;
    }

    public List<UserPreferenceDto> findAll(final String userId) {
        List<UserPreferenceDto> userPreferenceDtos = new ArrayList<>();
        List<UserPreference> userPreferences =
                userPreferenceRepository.findAllByOrganizationIdAndUserId(
                        new ObjectId(requestContextUserPreference.getRequestContext().getOrganizationId()),
                        UUID.fromString(userId));
        if (!userPreferences.isEmpty()) {
            userPreferenceDtos =
                    userPreferences.stream().map(userPreference ->
                            userPreferenceMapper.entityToDto(userPreference)).collect(Collectors.toList());
        }
        return userPreferenceDtos;
    }

    public UserPreferenceDto findById(final String id) {
        final UserPreference userPreference =
                userPreferenceRepository.findOneByOrganizationIdAndId(
                        new ObjectId(requestContextUserPreference.getRequestContext().getOrganizationId()),
                        UUID.fromString(id));
        return userPreferenceMapper.entityToDto(userPreference);
    }

    public UserPreferenceDto update(final String id, final UserPreferenceDto dto) {
        UserPreference userPreference = userPreferenceMapper.dtoToEntity(dto);
        userPreference.setId(UUID.fromString(id));
        UserPreference oldImage =
                userPreferenceRepository.findOneByOrganizationIdAndId(
                        new ObjectId(requestContextUserPreference.getRequestContext().getOrganizationId()),
                        userPreference.getId());
        userPreference.setAdminData(oldImage.getAdminData());
        userPreference.setOrganizationId(new ObjectId(requestContextUserPreference.getRequestContext().getOrganizationId()));
        userPreference.setDbId(oldImage.getDbId());
        updateAdminData(userPreference);
        userPreferenceRepository.save(userPreference);
        dto.setId(userPreference.getId().toString());
        return dto;
    }

    public void updateAdminData(final UserPreference currImage) {
        currImage.getAdminData().setUpdatedBy(UUID.fromString(requestContextUserPreference.getRequestContext().getUserId()));
        currImage.getAdminData().setUpdatedOn(Instant.now());
    }

    public void delete(String id) {
        final UserPreference userPreference =
                userPreferenceRepository.findOneByOrganizationIdAndId(
                        new ObjectId(requestContextUserPreference.getRequestContext().getOrganizationId()),
                        UUID.fromString(id));
        userPreferenceRepository.delete(userPreference);
    }

    private void setAdminData(final BaseEntity baseEntity) {
        baseEntity.setAdminData(
                AdminData.builder()
                        .createdBy(UUID.fromString(requestContextUserPreference.getRequestContext().getUserId()))
                        .createdOn(Instant.now())
                        .updatedBy(UUID.fromString(requestContextUserPreference.getRequestContext().getUserId()))
                        .updatedOn(Instant.now())
                        .build());
    }
}
