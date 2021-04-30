package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.snu.ids.kkma.ma.MorphemeAnalyzer;
import org.snu.ids.kkma.ma.Sentence;

public class Main {
	public static void main(String[] args) throws Exception {
		
		String s = "������ ���� �� �������� ��ǳ ������ ������� �ڿ������� ��� ���ϸ� ���ٸ� �����⸦ �߻��ߴ�. �װ� ª�� �Ӹ��� ����ߴ� �������� ���̺����� ��Ÿ�ϰ� ���� �÷��� ����Ʈ�� �� �м����� �ü��� �����Ҵ�.";
		
		// init MorphemeAnalyzer
		MorphemeAnalyzer ma = new MorphemeAnalyzer();

		// create logger, null then System.out is set as a default logger
		//�ΰŸ� �����ϱ�, null �ϸ� �ֿܼ� ǥ��
		ma.createLogger(null);

		// analyze morpheme without any post processing 
		List ret = ma.analyze(s);

		// refine spacing
		ret = ma.postProcess(ret);

		// leave the best analyzed result
		ret = ma.leaveJustBest(ret);

		// divide result to setences
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
	            System.out.println(iter.next());
	        }
		}
		System.out.println("ok");

		ma.closeLogger();
	}
}
