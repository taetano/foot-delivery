package com.project.delivery.controller;

import com.project.delivery.dto.GetAllStoresResponseDto;
import com.project.delivery.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/api/stores")
@RestController
public class StoreController {
    private final StoreService storeService;
    @GetMapping("/{categoryId}")
    public ResponseEntity<GetAllStoresResponseDto> getAllStores(@PathVariable("categoryId") Long id) {
        return ResponseEntity.ok(new GetAllStoresResponseDto(storeService.findAllStoreByCategoryId(id)));
    }
}
