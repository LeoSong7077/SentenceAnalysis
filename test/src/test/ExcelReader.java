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
		//��ġ�°� ���� Set���� ����!
		ArrayList<String> result = new ArrayList<String>();
		
		try {
            FileInputStream file = new FileInputStream(url); //data/grades.xlsx
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            
            int rowindex = 0;
            int columnindex = 0;
            //��Ʈ �� (ù��°���� �����ϹǷ� 0�� �ش�)
            //���� �� ��Ʈ�� �б����ؼ��� FOR���� �ѹ��� �����ش�
            XSSFSheet sheet = workbook.getSheetAt(0);
            //���� ��
            int rows = sheet.getPhysicalNumberOfRows();
            for(rowindex = 0; rowindex < rows; rowindex++){
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
                    	if (firstMorp_arr[2].equals("VV") || firstMorp_arr[2].equals("VA") ) { //��� ���� ���� ���
                    		origin_word = firstMorp_arr[1] + "��";
                    	}
                    	else {
                    		origin_word = firstMorp_arr[1];
                    	}
                    	
                    	//ã�� ���.
                    	if (origin_word.equals(word_value_splitzero[0]) ) {
                    		//����� ����� �߰�
                    		//hashSet���� �ؼ� key�� origin_word, value�� grade_value����
                    		
                    		result.add(origin_word + " / " + grade_value);
                    	}
                    	//result�� ����
                    	//���� ���� ��� ��ο� �ֱ�
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
