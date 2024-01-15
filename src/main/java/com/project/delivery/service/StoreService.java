package com.project.delivery.service;

import com.project.delivery.dto.StoreDto;
import com.project.delivery.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public List<StoreDto> findAllStoreByCategoryId(Long categoryId) {
        return storeRepository.findAllByCategoryId(categoryId);
    }
}
