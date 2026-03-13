package com.smartgym.controller;

import com.smartgym.model.Equipment;
import com.smartgym.model.EquipmentMaintenance;
import com.smartgym.service.EquipmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @PostMapping
    public Equipment create(@RequestBody Equipment equipment) {
        return equipmentService.save(equipment);
    }

    @GetMapping
    public List<Equipment> list() {
        return equipmentService.findAll();
    }

    @PostMapping("/maintenance")
    public EquipmentMaintenance scheduleMaintenance(@RequestBody EquipmentMaintenance maintenance) {
        return equipmentService.schedule(maintenance);
    }

    @GetMapping("/maintenance")
    public List<EquipmentMaintenance> maintenance() {
        return equipmentService.maintenanceList();
    }
}
