package com.windfally.uploader.user.repository;

import com.windfally.uploader.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByEmail(String email);

    Optional<User> findByEmailAndAuthorities_AuthorityName(String email, String authorityName);

    List<User> findByCreatedDateBetween(LocalDateTime createdDateStart, LocalDateTime createdDateEnd);


}
