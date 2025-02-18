package ru.showter.springxlsxservice;

import java.io.IOException;
import java.util.PriorityQueue;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    public int findNthMax(MultipartFile file, int n) throws IOException {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(n);

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    int value;
                    if (cell.getCellType() == CellType.NUMERIC) {
                        value = (int) cell.getNumericCellValue();
                    } else if (cell.getCellType() == CellType.FORMULA &&
                            cell.getCachedFormulaResultType() == CellType.NUMERIC) {
                        value = (int) cell.getNumericCellValue();
                    } else {
                        continue;
                    }

                    if (minHeap.size() < n) {
                        minHeap.offer(value);
                    } else if (value > minHeap.peek()) {
                        minHeap.poll();
                        minHeap.offer(value);
                    }
                }
            }
        }

        if (minHeap.size() < n) {
            throw new IllegalArgumentException("Not enough numbers in the file to find the N-th maximum");
        }

        return minHeap.peek();
    }
}
