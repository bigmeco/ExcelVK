import javafx.event.ActionEvent;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {
    public void Sjitat(ActionEvent actionEvent) throws IOException {
        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream("C:/Users/bigmeco/Documents/ExcelVK/sima_19_spiski.xlsx"));
        System.out.println(   myExcelBook.getSheetAt(0).getRow(1).getCell(0).getHyperlink().getAddress());

    }
}
//