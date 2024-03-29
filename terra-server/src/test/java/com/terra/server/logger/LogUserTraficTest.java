package com.terra.server.logger;


import com.mongodb.client.MongoClients;
import com.terra.server.persistence.TerraLogEntity;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;

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
    public void testLogUserTrafic() {
        TerraLogEntity log = new TerraLogEntity();
        log.setUserEmail("ivoSuperAdmin@mail.com");
        log.setToken("TEST_TOKEN_123");
        mongoTemplate =
                new MongoTemplate
                        (MongoClients.create(String.format(CONNECTION_STRING)), "test");
        mongoTemplate.insert(log);
    }
}
