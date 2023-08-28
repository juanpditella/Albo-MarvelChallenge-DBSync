package com.DbSync.repository;

import com.DbSync.model.entities.LastSyncInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastSyncInfoRepository extends JpaRepository<LastSyncInfo, Long> {
}

