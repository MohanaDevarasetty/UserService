package com.farmersbyte.enterprise.usermodule.role;

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
public class RoleService {
    @Autowired private RoleMapper roleMapper;
    @Autowired private RoleRepository roleRepository;
    @Autowired private RequestContextUser requestContextUser;

    public RoleDto create(final RoleDto roleDto) {
        Role role = roleMapper.dtoToEntity(roleDto);
        final UUID uuid = Generators.timeBasedGenerator().generate();
        role.setId(uuid);
        role.setOrganizationId(new ObjectId(requestContextUser.getRequestContext().getOrganizationId()));
        setAdminData(role);
        role = roleRepository.save(role);
        roleDto.setId(role.getId().toString());

        return roleDto;
    }

    public List<RoleDto> findAll() {
        List<RoleDto> roleDtos = new ArrayList<>();
        List<Role> roles =
                roleRepository.findAllByOrganizationId(new ObjectId(requestContextUser.getRequestContext().getOrganizationId()));
        if (!roles.isEmpty()) {
            roleDtos =
                    roles.stream().map(role -> roleMapper.entityToDto(role)).collect(Collectors.toList());
        }
        return roleDtos;
    }

    public RoleDto findById(final String id) {
        final Role role =
                roleRepository.findOneByOrganizationIdAndId(
                        new ObjectId(requestContextUser.getRequestContext().getOrganizationId()),
                        UUID.fromString(id));
        return roleMapper.entityToDto(role);
    }

    public RoleDto update(final String id, final RoleDto dto) {
        Role role = roleMapper.dtoToEntity(dto);
        role.setId(UUID.fromString(id));
        Role oldImage =
                roleRepository.findOneByOrganizationIdAndId(
                        new ObjectId(requestContextUser.getRequestContext().getOrganizationId()),
                        role.getId());
        role.setAdminData(oldImage.getAdminData());
        role.setOrganizationId(new ObjectId(requestContextUser.getRequestContext().getOrganizationId()));
        role.setDbId(oldImage.getDbId());
        updateAdminData(role);
        roleRepository.save(role);
        dto.setId(role.getId().toString());
        return dto;
    }

    public void updateAdminData(final Role currImage) {
        currImage.getAdminData().setUpdatedBy(UUID.fromString(requestContextUser.getRequestContext().getUserId()));
        currImage.getAdminData().setUpdatedOn(Instant.now());
    }

    public void delete(String id) {
        final Role role =
                roleRepository.findOneByOrganizationIdAndId(
                        new ObjectId(requestContextUser.getRequestContext().getOrganizationId()),
                        UUID.fromString(id));
        roleRepository.delete(role);
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
