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
            //���� ��
            int rows = sheet.getPhysicalNumberOfRows();
            for (rowindex = 0; rowindex < rows; rowindex++) {
                //�����д´�
                XSSFRow row = sheet.getRow(rowindex);
                if (row !=null){
                    //�ε��� 1 : ��� , �ε��� 2 : ����   
                    XSSFCell word_cell = row.getCell(2);
                    String word_value  = getValue(word_cell); 
                    String[] word_value_splitzero = word_value.split("0"); // �� ��ü�� �� �̿�����.
                    
                    XSSFCell grade_cell = row.getCell(1);
                    String grade_value = getValue(grade_cell);
                    
                    //���ϴ� �κ�.
                    for (int i = 0; i < firstMorps.length; i++) {
                    	// "/"�� ���ø� �Ҳ��� 0��°�� origin_word �� 1��°�� �ܾ� ����
                    	String origin_word = "-";
                    	
                    	String[] firstMorp_arr = firstMorps[i].split("/");
                    	//VV, VA, VXV, VXA, VCP, VCN
                    	if (firstMorp_arr[2].equals("VV") ||
                    			firstMorp_arr[2].equals("VA") ||
                    			firstMorp_arr[2].equals("VXV") ||
                    			firstMorp_arr[2].equals("VXA") ||
                    			firstMorp_arr[2].equals("VCP") ||
                    			firstMorp_arr[2].equals("VCN")) { //��� ���� ���� ���
                    		origin_word = firstMorp_arr[1] + "��";
                    	}
                    	else {
                    		origin_word = firstMorp_arr[1];
                    	}
                    	
                    	//ã�� ���. + word_value_splitzero�� length�� 2�ΰ��
                    	if (origin_word.equals(word_value_splitzero[0])) {
                    		if (word_value_splitzero.length == 1) {
                    			match.add(origin_word + " / " + grade_value + " - ( " + firstMorp_arr[3] + " )");
                    		}
                    		else {
                    			multiple.add(origin_word + " / " + "�������Ǿ�*" + " - ( " + firstMorp_arr[3] + " )"); //firstMorp_arr[1] + " / " + "�������Ǿ�*" 
                    			//����� ������ ���, �ٵ� �ƿ� ���������� ã�� �� ���� ���� .. �ϴ� WordGrader���� ��� ����� �ҵ��ϴ�.
                    			//�ٵ� �̷��� �Է��� ��� ������ ����� �Ǳ��Ѵ�. �ϴ� �Ϻ��� �����̶�� �ϰ� �غ���. ���� ���Ѻ���.
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
            //Ÿ�Ժ��� ���� �б�
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
