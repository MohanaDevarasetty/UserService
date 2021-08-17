package com.farmersbyte.enterprise.usermodule.user;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface PlatformOrganizationRepository extends MongoRepository<PlatformOrganization, UUID> {
    List<PlatformOrganization> findAllByOrganizationId(ObjectId organizationId);
    Page<PlatformOrganization> findAllByOrganizationId(ObjectId organizationId, Pageable pageable);
    Page<PlatformOrganization> findAllByType(String type, Pageable pageable);
    void deleteAllByOrganizationId(ObjectId organizationId);
}
