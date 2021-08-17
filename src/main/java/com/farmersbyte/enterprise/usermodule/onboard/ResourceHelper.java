package com.farmersbyte.enterprise.usermodule.onboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
@Slf4j
public class ResourceHelper {

    @Autowired
    Hashing hashing;

    public Map<String, Object> getFileDataAsMap(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> contentMap = null;
        InputStream input = null;
        try {
            input = getResourceStream(filePath);
            if (input == null) {
                return null;
            }
            contentMap = mapper.readValue(input, Map.class);
        } catch (IOException e) {
            log.error("Exception reading file content..", e);
        } finally {
            safeClose(input);
        }
        return contentMap;
    }

    public InputStream getResourceStream(String jsonPath) {
        try {
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource resource = resourcePatternResolver.getResource(jsonPath);
            return resource.getInputStream();
        } catch (IOException e) {
            log.error("Exception reading file content..", e);
        }
        return null;
    }

    public static void safeClose(InputStream input) {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                log.error("IO Exception reading file content..", e);
            }
        }
    }
}
