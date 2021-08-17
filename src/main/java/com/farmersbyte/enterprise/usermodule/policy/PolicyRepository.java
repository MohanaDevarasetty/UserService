package com.farmersbyte.enterprise.usermodule.policy;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

interface PolicyRepository extends MongoRepository<Policy, UUID> {
    List<Policy> findAllByOrganizationId(ObjectId organizationId);
    Policy findOneByOrganizationIdAndId(ObjectId organizationId, UUID id);
}
