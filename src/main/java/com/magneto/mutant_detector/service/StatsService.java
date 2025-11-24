package com.magneto.mutant_detector.service;

import com.magneto.mutant_detector.dto.StatsResponse;
import com.magneto.mutant_detector.repository.DnaRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final DnaRecordRepository dnaRecordRepository;

    @Autowired
    public StatsService(DnaRecordRepository dnaRecordRepository) {
        this.dnaRecordRepository = dnaRecordRepository;
    }

    public StatsResponse getStats() {
        long countMutantDna = dnaRecordRepository.countByMutantTrue();
        long countHumanDna = dnaRecordRepository.countByMutantFalse();
        double ratio = countHumanDna == 0 ? 0 : (double) countMutantDna / countHumanDna;

        return new StatsResponse(countMutantDna, countHumanDna, ratio);
    }
}
