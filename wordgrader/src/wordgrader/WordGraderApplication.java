package wordgrader;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import javax.swing.JTextArea;

import org.snu.ids.kkma.ma.MorphemeAnalyzer;
import org.snu.ids.kkma.ma.Sentence;

public class WordGraderApplication {
	
	SentenceGrader er = new SentenceGrader("data/grades.xlsx");
	
	LinkedHashSet<String> result = new LinkedHashSet<String>();
	
	String finalResult = "";
	
	public void analyze(String s, JTextArea ta) throws Exception {
		//String s = "������ ���� �� �������� ��ǳ ������ ������� �ڿ������� ��� ���ϸ� ���ٸ� �����⸦ �߻��ߴ�. �װ� ª�� �Ӹ��� ����ߴ� �������� ���̺����� ��Ÿ�ϰ� ���� �÷��� ����Ʈ�� �� �м����� �ü��� �����Ҵ�.";
		
		// init MorphemeAnalyzer
		MorphemeAnalyzer ma = new MorphemeAnalyzer();
		//ma.createLogger(null);
		List ret = ma.analyze(s);
		ret = ma.postProcess(ret);
		ret = ma.leaveJustBest(ret);
		List stl = ma.divideToSentences(ret);
		
		ta.setText(ta.getText() + "���� ���ϰ� ���� grading ���Դϴ�...\n"); //�α� ó��
		
		// print the result
		for ( int i = 0; i < stl.size(); i++ ) { //���帶��
			Sentence st = (Sentence) stl.get(i);
			String[] tempArr = new String[st.size()];
			for( int j = 0; j < st.size(); j++ ) { //�ܾ��.. �ܼ��� String�� �ް� �ٸ� �����ͷ� �ٲٴ°ǵ�. �������� �����鼭 ǥ���Ҽ� �ִ� ������ �ƴϴ�.
				//�׷���.. ���� ���嵵 ǥ��������.
				tempArr[j] = st.get(j).getFirstMorp().toString() + "/" + st.get(j).getExp(); //st.get(j).getExp() : ���� �ܾ�
				System.out.println(st.get(j).getFirstMorp().toString() + "/" + st.get(j).getExp());
			}
			result.addAll(er.analyze(tempArr));
			//result.add("----------------------------------------------------");
		}
		
		ta.setText(ta.getText() + "---�Ϸ�---\n"); //�α� ó��
		//System.out.println("ok");
		
		ma.closeLogger();
	}
	
	public String getFinalResult() {	
		Iterator<String> iter = result.iterator();
		while (iter.hasNext()) {
			String val = iter.next();
            System.out.println(val);
            finalResult += val + "\n";
        }
		
		return finalResult;
	}
	
	public void clearFinalResult() {
		finalResult = "";
	}
	
	public void clearResult() {
		result.clear();
	}
}
