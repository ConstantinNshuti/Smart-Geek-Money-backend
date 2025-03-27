package com.geekinstitut.smartmoney.service;

import com.geekinstitut.smartmoney.entity.AppSetting;
import com.geekinstitut.smartmoney.repository.AppSettingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppSettingService {

    private final AppSettingRepository appSettingRepository;

    public List<AppSetting> getAllAppSettings() {
        return appSettingRepository.findAll();
    }

    public AppSetting getAppSettingById(UUID id) {
        return appSettingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("AppSetting not found with id: " + id)
        );
    }

    public AppSetting createOrUpdate(AppSetting appSetting) {
        return appSettingRepository.save(appSetting);
    }

    public void deleteAppSettingById(UUID id) {
        AppSetting appSetting = getAppSettingById(id);
        appSettingRepository.delete(appSetting);
    }


}