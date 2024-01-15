package com.project.delivery.repository;

import com.project.delivery.dto.StoreDto;
import com.project.delivery.entity.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT new com.project.delivery.dto.StoreDto(s.id, s.ownerName, s.title, s.openTime, s.closeTime, s.address, s.phone, s.status.name, c.name) " +
            "FROM Store s LEFT JOIN s.category c " +
            "WHERE s.category.id = :categoryId AND s.deletedAt IS NULL")
    List<StoreDto> findAllByCategoryId(@Param("categoryId") Long categoryId, Sort sort);

    @Query("SELECT new com.project.delivery.dto.StoreDto(s.id, s.ownerName, s.title, s.openTime, s.closeTime, s.address, s.phone, s.status.name, c.name) " +
            "FROM Store s LEFT JOIN s.category c " +
            "WHERE s.title LIKE %:search% AND s.deletedAt IS NULL")
    List<StoreDto> findAllBySearch(String search, Sort sort);
}

// AND WHERE s.deletedAt IS NULL GROUP BY s.status
