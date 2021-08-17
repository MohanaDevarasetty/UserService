package com.farmersbyte.enterprise.usermodule.policy;

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
public class PolicyService {
    @Autowired private PolicyMapper policyMapper;
    @Autowired private PolicyRepository policyRepository;
    @Autowired private RequestContextUser requestContextUser;

    public PolicyDto create(final PolicyDto policyDto) {
        Policy policy = policyMapper.dtoToEntity(policyDto);
        final UUID uuid = Generators.timeBasedGenerator().generate();
        policy.setId(uuid);
        policy.setOrganizationId(new ObjectId(requestContextUser.getRequestContext().getOrganizationId()));
        setAdminData(policy);
        policy = policyRepository.save(policy);
        policyDto.setId(policy.getId().toString());

        return policyDto;
    }

    public List<PolicyDto> findAll() {
        List<PolicyDto> policyDtos = new ArrayList<>();
        List<Policy> policies =
                policyRepository.findAllByOrganizationId(new ObjectId(requestContextUser.getRequestContext().getOrganizationId()));
        if (!policies.isEmpty()) {
            policyDtos =
                    policies.stream().map(policy -> policyMapper.entityToDto(policy)).collect(Collectors.toList());
        }
        return policyDtos;
    }

    public PolicyDto findById(final String id) {
        final Policy policy =
                policyRepository.findOneByOrganizationIdAndId(
                        new ObjectId(requestContextUser.getRequestContext().getOrganizationId()),
                        UUID.fromString(id));
        return policyMapper.entityToDto(policy);
    }

    public PolicyDto update(final String id, final PolicyDto dto) {
        Policy policy = policyMapper.dtoToEntity(dto);
        policy.setId(UUID.fromString(id));
        Policy oldImage =
                policyRepository.findOneByOrganizationIdAndId(
                        new ObjectId(requestContextUser.getRequestContext().getOrganizationId()),
                        policy.getId());
        policy.setAdminData(oldImage.getAdminData());
        policy.setOrganizationId(new ObjectId(requestContextUser.getRequestContext().getOrganizationId()));
        policy.setDbId(oldImage.getDbId());
        updateAdminData(policy);
        policyRepository.save(policy);
        dto.setId(policy.getId().toString());
        return dto;
    }

    public void updateAdminData(final Policy currImage) {
        currImage.getAdminData().setUpdatedBy(UUID.fromString(requestContextUser.getRequestContext().getUserId()));
        currImage.getAdminData().setUpdatedOn(Instant.now());
    }

    public void delete(String id) {
        final Policy policy =
                policyRepository.findOneByOrganizationIdAndId(
                        new ObjectId(requestContextUser.getRequestContext().getOrganizationId()),
                        UUID.fromString(id));
        policyRepository.delete(policy);
    }

    private void setAdminData(final BaseEntity baseEntity) {
        baseEntity.setAdminData(
                AdminData.builder()
                        .createdBy(UUID.fromString(requestContextUser.getRequestContext().getUserId()))
                        .createdOn(Instant.now())
                        .updatedBy(UUID.fromString(requestContextUser.getRequestContext().getUserId()))
                        .updatedOn(Instant.now())
                        .build());
    }
}
