package iot.database.repository;

import iot.database.domain.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProviderRepository extends JpaRepository<Provider, Long>{
}
