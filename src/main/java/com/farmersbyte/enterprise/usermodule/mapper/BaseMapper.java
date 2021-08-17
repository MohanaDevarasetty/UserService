package com.farmersbyte.enterprise.usermodule.mapper;

import org.bson.types.ObjectId;

import java.util.UUID;

public interface BaseMapper {
    default String uuidToString(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }
    default UUID stringToUuid(String uuid) {
        return uuid != null ? UUID.fromString(uuid) : null;
    }
    default String map(ObjectId objectId) {
        return objectId.toString();
    }
    default ObjectId map(String id) {
        return new ObjectId(id);
    }
}
