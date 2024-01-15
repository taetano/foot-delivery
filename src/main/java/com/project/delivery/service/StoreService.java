package com.project.delivery.service;

import com.project.delivery.dto.StoreDto;
import com.project.delivery.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public List<StoreDto> findAllStoreByCategoryId(Long categoryId) {
        return storeRepository.findAllByCategoryId(categoryId, Sort.by(Sort.Direction.DESC, "status"));
    }
}
