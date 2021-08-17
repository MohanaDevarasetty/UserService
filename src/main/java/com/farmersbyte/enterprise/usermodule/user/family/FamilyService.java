package com.farmersbyte.enterprise.usermodule.user.family;

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
public class FamilyService {
    @Autowired private FamilyMapper familyMapper;
    @Autowired private FamilyRepository familyRepository;
    @Autowired private RequestContextUser requestContextFamily;

    public FamilyDto create(final FamilyDto familyDto) {
        Family family = familyMapper.dtoToEntity(familyDto);
        final UUID uuid = Generators.timeBasedGenerator().generate();
        family.setId(uuid);
        family.setOrganizationId(new ObjectId(requestContextFamily.getRequestContext().getOrganizationId()));
        setAdminData(family);
        family = familyRepository.save(family);
        familyDto.setId(family.getId().toString());

        return familyDto;
    }

    public List<FamilyDto> findAll(final String userId) {
        List<FamilyDto> familyDtos = new ArrayList<>();
        List<Family> families =
                familyRepository.findAllByOrganizationIdAndUserId(
                        new ObjectId(requestContextFamily.getRequestContext().getOrganizationId()),
                        UUID.fromString(userId));
        if (!families.isEmpty()) {
            familyDtos =
                    families.stream().map(family -> familyMapper.entityToDto(family)).collect(Collectors.toList());
        }
        return familyDtos;
    }

    public FamilyDto findById(final String id) {
        final Family family =
                familyRepository.findOneByOrganizationIdAndId(
                        new ObjectId(requestContextFamily.getRequestContext().getOrganizationId()),
                        UUID.fromString(id));
        return familyMapper.entityToDto(family);
    }

    public FamilyDto update(final String id, final FamilyDto dto) {
        Family family = familyMapper.dtoToEntity(dto);
        family.setId(UUID.fromString(id));
        Family oldImage =
                familyRepository.findOneByOrganizationIdAndId(
                        new ObjectId(requestContextFamily.getRequestContext().getOrganizationId()),
                        family.getId());
        family.setAdminData(oldImage.getAdminData());
        family.setOrganizationId(new ObjectId(requestContextFamily.getRequestContext().getOrganizationId()));
        family.setDbId(oldImage.getDbId());
        updateAdminData(family);
        familyRepository.save(family);
        dto.setId(family.getId().toString());
        return dto;
    }

    public void updateAdminData(final Family currImage) {
        currImage.getAdminData().setUpdatedBy(UUID.fromString(requestContextFamily.getRequestContext().getUserId()));
        currImage.getAdminData().setUpdatedOn(Instant.now());
    }

    public void delete(String id) {
        final Family family =
                familyRepository.findOneByOrganizationIdAndId(
                        new ObjectId(requestContextFamily.getRequestContext().getOrganizationId()),
                        UUID.fromString(id));
        familyRepository.delete(family);
    }

    private void setAdminData(final BaseEntity baseEntity) {
        baseEntity.setAdminData(
                AdminData.builder()
                        .createdBy(UUID.fromString(requestContextFamily.getRequestContext().getUserId()))
                        .createdOn(Instant.now())
                        .updatedBy(UUID.fromString(requestContextFamily.getRequestContext().getUserId()))
                        .updatedOn(Instant.now())
                        .build());
    }

}
