package kz.javazhan.sigma_finance.repositories;

import jakarta.validation.constraints.Email;
import kz.javazhan.sigma_finance.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findByUsername(@Email String username);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
