package com.trackprosto.trackprosto.controller;


import com.trackprosto.trackprosto.model.entity.Meat;
import com.trackprosto.trackprosto.service.MeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meats")
public class MeatController {

    private final MeatService meatService;

    @Autowired
    public MeatController(MeatService meatService) {
        this.meatService = meatService;
    }

    @GetMapping
    public List<Meat> getAllMeats() {
        return meatService.findAll();
    }


    @PostMapping
    public Meat createMeat(@RequestBody Meat meat) {
        return meatService.save(meat);
    }

    @PutMapping("/{id}")
    public Meat updateMeat(@PathVariable String id, @RequestBody Meat updatedMeat) {
        Meat meat = meatService.findById(id).orElse(null);
        if (meat != null) {
            updatedMeat.setId(id);
            return meatService.save(updatedMeat);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteMeat(@PathVariable String id) {
        meatService.deleteById(id);
    }

    @GetMapping("/name/{name}")
    public List<Meat> getMeatByName(@PathVariable String name) {
        return meatService.findByName(name);
    }

    // continue with the same pattern for stock, price, and isActive
}
