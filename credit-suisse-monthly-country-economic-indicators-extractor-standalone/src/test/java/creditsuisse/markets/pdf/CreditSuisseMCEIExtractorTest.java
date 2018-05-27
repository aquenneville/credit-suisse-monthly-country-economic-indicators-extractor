package creditsuisse.markets.pdf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class CreditSuisseMCEIExtractorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLoadPdf() {		
		String text = ITextPdfService.extractTextFromPage("CS2016.pdf", 26);
		System.out.println(text);
	}

	@Test
	public void testRegexFindNumbers() {
		String content = "Public Foreign Debt ($bn) na na -96.4 105.5 104.3 115.5 120.5 136.9 138.0 135.0 135.0 ";
		// ((\d+(\.\d{1,4})?)\s)
		String pattern = "(na\\s)|((-?\\d+(\\.\\d{1,4})?)\\s)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(content);
		while (m.find()) {
			System.out.println("Found value: " + m.group());
		}
	}
}
