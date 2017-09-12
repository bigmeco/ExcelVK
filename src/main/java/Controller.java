import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class Controller {
    public TextField AddressExcle;

    public void Sjitat(ActionEvent actionEvent) throws IOException {
        XSSFWorkbook myExcelBook = null;
        String addr= AddressExcle.getText()+".xlsx";
        myExcelBook = new XSSFWorkbook(new FileInputStream(addr));
        myExcelBook.getSheetAt(0);
        int x = 1;
        String UrlAddress= "";
        for (Row row : myExcelBook.getSheetAt(0)) {
            x++;
            try {
                if(Objects.equals(myExcelBook.getSheetAt(0).getRow(x).getCell(0).getHyperlink().getAddress(), UrlAddress)) {}
                else {UrlAddress = (myExcelBook.getSheetAt(0).getRow(x).getCell(0).getHyperlink().getAddress());
                    System.out.println(UrlAddress);}
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