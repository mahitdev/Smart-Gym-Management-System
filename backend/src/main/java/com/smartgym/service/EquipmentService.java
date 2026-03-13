package com.smartgym.service;

import com.smartgym.model.Equipment;
import com.smartgym.model.EquipmentMaintenance;
import com.smartgym.repository.EquipmentMaintenanceRepository;
import com.smartgym.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentMaintenanceRepository maintenanceRepository;

    public EquipmentService(EquipmentRepository equipmentRepository,
                            EquipmentMaintenanceRepository maintenanceRepository) {
        this.equipmentRepository = equipmentRepository;
        this.maintenanceRepository = maintenanceRepository;
    }

    public Equipment save(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    public EquipmentMaintenance schedule(EquipmentMaintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    public List<EquipmentMaintenance> maintenanceList() {
        return maintenanceRepository.findAll();
    }
}
