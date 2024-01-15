package com.project.delivery.repository;

import com.project.delivery.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class UserRepositoryTest {

    @Container // TODO: 2024/01/15 Adds TestContainer Config Class
    static MariaDBContainer<?> maria = new MariaDBContainer<>("mariadb:11"); // share all tests

    @Autowired
    UserRepository userRepository;

    // no need this, the @Testcontainers and @Container will auto start and stop the container.
    /*@BeforeAll
    static void beforeAll() {
      maria.start();
    }

    @AfterAll
    static void afterAll() {
      maria.stop();
    }*/

    @DynamicPropertySource  // Springboot-testContainer @ServiceConnection 사용시 해당 설정은 없어도 되는듯
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", maria::getJdbcUrl);
        registry.add("spring.datasource.username", maria::getUsername);
        registry.add("spring.datasource.password", maria::getPassword);
    }

    // TODO: 2024/01/15 User 생성하기 귀찮고 테스트 추가시 중복되지 않도록 신경써야 한다. 팩토리? 생성 편히 하게끔 TestUser 만드는 법 추가하기
    @Test
    void existsByUsername() {
        // before save
        boolean result1 = userRepository.existsByUsername("username2");
        assertThat(result1).isFalse();

        // after save
        User newUser = User.builder()
                .username("username2")
                .password("password2")
                .nickname("nickname2")
                .phone("phone2")
                .address("address2")
                .build();

        userRepository.save(newUser);
        boolean result2 = userRepository.existsByUsername("username2");
        assertThat(result2).isTrue();
    }

    @Test
    void findByUsername() {
        // before save
        Optional<User> dbUser1 = userRepository.findByUsername("username");
        assertThat(dbUser1.isEmpty()).isTrue();


        // after save
        User newUser = User.builder()
                .username("username")
                .password("password")
                .nickname("nickname")
                .phone("phone")
                .address("address")
                .build();

        userRepository.save(newUser);
        Optional<User> dbUser = userRepository.findByUsername("username");
        assertThat(dbUser.get()).usingRecursiveComparison().isEqualTo(newUser);

    }
}