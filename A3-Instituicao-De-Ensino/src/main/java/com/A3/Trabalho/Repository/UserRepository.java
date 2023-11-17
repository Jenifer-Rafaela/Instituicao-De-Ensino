package com.A3.Trabalho.Repository;

import com.A3.Trabalho.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);
}
