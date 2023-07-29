package com.trackprosto.trackprosto.repository;

import com.trackprosto.trackprosto.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, String> {

    List<Company> findByCompanyName(String companyName);
}

