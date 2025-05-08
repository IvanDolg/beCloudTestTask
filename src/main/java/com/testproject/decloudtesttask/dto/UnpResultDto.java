package com.testproject.decloudtesttask.dto;

import lombok.Data;


import java.util.List;

@Data
public class UnpResultDto {
    private List<String> individuals;
    private List<String> unp;

    public UnpResultDto(List<String> individuals, List<String> unp) {
        this.individuals = individuals;
        this.unp = unp;
    }
}