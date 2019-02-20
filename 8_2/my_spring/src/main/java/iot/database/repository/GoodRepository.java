package iot.database.repository;

import iot.database.domain.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface GoodRepository extends JpaRepository<Good, Long> {
}
