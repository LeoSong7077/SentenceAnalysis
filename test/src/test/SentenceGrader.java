package test;

import java.io.FileInputStream;
import java.util.HashSet;

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
	
	public HashSet<String>[] analyze(String[] firstMorps) {
		HashSet[] result = new HashSet[2];
		
		HashSet<String> match = new HashSet<String>();
		HashSet<String> multiple = new HashSet<String>();
		
		try {
            FileInputStream file = new FileInputStream(url); //data/grades.xlsx
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            
            int rowindex = 0;
            XSSFSheet sheet = workbook.getSheetAt(0);
            //행의 수
            int rows = sheet.getPhysicalNumberOfRows();
            for (rowindex = 0; rowindex < rows; rowindex++) {
                //행을읽는다
                XSSFRow row = sheet.getRow(rowindex);
                if (row !=null){
                    //인덱스 1 : 등급 , 인덱스 2 : 어휘   
                    XSSFCell word_cell = row.getCell(2);
                    String word_value  = getValue(word_cell); 
                    String[] word_value_splitzero = word_value.split("0"); // 이 자체로 값 이용하자.
                    
                    XSSFCell grade_cell = row.getCell(1);
                    String grade_value = getValue(grade_cell);
                    
                    //비교하는 부분.
                    for (int i = 0; i < firstMorps.length; i++) {
                    	// "/"로 스플릿 할꺼고 0번째가 origin_word 고 1번째가 단어 종류
                    	String origin_word = "-";
                    	
                    	String[] firstMorp_arr = firstMorps[i].split("/");
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
                    	
                    	//찾은 경우. + word_value_splitzero의 length가 2인경우
                    	if (origin_word.equals(word_value_splitzero[0])) {
                    		if (word_value_splitzero.length == 1) {
                    			match.add(origin_word + " / " + grade_value + " - ( " + firstMorp_arr[3] + " )");
                    		}
                    		else {
                    			multiple.add(origin_word + " / " + "동음이의어*" + " - ( " + firstMorp_arr[3] + " )"); //firstMorp_arr[1] + " / " + "동음이의어*" 
                    			//결과가 복수인 경우, 근데 아예 꼬꼬마에서 찾을 수 없는 경우는 .. 일단 WordGrader에서 출력 해줘야 할듯하다.
                    			//근데 이러면 입력한 모든 값들이 결과가 되긴한다. 일단 완벽한 문장이라고 믿고 해보자. 오차 지켜보자.
                    		}
                    	}
                    }
                }
            }
            result[0] = match;
            result[1] = multiple;
 
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
