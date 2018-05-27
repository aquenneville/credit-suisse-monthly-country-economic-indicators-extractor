package creditsuisse.markets.pdf;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfBoxService {

    public static String extractTextFromPage(String filename, int page) {
        String parsedText = "";
        PDFTextStripper pdfStripper = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;		

        try {
            RandomAccessFile rar = new RandomAccessFile(new File(filename), "rw");
            PDFParser parser = new PDFParser(rar);
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            pdfStripper.setStartPage(page);
            pdfStripper.setEndPage(page);
            parsedText = pdfStripper.getText(pdDoc);
            System.out.println(parsedText);

        } catch (IOException e) {			
            e.printStackTrace();
        }
        return parsedText;
    }
}
