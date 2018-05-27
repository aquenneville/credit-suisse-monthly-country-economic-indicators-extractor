package creditsuisse.markets.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class POIExcelService {

    public static void saveTextInExcelFile(String origFilename, int page, String content) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("page_" + page);
        String baseFilename = FilenameUtils.removeExtension(origFilename);
        baseFilename = "./page-" + page + "_" + baseFilename + ".xls";		
        String[] stringList = content.split("\n");

        int rowIdx = 0;
        for (String line : stringList) {

            Row row = sheet.createRow(rowIdx++);
            if (hasNumbersInString(line)) {
                String numbers = extractTheNumbersInString(line);
                int position = line.indexOf(numbers);

                if ((position == 0 || position == -1)
                        && numbers.length() < line.length()) {
                    Cell cell = row.createCell(0);
                    cell.setCellValue(line);

                } else if (hasNumbersInString(line)
                        && numbers.length() == line.length()) {

                    Cell cell = row.createCell(0);
                    cell.setCellValue("");
                    String[] numberArray = numbers.split("\\s");
                    int idx = 1;
                    for (String number : numberArray) {
                        cell = row.createCell(idx);
                        cell.setCellValue(number);
                        idx++;
                    }
                } else {
                    Cell cell = row.createCell(0);
                    cell.setCellValue(line.substring(0, position - 1));
                    String[] numberArray = numbers.split("\\s");
                    int idx = 1;
                    for (String number : numberArray) {
                        cell = row.createCell(idx);
                        cell.setCellValue(number);
                        idx++;
                    }
                }
            } else {
                Cell cell = row.createCell(0);
                cell.setCellValue(line);
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(new File(baseFilename));
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean hasNumbersInString(String line) {
        String pattern = "((\\d+(\\.\\d{1,4})?)\\s)";
        boolean result = false;
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);

        if (m.find()) {
            System.out.println("Found value: " + m.group(0));
            result = true;
        } else {
            System.out.println("NO MATCH");
        }
        return result;
    }

    private static String extractTheNumbersInString(String line) {
        String pattern = "na\\s|((-?\\d+[EF]?([\\.|,]\\d{1,4})?)\\s)";
        String result = "";
        System.out.println("Analyzing: " + line);
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);

        while (m.find()) {
            result += m.group();
            System.out.println("Found value: " + result);
        }
        if (result.length() == 0) {
            System.out.println("NO MATCH");
        }
        return result;
    }
}
