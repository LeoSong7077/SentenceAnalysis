package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.snu.ids.kkma.ma.MorphemeAnalyzer;
import org.snu.ids.kkma.ma.Sentence;

public class WordGrader {

	public String analyze(String s) throws Exception {
		String finalresult = "";
		//String s = "공개된 사진 속 정동원은 폭풍 성장한 모습으로 자연스러운 포즈를 취하며 남다른 분위기를 발산했다. 그간 짧은 머리를 고수했던 정동원은 웨이브펌의 헤어스타일과 레드 컬러로 포인트를 준 패션으로 시선을 사로잡았다.";
		
		// init MorphemeAnalyzer
		MorphemeAnalyzer ma = new MorphemeAnalyzer();

		// create logger, null then System.out is set as a default logger
		//로거를 지정하기, null 하면 콘솔에 표시
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
				
				//이 firstMorp가 용언포함된 것 맞고
				// split("/") 해서 , 1번 인덱스와 2번 인덱스만 저장.
				// 2번 인덱스가 VV, VA, VXV, VXA, VCP, VCN
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
