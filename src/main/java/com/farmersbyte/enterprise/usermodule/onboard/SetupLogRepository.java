package com.farmersbyte.enterprise.usermodule.onboard;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SetupLogRepository extends MongoRepository<SetupLog, String> {
    SetupLog findFirstByContentTypeOrderByDeployedOnDesc(String contentType);
}
