package com.farmersbyte.enterprise.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
@Profile("prod")
public class MongoDBConfiguration extends AbstractMongoClientConfiguration {
  @Value("${spring.data.mongodb.database}")
  private String dbName;

  @Autowired
  @Nullable
  private List<Converter> customConverters = List.of();

  @Override
  protected String getDatabaseName() {
    return dbName;
  }

  @Override
  public MongoClient mongoClient() {
    try {
      Map<String, String> credentials = this.getMongoDbCredentials();

      String username = credentials.get("username");
      String password = credentials.get("password");
      String hostname = credentials.get("hostname");
      log.info("hostname {} , user {}, database {}", hostname, username, getDatabaseName());
      String connectionString =
          String.format(
              "mongodb+srv://%s:%s@%s/%s?retryWrites=true&w=majority",
              username, password, hostname, getDatabaseName());
      MongoClientSettings settings =
          MongoClientSettings.builder()
              .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                  .applyConnectionString(new ConnectionString("mongodb+srv://rposa:TestingAtlas123!@cluster0.jjktj.mongodb.net/test"))
              .build();
      return MongoClients.create(settings);
    } catch (IOException e) {
      log.error("CRITICAL: Unable to connect to DB", e);
    }
    return null;
  }

  @Bean
  @Override
  public MongoCustomConversions customConversions() {
    return new MongoCustomConversions(customConverters);
  }

  private Map<String, String> getMongoDbCredentials() throws IOException {
    Map<String, String> credentialsMaps;

    String systemSecret = "";

    ObjectMapper objMapper = new ObjectMapper();
    credentialsMaps = objMapper.readValue(systemSecret, Map.class);

    return credentialsMaps;
  }
}
