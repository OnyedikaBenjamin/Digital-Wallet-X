package com.africa.semicolon.ewallet.data.repositories;

import com.africa.semicolon.ewallet.data.models.NextOfKin;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NextOfKinRepo extends JpaRepository<NextOfKin, Long> {

}
