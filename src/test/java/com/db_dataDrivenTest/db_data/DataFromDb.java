package com.db_dataDrivenTest.db_data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;
/**
 * 
 * This class reads data from DB
 * 
 * @author Saidamma
 * 
 */
public class DataFromDb {

	@Test
	public void dataFromDbTest() throws Exception {
		List<String> l = new ArrayList<String>();
		Map<String, List<Object>> resultMap1 = new HashMap<String, List<Object>>();
		
	
		String sql = "SELECT M.* FROM MEMBER M,AUTHENTICATION A WHERE member_type = 'SEEKER' AND  M.authentication_id = A.id ORDER BY 1 DESC LIMIT 10";
		String [] a = {"username","id"};
		resultMap1=Dbconnection.getDataFromDb(sql,  a);
		System.out.println("username:::::"+resultMap1.get("username"));
		
		List<Object> result=resultMap1.get("username");
		
		for(int i=0;i<result.size();i++){
			System.out.println("***************"+result.get(i));
			
		}
		//System.out.println("Id:::::"+resultMap1.get("id"));
	ReadWriteDataFromExcel.setExcelFile("a.xlsx","USERNAME");
	ReadWriteDataFromExcel.setCellData(result,"a.xlsx");
	l=ReadWriteDataFromExcel.getCellData("USERNAME");
	for(int i=0;i<l.size();i++){
		System.out.println("***************"+l.get(i));
		
	
		
		
	}

}
}
