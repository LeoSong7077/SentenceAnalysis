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
		//String s = "공개된 사진 속 정동원은 폭풍 성장한 모습으로 자연스러운 포즈를 취하며 남다른 분위기를 발산했다. 그간 짧은 머리를 고수했던 정동원은 웨이브펌의 헤어스타일과 레드 컬러로 포인트를 준 패션으로 시선을 사로잡았다.";
		
		// init MorphemeAnalyzer
		MorphemeAnalyzer ma = new MorphemeAnalyzer();
		//ma.createLogger(null);
		List ret = ma.analyze(s);
		ret = ma.postProcess(ret);
		ret = ma.leaveJustBest(ret);
		List stl = ma.divideToSentences(ret);
		
		ta.setText(ta.getText() + "엑셀 파일과 비교해 grading 중입니다...\n"); //로그 처리
		
		// print the result
		for ( int i = 0; i < stl.size(); i++ ) { //문장마다
			Sentence st = (Sentence) stl.get(i);
			String[] tempArr = new String[st.size()];
			for( int j = 0; j < st.size(); j++ ) { //단어마다.. 단순히 String을 받고 다른 데이터로 바꾸는건데. 순서데로 읽으면서 표시할수 있는 개념이 아니다.
				//그러면.. 원래 문장도 표시해주자.
				tempArr[j] = st.get(j).getFirstMorp().toString() + "/" + st.get(j).getExp(); //st.get(j).getExp() : 원래 단어
				System.out.println(st.get(j).getFirstMorp().toString() + "/" + st.get(j).getExp());
			}
			result.addAll(er.analyze(tempArr));
			//result.add("----------------------------------------------------");
		}
		
		ta.setText(ta.getText() + "---완료---\n"); //로그 처리
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
