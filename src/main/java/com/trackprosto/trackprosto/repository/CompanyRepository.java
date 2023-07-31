package com.trackprosto.trackprosto.repository;

import com.trackprosto.trackprosto.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String> {

    List<Company> findByCompanyName(String companyName);
    @Query(value = "SELECT * FROM companies", nativeQuery = true)
    List<Company> findAll();

    @Query(value = "SELECT * FROM companies WHERE id = :id", nativeQuery = true)
    Optional<Company> findById(@Param("id") String id);

    @Query(value = "INSERT INTO companies (id, name, address) VALUES (:id, :name, :address)", nativeQuery = true)
    Company save(@Param("id") String id, @Param("name") String name, @Param("address") String address);

    @Query(value = "DELETE FROM companies WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") String id);
}

