package com.project.delivery.repository;

import com.project.delivery.dto.StoreDto;
import com.project.delivery.entity.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT new com.project.delivery.dto.StoreDto(s.id, s.ownerName, s.title, s.openTime, s.closeTime, s.address, s.phone, s.status, c) " +
            "FROM Store s INNER JOIN s.category c " +
            "WHERE s.deletedAt IS NULL GROUP BY s.status")
    List<StoreDto> findAllByCategoryId(Long categoryId);
}
