package com.africa.semicolon.ewallet.data.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NextOfKin extends JpaRepository<NextOfKin, Long> {
}
