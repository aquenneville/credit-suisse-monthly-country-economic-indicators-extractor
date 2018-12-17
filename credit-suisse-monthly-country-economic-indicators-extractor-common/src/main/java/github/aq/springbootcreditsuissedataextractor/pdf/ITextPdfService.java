package github.aq.springbootcreditsuissedataextractor.pdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class ITextPdfService {

    public static String extractTextFromPage(String filename, int page) {
        String textReturned = "";
        while (i=<85)
        {
            
        try {
            PdfReader reader = new PdfReader(filename);
            // int n = reader.getNumberOfPages();
            textReturned = PdfTextExtractor.getTextFromPage(reader, i); // Extracting																// page.
            System.out.println(textReturned);
            reader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
            i++;
        }
        return textReturned;
    }
}
