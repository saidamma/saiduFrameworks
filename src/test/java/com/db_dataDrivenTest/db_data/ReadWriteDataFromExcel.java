package com.db_dataDrivenTest.db_data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * 
 * This class contains methods to read data from
 * excel sheet and write data into excel sheet
 * 
 * @author Saidamma
 * 
 */
public class ReadWriteDataFromExcel {

	private static XSSFSheet ExcelWSheet;

	private static XSSFWorkbook ExcelWBook;

	private static XSSFCell Cell;

	private static XSSFRow Row;

	// This method is to set the File path and to open the Excel file, Pass
	// Excel Path and Sheetname as Arguments to this method

	public static void setExcelFile(String filename, String SheetName)
			throws Exception {

		try {

			String filePath = System.getProperty("user.dir");
			File file = new File(filePath + "\\" + filename);

			// Open the Excel file

			FileInputStream ExcelFile = new FileInputStream(file);

			// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);

		} catch (Exception e) {

			throw (e);

		}

	}

	// This method is to read the test data from the Excel cell, in this we are
	// passing parameters as Row num and Col num

	public static List<String> getCellData(String sheet) throws Exception {
		List<String> l = new ArrayList<String>();
		try {
			int RowNum = 1;
			int ColNum = 0;
			ExcelWSheet = ExcelWBook.getSheet(sheet);
			int rowCount = ExcelWSheet.getLastRowNum()
					- ExcelWSheet.getFirstRowNum();

			for (int i = 0; i < rowCount; i++) {
				Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

				String CellData = Cell.getStringCellValue();

				l.add(CellData);
				RowNum = RowNum + 1;
			}
		} catch (Exception e) {

			return null;

		}
		return l;

	}

	// This method is to write in the Excel cell, Row num and Col num are the
	// parameters

	public static void setCellData(List<Object> l, String s) throws Exception {

		try {
			int RowNum = 1;
			int ColNum = 0;

			String filePath = System.getProperty("user.dir");
			File file = new File(filePath + "\\" + s);
			for (int i = 0; i < l.size(); i++) {
				String value = (String) l.get(i);
				Row = ExcelWSheet.getRow(RowNum);
				if (Row == null) {
					Row = ExcelWSheet.createRow(RowNum);
				}
				Cell = Row.getCell(ColNum);

				if (Cell == null) {

					Cell = Row.createCell(ColNum);

					Cell.setCellValue(value);

				} else {

					Cell.setCellValue(value);

				}

				RowNum = RowNum + 1;
			}
			// Constant variables Test Data path and Test Data file name

			FileOutputStream fileOut = new FileOutputStream(file);

			ExcelWBook.write(fileOut);

			fileOut.flush();

			fileOut.close();

		} catch (Exception e) {

			throw (e);

		}

	}

	public static void setDataRowCell(int RowNum, String s, String data,
			int ColNum) throws Exception {

		try {

			String filePath = System.getProperty("user.dir");
			File file = new File(filePath + "\\" + s);

			Row = ExcelWSheet.getRow(RowNum);
			if (Row == null) {
				Row = ExcelWSheet.createRow(RowNum);
			}
			Cell = Row.getCell(ColNum);

			if (Cell == null) {

				Cell = Row.createCell(ColNum);

				Cell.setCellValue(data);

			} else {

				Cell.setCellValue(data);

			}

			// Constant variables Test Data path and Test Data file name

			FileOutputStream fileOut = new FileOutputStream(file);

			ExcelWBook.write(fileOut);

			fileOut.flush();

			fileOut.close();

		} catch (Exception e) {

			throw (e);

		}
	}
}
