package com.farmersbyte.enterprise.model;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
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

import java.util.List;

@Profile("local")
@Configuration
public class MongoDBConfigurationLocal extends AbstractMongoClientConfiguration {

  @Autowired  @Nullable private List<Converter> customConverters = List.of();
  @Value("${spring.data.mongodb.database}")
  private String database;
  @Value("${spring.data.mongodb.port}")
  private int port;
  private String host = "localhost";

  @Override
  public MongoClient mongoClient() {
    String connString = String.format("mongodb://%s:%d", host, port);
    MongoClientSettings settings =
        MongoClientSettings.builder()
            .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                .applyConnectionString(new ConnectionString("mongodb+srv://rposa:TestingAtlas123!@cluster0.jjktj.mongodb.net/test"))
            .build();
    return MongoClients.create(settings);
  }

  @Override
  protected String getDatabaseName() {
    return database;
  }

  @Bean
  @Override
  public MongoCustomConversions customConversions() {
    return new MongoCustomConversions(customConverters);
  }
}
