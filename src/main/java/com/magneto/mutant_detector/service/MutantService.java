package com.magneto.mutant_detector.service;

import com.magneto.mutant_detector.model.DnaRecord;
import com.magneto.mutant_detector.repository.DnaRecordRepository;
import com.magneto.mutant_detector.util.MutantDetector;
import com.magneto.mutant_detector.util.DnaHashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MutantService {

    private final DnaRecordRepository dnaRecordRepository;

    @Autowired
    public MutantService(DnaRecordRepository dnaRecordRepository) {
        this.dnaRecordRepository = dnaRecordRepository;
    }

    public boolean isMutant(String[] dna) {
        // Calcular el hash
        String dnaHash = calculateDnaHash(dna);


        DnaRecord found = dnaRecordRepository.findByDnaHash(dnaHash);
        if (found != null) {
            return found.isMutant();
        }

        // Usar el algoritmo para verificar si es mutante
        boolean isMutant = MutantDetector.isMutant(dna);

        // Guardar el resultado en la base de datos
        DnaRecord dnaRecord = new DnaRecord();
        dnaRecord.setDnaHash(dnaHash);
        dnaRecord.setMutant(isMutant);
        dnaRecordRepository.save(dnaRecord);

        return isMutant;
    }


    private String calculateDnaHash(String[] dna) {
        return DnaHashUtil.sha256Hex(dna);
    }
}
