package wordgrader;

import java.io.FileInputStream;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SentenceGrader {
	
	String url;
	
	public SentenceGrader() {
		url = "";
	}
	
	public SentenceGrader(String url) {
		this.url = url;
	}
	
	public LinkedHashSet<String> analyze(String[] firstMorps) { //HashSet<String>[]
		
		LinkedHashSet<String> result = new LinkedHashSet<String>();
		
		try {
			FileInputStream file = new FileInputStream(url); //data/grades.xlsx
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            String origin_word = "";
            String[] firstMorp_arr;
            for (int i = 0; i < firstMorps.length; i++) {
            	firstMorp_arr = firstMorps[i].split("/");
            	//VV, VA, VXV, VXA, VCP, VCN
            	if (firstMorp_arr[2].equals("VV") ||
            			firstMorp_arr[2].equals("VA") ||
            			firstMorp_arr[2].equals("VXV") ||
            			firstMorp_arr[2].equals("VXA") ||
            			firstMorp_arr[2].equals("VCP") ||
            			firstMorp_arr[2].equals("VCN")) { //모든 동사 종류 경우
            		origin_word = firstMorp_arr[1] + "다";
            	}
            	else {
            		origin_word = firstMorp_arr[1];
            	}
            	
            	int rows = sheet.getPhysicalNumberOfRows();
            	for (int rowindex = 0; rowindex < rows; rowindex++) {
                    //행을읽는다
                    XSSFRow row = sheet.getRow(rowindex);
                    if (row != null){
                        //인덱스 1 : 등급 , 인덱스 2 : 어휘   
                        XSSFCell word_cell = row.getCell(2);
                        String word_value  = getValue(word_cell); 
                        String[] word_value_splitzero = word_value.split("0"); // 이 자체로 값 이용하자.
                        
                        XSSFCell grade_cell = row.getCell(1);
                        String grade_value = getValue(grade_cell);
                        
                        //비교하는 부분.
                        if (origin_word.equals(word_value_splitzero[0])) {
                    		if (word_value_splitzero.length == 1) {
                    			result.add(origin_word + " / " + grade_value + " - ( " + firstMorp_arr[3] + " )");
                    		}
                    		else {
                    			result.add(origin_word + " / " + "동음이의어" + " - ( " + firstMorp_arr[3] + " ) *");
                    		}
                    		break;
                    	}
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
            case FORMULA:
                return cell.getCellFormula();
            case NUMERIC:
            	return cell.getNumericCellValue()+"";
            case STRING:
            	return cell.getStringCellValue()+"";
            case BLANK:
            	return cell.getBooleanCellValue()+"";
            case ERROR:
            	return cell.getErrorCellValue()+"";
            default:
            	return "";
            }
        }
	}
}
