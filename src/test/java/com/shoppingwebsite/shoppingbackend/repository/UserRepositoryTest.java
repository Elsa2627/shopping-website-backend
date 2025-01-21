package com.shoppingwebsite.shoppingbackend.repository;


import com.shoppingwebsite.shoppingbackend.model.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail_UserExists() {

        AppUser user = new AppUser();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        userRepository.save(user);


        Optional<AppUser> foundUser = userRepository.findByEmail("test@example.com");


        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testFindByEmail_UserDoesNotExist() {

        Optional<AppUser> foundUser = userRepository.findByEmail("nonexistent@example.com");


        assertThat(foundUser).isEmpty();
    }

    @Test
    public void testSaveUser() {

        AppUser user = new AppUser();
        user.setEmail("newuser@example.com");
        user.setPassword("newpassword");
        user.setFirstName("Alice");
        user.setLastName("Wonderland");


        AppUser savedUser = userRepository.save(user);


        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("newuser@example.com");
    }

    @Test
    public void testExistsByEmail() {

        AppUser user = new AppUser();
        user.setEmail("test@example.com");
        userRepository.save(user);


        boolean emailExists = userRepository.existsByEmail("test@example.com");
        assertTrue(emailExists);
    }

}
