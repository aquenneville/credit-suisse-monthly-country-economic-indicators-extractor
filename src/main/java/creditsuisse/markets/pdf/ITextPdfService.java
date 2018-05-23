package creditsuisse.markets.pdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class ITextPdfService {

    public static String extractTextFromPage(String filename, int page) {
        String textReturned = "";
        try {
            PdfReader reader = new PdfReader(filename);
            // int n = reader.getNumberOfPages();
            textReturned = PdfTextExtractor.getTextFromPage(reader, page); // Extracting																// page.
            System.out.println(textReturned);
            reader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return textReturned;
    }
}
