package com.trackprosto.trackprosto.controller;


import com.trackprosto.trackprosto.model.dto.StockMovementReportDTO;
import com.trackprosto.trackprosto.model.entity.Meat;
import com.trackprosto.trackprosto.model.response.TemplateResponse;
import com.trackprosto.trackprosto.service.MeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public TemplateResponse<List<Meat>> getAllMeats() {
        TemplateResponse<List<Meat>> res = new TemplateResponse<List<Meat>>();
        res.message = "succes";
        res.data = meatService.findAll();
        return res;
    }


    @PostMapping
    public TemplateResponse<Meat> createMeat(@RequestBody Meat meat) {
        TemplateResponse res = new TemplateResponse<Meat>();
        res.message = "succes insert meat";
        res.data = meatService.save(meat);
        return res;
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

    @GetMapping("/stock-movement")
    public TemplateResponse<List<StockMovementReportDTO>> getStockMovementReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        TemplateResponse<List<StockMovementReportDTO>> res = new TemplateResponse<List<StockMovementReportDTO>>();
        res.message = "succes";
        res.data = meatService.generateStockMovement(startDate, endDate);
        return res;
    }

}
