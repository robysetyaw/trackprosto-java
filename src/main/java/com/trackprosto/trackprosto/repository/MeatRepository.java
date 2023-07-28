
package com.trackprosto.trackprosto.repository;
import com.trackprosto.trackprosto.entity.Meat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeatRepository extends JpaRepository<Meat, String> {
    List<Meat> findByName(String name);
    List<Meat> findByStock(Double stock);
    List<Meat> findByPrice(Double price);
    List<Meat> findByIsActive(Boolean isActive);
}
