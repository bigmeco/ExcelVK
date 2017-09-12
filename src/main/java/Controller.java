import javafx.event.ActionEvent;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class Controller {
    public void Sjitat(ActionEvent actionEvent) throws IOException {
        XSSFWorkbook myExcelBook = null;
        myExcelBook = new XSSFWorkbook(new FileInputStream("C:/Users/bigmeco/Documents/ExcelVK/sima_19_spiski.xlsx"));
        myExcelBook.getSheetAt(0);
        int x = 1;
        for (Row row : myExcelBook.getSheetAt(0)) {
            x++;
            try {
                System.out.println (myExcelBook.getSheetAt(0).getRow(x).getCell(0).getHyperlink().getAddress());
//                CellReference cellRef = new CellReference(row.getRowNum(), myExcelBook.getSheetAt(0).getRow(x).getCell(0).getColumnIndex());
//                System.out.print(cellRef.formatAsString());
            } catch (Exception e) {}
        }
        myExcelBook.close();
    }

    public static String getCalltxt(Cell cell) {
        String GipSil = null;
        switch (cell.getCellTypeEnum()) {
            case STRING:
                System.out.println(cell.getRichStringCellValue().getString());
                try {
                    GipSil = (cell.getHyperlink().getAddress());
                } catch (Exception e) {break;}

                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    System.out.println(cell.getDateCellValue());
                } else {
                    System.out.println(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                System.out.println(cell.getBooleanCellValue());
                break;
            case FORMULA:
                System.out.println(cell.getCellFormula());
                break;
            case BLANK:
                System.out.println();
                break;
            default:
                break;
        }
        return GipSil;
    }
}
//