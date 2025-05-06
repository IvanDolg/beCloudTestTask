package com.testproject.decloudtesttask.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UnpResultDto {
    private List<String> individuals;
    private List<String> unp;

    public UnpResultDto(List<String> individuals, List<String> unp) {
        this.individuals = individuals;
        this.unp = unp;
    }

}