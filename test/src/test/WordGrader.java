package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.snu.ids.kkma.ma.MorphemeAnalyzer;
import org.snu.ids.kkma.ma.Sentence;

public class WordGrader {

	public String analyze(String s) throws Exception {
		String finalresult = "";
		//String s = "������ ���� �� �������� ��ǳ ������ ������� �ڿ������� ��� ���ϸ� ���ٸ� �����⸦ �߻��ߴ�. �װ� ª�� �Ӹ��� ����ߴ� �������� ���̺����� ��Ÿ�ϰ� ���� �÷��� ����Ʈ�� �� �м����� �ü��� �����Ҵ�.";
		
		// init MorphemeAnalyzer
		MorphemeAnalyzer ma = new MorphemeAnalyzer();

		// create logger, null then System.out is set as a default logger
		//�ΰŸ� �����ϱ�, null �ϸ� �ֿܼ� ǥ��
		ma.createLogger(null);

		List ret = ma.analyze(s);
		ret = ma.postProcess(ret);
		ret = ma.leaveJustBest(ret);
		List stl = ma.divideToSentences(ret);
		
		ExcelReader er = new ExcelReader("data/grades.xlsx");
		
		// print the result
		for( int i = 0; i < stl.size(); i++ ) {
			Sentence st = (Sentence) stl.get(i);
			//System.out.println("===>  " + st.getSentence());
			String[] tempArr = new String[st.size()];
			for( int j = 0; j < st.size(); j++ ) {
				//System.out.println(st.get(j) + "\t" + st.get(j).getFirstMorp());
				tempArr[j] = st.get(j).getFirstMorp().toString();
				
				//�� firstMorp�� ������Ե� �� �°�
				// split("/") �ؼ� , 1�� �ε����� 2�� �ε����� ����.
				// 2�� �ε����� VV, VA, VXV, VXA, VCP, VCN
			}
			
			ArrayList<String> result = er.analyze(tempArr);
			//System.out.println(result.size());
			Iterator<String> iter = result.iterator();
			while (iter.hasNext()) {
				String val = iter.next();
	            System.out.println(val);
	            finalresult += val + "\n";
	        }
		}
		System.out.println("ok");

		ma.closeLogger();
		
		return finalresult;
	}
}
