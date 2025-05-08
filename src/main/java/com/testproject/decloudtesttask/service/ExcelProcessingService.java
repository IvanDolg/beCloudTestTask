package com.testproject.decloudtesttask.service;

import com.testproject.decloudtesttask.dto.UnpResultDto;
import com.testproject.decloudtesttask.exception.FileProcessingException;
import com.testproject.decloudtesttask.exception.СellException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelProcessingService {

    private static final String SHEET_NAME = "Список доменов";
    private static final String ORGANIZATION_HEADER = "Организация";
    private static final String UNP_HEADER = "УНП";

    public UnpResultDto processDomainsFile(byte[] fileBytes) throws FileProcessingException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(fileBytes);
             Workbook workbook = new XSSFWorkbook(bis)) {

            Sheet sheet = workbook.getSheet(SHEET_NAME);
            if (sheet == null) {
                throw new FileProcessingException("Sheet '" + SHEET_NAME + "' not found.");
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new FileProcessingException("Header row is missing.");
            }

            int orgIndex = getColumnIndex(headerRow, ORGANIZATION_HEADER);
            int unpIndex = getColumnIndex(headerRow, UNP_HEADER);

            return extractUnpResult(sheet, orgIndex, unpIndex);

        } catch (IOException e) {
            throw new FileProcessingException("File processing error", e);
        }
    }

    private int getColumnIndex(Row headerRow, String columnName) throws FileProcessingException {
        for (Cell cell : headerRow) {
            String value = getCellValueAsString(cell).trim();
            if (columnName.equalsIgnoreCase(value)) {
                return cell.getColumnIndex();
            }
        }
        throw new FileProcessingException("Missing required column: " + columnName);
    }

    private UnpResultDto extractUnpResult(Sheet sheet, int orgIndex, int unpIndex) {
        List<String> individuals = new ArrayList<>();
        List<String> unps = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            String organization = getCellValueSafe(row.getCell(orgIndex)).trim();
            String unp = getCellValueSafe(row.getCell(unpIndex)).trim();

            if (!unp.isEmpty()) {
                unps.add(unp);
            } else if (!organization.isEmpty()) {
                individuals.add(organization);
            }
        }

        return new UnpResultDto(individuals, unps);
    }

    private String getCellValueSafe(Cell cell) {
        return cell == null ? "" : getCellValueAsString(cell);
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) throw new СellException("Cell is null");

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return DateUtil.isCellDateFormatted(cell)
                        ? cell.getDateCellValue().toString()
                        : (cell.getNumericCellValue() % 1 == 0
                        ? String.valueOf((long) cell.getNumericCellValue())
                        : String.valueOf(cell.getNumericCellValue()));
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                throw new СellException("Unsupported cell type: " + cell.getCellType());
        }
    }
}

