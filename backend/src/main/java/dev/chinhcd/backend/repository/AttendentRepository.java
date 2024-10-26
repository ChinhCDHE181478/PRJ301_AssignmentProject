package dev.chinhcd.backend.repository;

import dev.chinhcd.backend.models.Attendent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendentRepository extends JpaRepository<Attendent, Integer> {
}
