package com.testproject.decloudtesttask.service;

import com.testproject.decloudtesttask.dto.UnpResultDto;
import com.testproject.decloudtesttask.exception.FileProcessingException;
import com.testproject.decloudtesttask.model.Individual;
import com.testproject.decloudtesttask.repository.IndividualRepository;
import com.testproject.decloudtesttask.repository.UnpRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnpService {
    private final ExcelProcessingService excelProcessingService;
    private final IndividualRepository individualRepository;
    private final UnpRecordRepository unpRecordRepository;

    public UnpResultDto processDomainsFile(byte[] fileBytes) throws FileProcessingException {
        return excelProcessingService.processDomainsFile(fileBytes);
    }

    @Transactional
    public UnpResultDto processAndSaveDomainsFile(byte[] fileBytes) throws FileProcessingException {
        UnpResultDto result = excelProcessingService.processDomainsFile(fileBytes);

        if (result.getIndividuals() != null) {
            List<Individual> individuals = result.getIndividuals().stream()
                    .map(fullName -> {
                        Individual individual = new Individual();
                        individual.setFullName(fullName);
                        return individual;
                    })
                    .collect(Collectors.toList());
            individualRepository.saveAll(individuals);
        }

        if (result.getUnp() != null) {
            for (String unp : result.getUnp()) {
                unpRecordRepository.insertIgnoreConflict(unp);
            }
        }

        return result;
    }
}
