package File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelData {

	public static void main(String[] args) throws IOException {
		ReadExcelData data = new ReadExcelData();
		ArrayList<String> l = data.getData("AddBookAPI");
		System.out.println("test case name is " + l.get(0));
		System.out.println("Data set are : ");
		for (int i = 1; i < l.size(); i++)
			System.out.println(l.get(i));
	}

	public ArrayList<String> getData(String testcasename) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		File file = new File("resources/TestData.xlsx");
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		int sheetcount = wb.getNumberOfSheets();
		for (int i = 0; i < sheetcount; i++) {
			if (wb.getSheetName(i).equalsIgnoreCase("Sheet1")) {
				Sheet sheet = wb.getSheetAt(i);
				Iterator<Row> row = sheet.iterator();
				Row firstrow = row.next();
				Iterator<Cell> cell = firstrow.cellIterator();
				int k = 0, column = 0;
				while (cell.hasNext()) {
					Cell eachcell = cell.next();
					if (eachcell.getStringCellValue().equalsIgnoreCase("TestCases")) {
						column = k;
					}
					k++;
				}
				System.out.println(column);
				while (row.hasNext()) {
					Row r = row.next();
					if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testcasename)) {
						Iterator<Cell> cv = r.cellIterator();
						while (cv.hasNext()) {
							Cell c = cv.next();
							if (c.getCellType() == CellType.STRING) {
							list.add(c.getStringCellValue());
							}
							else {
							list.add(NumberToTextConverter.toText(c.getNumericCellValue()));
							}
						}
					}
				}
			}
		}
		return list;

	}

}
