package com.project.delivery.controller;

import com.project.delivery.dto.GetAllStoresResponseDto;
import com.project.delivery.service.StoreService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api/stores")
@RestController
public class StoreController {
    private final StoreService storeService;
    @GetMapping("/{categoryId}")
    public ResponseEntity<GetAllStoresResponseDto> getAllStores(@PathVariable(value = "categoryId") Long id) {
        return ResponseEntity.ok(new GetAllStoresResponseDto(storeService.findAllStoreByCategoryId(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<GetAllStoresResponseDto> getAllStoresBySearch(@RequestParam("q") String search) {
        return ResponseEntity.ok(new GetAllStoresResponseDto(storeService.findAllStoreBySearch(search)));
    }
}
