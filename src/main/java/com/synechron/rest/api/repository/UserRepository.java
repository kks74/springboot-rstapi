package com.synechron.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synechron.rest.api.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
}
