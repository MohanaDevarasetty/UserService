package com.farmersbyte.enterprise.usermodule.user.preferences;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface UserPreferenceRepository extends MongoRepository<UserPreference, UUID> {
    List<UserPreference> findAllByOrganizationId(ObjectId organizationId);
    UserPreference findOneByOrganizationIdAndId(ObjectId organizationId, UUID id);
    List<UserPreference> findAllByOrganizationIdAndUserId(ObjectId organizationId, UUID userId);
}
