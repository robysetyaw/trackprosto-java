package com.trackprosto.trackprosto.repository;

import com.trackprosto.trackprosto.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
    // Anda bisa menambahkan metode query tambahan di sini jika diperlukan
}

