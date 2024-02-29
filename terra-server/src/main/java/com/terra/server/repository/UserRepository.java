package com.terra.server.repository;

import com.terra.server.persistence.TerraUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<TerraUserEntity, Integer> {
    @Query("SELECT u FROM TerraUserEntity u WHERE u.email = ?1")
    Optional<TerraUserEntity> findByEmail(String email);
}
