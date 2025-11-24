package com.magneto.mutant_detector.repository;

import com.magneto.mutant_detector.model.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long> {

    boolean existsByDnaHash(String dnaHash);

    DnaRecord findByDnaHash(String dnaHash);

    long countByMutantTrue();

    long countByMutantFalse();

}
