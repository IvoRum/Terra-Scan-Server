package com.terra.server.logger;


import com.mongodb.client.MongoClients;
import com.terra.server.persistence.TerraUserLogEntity;
import de.flapdoodle.embed.mongo.MongodExecutable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;

import java.sql.Date;
import java.time.Instant;

@SpringBootTest
@TestPropertySource(properties = { "logging.level.org.springframework.data.mongodb.core.MongoTemplate=DEBUG" })
public class LogUserTraficTest {

    private static final String CONNECTION_STRING = "mongodb+srv://ivo:ar4ebar4e@terra-log.rumnftg.mongodb.net/?retryWrites=true&w=majority&appName=terra-log";

    private MongoTemplate mongoTemplate;
    private MongodExecutable mongodExecutable;

    @AfterEach
    void clean() {
        mongodExecutable.stop();
    }

    @BeforeEach
    void setup() throws Exception {
        mongodExecutable.start();
        mongoTemplate = new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING)), "test");
    }

    @Test
    @Disabled
    public void testLogUserTrafic() {
        TerraUserLogEntity log = new TerraUserLogEntity();
        log.setUserEmail("ivoSuperAdmin@mail.com");
        log.setIpAddress("test ip address");
        log.setMacAddress("test mac address");
        log.setDate(Date.from(Instant.now()));
        log.setToken("TEST_TOKEN_123");
        mongoTemplate =
                new MongoTemplate
                        (MongoClients.create(String.format(CONNECTION_STRING)), "test");
        mongoTemplate.insert(log);
    }
}
