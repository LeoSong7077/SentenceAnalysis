package test;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	String url;
	
	public ExcelReader() {
		url = "";
	}
	
	public ExcelReader(String url) {
		this.url = url;
	}
	
	public ArrayList<String> analyze(String[] firstMorps) {
		//겹치는값 없게 Set으로 하자!
		ArrayList<String> result = new ArrayList<String>();
		
		try {
            FileInputStream file = new FileInputStream(url); //data/grades.xlsx
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            
            int rowindex = 0;
            int columnindex = 0;
            //시트 수 (첫번째에만 존재하므로 0을 준다)
            //만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
            XSSFSheet sheet = workbook.getSheetAt(0);
            //행의 수
            int rows = sheet.getPhysicalNumberOfRows();
            for(rowindex = 0; rowindex < rows; rowindex++){
                //행을읽는다
                XSSFRow row = sheet.getRow(rowindex);
                if (row !=null){
                    //셀의 수
//                    int cells = row.getPhysicalNumberOfCells();
//                    for (columnindex = 0; columnindex <= cells; columnindex++){
//                        //셀값을 읽는다
//                        XSSFCell cell = row.getCell(columnindex);
//                        String value  = getValue(cell);
//                        System.out.println(rowindex+"번 행 : "+columnindex+"번 열 값은: "+value);
//                    }
                    //인덱스 1 : 등급 , 인덱스 2 : 어휘   
                    XSSFCell word_cell = row.getCell(2);
                    String word_value  = getValue(word_cell); 
                    String[] word_value_splitzero = word_value.split("0"); // 이 자체로 값 이용하자.
                    
                    XSSFCell grade_cell = row.getCell(1);
                    String grade_value = getValue(grade_cell);
                    
                    //얘를 split "0"해서 길이가 1이면 그대로 유지하고 길이가 2이면 grade_value에 "동음이의어" 결과 넣기
                    
                    //비교하는 부분.
                    for (int i = 0; i < firstMorps.length; i++) {
                    	// "/"로 스플릿 할꺼고 0번째가 origin_word 고 1번째가 단어 종류
                    	String origin_word = "";
                    	
                    	String[] firstMorp_arr = firstMorps[i].split("/");
                    	if (firstMorp_arr[1] == "VV") { //모든 동사 종류 경우
                    		origin_word = firstMorp_arr[0] + "다";
                    	}
                    	else {
                    		origin_word = firstMorp_arr[0];
                    	}
                    	
                    	//찾은 경우.
                    	if (origin_word == word_value_splitzero[0]) {
                    		//출력할 결과값 추가
                    		//hashSet으로 해서 key를 origin_word, value를 grade_value하자
                    		result.add(origin_word + "\t" + grade_value);
                    	}
                    	
                    	//result값 리턴
                    	//엑셀 파일 상대 경로에 넣기
                    	
                    }
                }
            }
 
        } catch(Exception e) {
            e.printStackTrace();
        }
		return result;
	}
	
	public String getValue(XSSFCell cell) {
		if (cell == null){
            return "";
        } else {
            //타입별로 내용 읽기
            switch (cell.getCellType()){
            case XSSFCell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case XSSFCell.CELL_TYPE_NUMERIC:
            	return cell.getNumericCellValue()+"";
            case XSSFCell.CELL_TYPE_STRING:
            	return cell.getStringCellValue()+"";
            case XSSFCell.CELL_TYPE_BLANK:
            	return cell.getBooleanCellValue()+"";
            case XSSFCell.CELL_TYPE_ERROR:
            	return cell.getErrorCellValue()+"";
            default:
            	return "";
            }
            
        }
	}
}
