package com.trackprosto.trackprosto.repository;

import com.trackprosto.trackprosto.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,String> {

    @Query(value = "SELECT id, address, fullname,phone_number, company_id, created_at, updated_at, created_by, updated_by, debt FROM customers WHERE fullname = :name",
            nativeQuery = true)
    Customer findbyName(String name);

    List<Customer> findByFullname(String fullname);

    List<Customer> findByCompanyId(String companyId);

}
