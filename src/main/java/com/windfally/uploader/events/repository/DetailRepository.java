package com.windfally.uploader.events.repository;

import com.windfally.uploader.events.domain.Detail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailRepository extends JpaRepository<Detail,Long> {
}
