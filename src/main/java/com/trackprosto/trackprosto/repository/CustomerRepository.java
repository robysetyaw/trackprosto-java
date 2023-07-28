package com.trackprosto.trackprosto.repository;

import com.trackprosto.trackprosto.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,String> {
    List<Customer> findByFullname(String fullname);
    List<Customer> findByCompanyId(String companyId);

}
