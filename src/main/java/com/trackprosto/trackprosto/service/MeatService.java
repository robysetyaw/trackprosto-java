package com.trackprosto.trackprosto.service;

import com.trackprosto.trackprosto.entity.Meat;
import com.trackprosto.trackprosto.repository.MeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeatService {

    private final MeatRepository meatRepository;

    @Autowired
    public MeatService(MeatRepository meatRepository) {
        this.meatRepository = meatRepository;
    }

    public List<Meat> findAll() {
        return meatRepository.findAll();
    }

    public Optional<Meat> findById(String id) {
        return meatRepository.findById(id);
    }

    public Meat save(Meat meat) {
        return meatRepository.save(meat);
    }

    public void deleteById(String id) {
        meatRepository.deleteById(id);
    }

    public List<Meat> findByName(String name) {
        return meatRepository.findByName(name);
    }

    public List<Meat> findByStock(Double stock) {
        return meatRepository.findByStock(stock);
    }

    public List<Meat> findByPrice(Double price) {
        return meatRepository.findByPrice(price);
    }

    public List<Meat> findByIsActive(Boolean isActive) {
        return meatRepository.findByIsActive(isActive);
    }
}
