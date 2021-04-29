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
                    //���� ��
//                    int cells = row.getPhysicalNumberOfCells();
//                    for (columnindex = 0; columnindex <= cells; columnindex++){
//                        //������ �д´�
//                        XSSFCell cell = row.getCell(columnindex);
//                        String value  = getValue(cell);
//                        System.out.println(rowindex+"�� �� : "+columnindex+"�� �� ����: "+value);
//                    }
                    //�ε��� 1 : ��� , �ε��� 2 : ����   
                    XSSFCell word_cell = row.getCell(2);
                    String word_value  = getValue(word_cell); 
                    String[] word_value_splitzero = word_value.split("0"); // �� ��ü�� �� �̿�����.
                    
                    XSSFCell grade_cell = row.getCell(1);
                    String grade_value = getValue(grade_cell);
                    
                    //�긦 split "0"�ؼ� ���̰� 1�̸� �״�� �����ϰ� ���̰� 2�̸� grade_value�� "�������Ǿ�" ��� �ֱ�
                    
                    //���ϴ� �κ�.
                    for (int i = 0; i < firstMorps.length; i++) {
                    	// "/"�� ���ø� �Ҳ��� 0��°�� origin_word �� 1��°�� �ܾ� ����
                    	String origin_word = "";
                    	
                    	String[] firstMorp_arr = firstMorps[i].split("/");
                    	if (firstMorp_arr[1] == "VV") { //��� ���� ���� ���
                    		origin_word = firstMorp_arr[0] + "��";
                    	}
                    	else {
                    		origin_word = firstMorp_arr[0];
                    	}
                    	
                    	//ã�� ���.
                    	if (origin_word == word_value_splitzero[0]) {
                    		//����� ����� �߰�
                    		//hashSet���� �ؼ� key�� origin_word, value�� grade_value����
                    		result.add(origin_word + "\t" + grade_value);
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
