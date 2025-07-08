package com.OnlineBusBooking.OnlineBus.repository;

import com.OnlineBusBooking.OnlineBus.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> aa3dc81 (updated)
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
<<<<<<< HEAD
=======

    List<User> findByRole(String role);
>>>>>>> aa3dc81 (updated)
}
