package com.farmersbyte.enterprise.usermodule.user;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
    List<User> findAllByOrganizationId(ObjectId organizationId);
    User findOneByOrganizationIdAndId(ObjectId organizationId, UUID id);
	User findOneByOrganizationIdAndUserName(ObjectId organizationId, String userName);
    void deleteAllByOrganizationId(ObjectId organizationId);
    void deleteByOrganizationIdAndId(ObjectId organizationId, UUID id);
}
