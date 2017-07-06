package pl.assecods.pkitest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.assecods.pkitest.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findOneByLogin(String login);
}
