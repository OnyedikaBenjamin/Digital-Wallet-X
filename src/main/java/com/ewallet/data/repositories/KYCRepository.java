package com.ewallet.data.repositories;

import com.ewallet.data.models.KYC;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KYCRepository extends JpaRepository<KYC, Long>{
}
