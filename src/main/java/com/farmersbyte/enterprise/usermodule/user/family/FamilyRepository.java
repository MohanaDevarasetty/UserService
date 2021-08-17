package com.farmersbyte.enterprise.usermodule.user.family;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface FamilyRepository extends MongoRepository<Family, UUID> {
    List<Family> findAllByOrganizationId(ObjectId organizationId);
    Family findOneByOrganizationIdAndId(ObjectId organizationId, UUID id);
    List<Family> findAllByOrganizationIdAndUserId(ObjectId organizationId, UUID userId);
}
