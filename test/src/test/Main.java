package test;

import java.util.List;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.snu.ids.kkma.ma.MorphemeAnalyzer;
import org.snu.ids.kkma.ma.Sentence;

public class Main {
	public static void main(String[] args) throws Exception {
String s = "���� �β����� ���";
		
		// string to extract keywords
		String strToExtrtKwrd = s;

		// init KeywordExtractor
		KeywordExtractor ke = new KeywordExtractor();

		// extract keywords
		KeywordList kl = ke.extractKeyword(strToExtrtKwrd, true);

		System.out.println("------------------------------------------------------");
		
		// init MorphemeAnalyzer
		MorphemeAnalyzer ma = new MorphemeAnalyzer();

		// create logger, null then System.out is set as a default logger
		ma.createLogger(null);

		// analyze morpheme without any post processing 
		List ret = ma.analyze(s);

		// refine spacing
		ret = ma.postProcess(ret);

		// leave the best analyzed result
		ret = ma.leaveJustBest(ret);

		// divide result to setences
		List stl = ma.divideToSentences(ret);

		// print result
				for( int i = 0; i < kl.size(); i++ ) {
					Keyword kwrd = kl.get(i);
					System.out.println(kwrd.getString() + "\t" + kwrd.getCnt() + "\t" + kwrd.getKey());
				}
		
		// print the result
		for( int i = 0; i < stl.size(); i++ ) {
			Sentence st = (Sentence) stl.get(i);
			System.out.println("===>  " + st.getSentence());
			for( int j = 0; j < st.size(); j++ ) {
				System.out.println(st.get(j) + "\t" + st.get(j).getFirstMorp());
				//�� firstMorp�� ������Ե� �� �°�
				// split("/") �ؼ� , 1�� �ε����� 2�� �ε����� ����.
				// 2�� �ε����� VV, VA, VXV, VXA, VCP, VCN
			}
		}

		ma.closeLogger();
	}
}
