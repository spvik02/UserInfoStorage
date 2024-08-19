package edu.vsp.repository;

import edu.vsp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    User update(String email, User newUser);

    void deleteByEmail(String email);
}
