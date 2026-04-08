package io.dzung.taskmanager.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Testcontainers
public class UserRepositoryIntegrationTests {
    @Container
    static MySQLContainer mysql = new MySQLContainer(DockerImageName.parse("mysql:lts"));

    @DynamicPropertySource
    static void setupMySQLConnection(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        user = userRepository.save(
                User.builder()
                        .username("dzungnguyen")
                        .email("dzungnguyen@example.com")
                        .password("123456")
                        .build());
    }

    @Test
    void testFindByEmail() {
        User foundUser = userRepository.findByEmail("dzungnguyen@example.com").orElse(null);

        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void testFindByUsername() {
        User foundUser = userRepository.findByUsername("dzungnguyen").orElse(null);

        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
    }
}
