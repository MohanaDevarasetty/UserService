package com.farmersbyte.enterprise.usermodule.role;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

interface RoleRepository extends MongoRepository<Role, UUID> {
    List<Role> findAllByOrganizationId(ObjectId organizationId);
    Role findOneByOrganizationIdAndId(ObjectId organizationId, UUID id);
}
