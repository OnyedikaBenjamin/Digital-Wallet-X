package com.ewallet.data.repositories;

import com.ewallet.data.models.NextOfKin;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NextOfKinRepo extends JpaRepository<NextOfKin, Long> {

}
