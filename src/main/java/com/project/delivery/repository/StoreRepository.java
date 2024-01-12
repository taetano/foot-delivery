package com.project.delivery.repository;

import com.project.delivery.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAllByCategoryId(Long categoryId);
}
