package creditsuisse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FilenameUtils;

import creditsuisse.markets.excel.POIExcelService;
import creditsuisse.markets.pdf.PdfBoxService;

public class CreditSuisseMCEIExtractor {

	public static void main(String[] args) {

        String pdfFile = "", pageNumberArgument = "";
        Options options = new Options();
        
        options.addOption(Option.builder("pdf_file")
				.required(true)
				.hasArg(true).argName("pdf_file")
				.desc("required, the pdf file to read").build());
		
		options.addOption(Option.builder("page_number")
				.optionalArg(true)				
				.hasArg(true).argName("page_number")
				.desc("optional, the page to extract the data").build());
		                
		try {
			CommandLine commandLine = new DefaultParser().parse(options, args);
			pdfFile = commandLine.getOptionValue("pdf_file");			
			pageNumberArgument = commandLine.getOptionValue("page_number");
			int pageNumber = -1;	

			System.out.println("version: 0.1");
			System.out.println("Starting " + CreditSuisseMCEIExtractor.class.getName() + " extraction of page: "+ pageNumber);
			
			if (pageNumberArgument != null) {
				pageNumber = Integer.parseInt(pageNumberArgument);
				String content = PdfBoxService.extractTextFromPage(pdfFile, pageNumber);
				POIExcelService.saveTextInExcelFile(pdfFile, pageNumber, content);
				
			} else if (pageNumberArgument == null) {							
				System.out.print("Please enter the page number: ");
				String userInputPageNumber = "";
				
				while (!"Q".equals(userInputPageNumber)) {
					try{
						BufferedReader br = new BufferedReader(new InputStreamReader(System.in));						
													
						boolean loop = true;
						while((userInputPageNumber=br.readLine())!=null && !loop){
							loop = false;
						}
						if (!"Q".equals(userInputPageNumber)) {
							pageNumber = Integer.parseInt(userInputPageNumber);
							System.out.println("Page selected is: "+pageNumber);
						} else {
							break;
						}
					}catch(IOException io){
						System.out.println("Invalid page number.");
					}	
					if (pageNumber > -1){
						System.out.println("Starting extraction of page: "+ pageNumber);
						String content = PdfBoxService.extractTextFromPage(pdfFile, pageNumber);
						POIExcelService.saveTextInExcelFile(pdfFile, pageNumber, content);
					}
					System.out.print("Please enter the page number or type [Q]: ");
				}
			}			
			System.out.println("Done");
			
		} catch (ParseException e) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.setOptionComparator(null); // Keep insertion order of options
			formatter.printHelp("CreditSuisseMCEIExtractor", "Extract the market country economic indicators", options, null);
			System.exit(1);
			return;
		}
	}

	public void saveTextInCsvFile(String origFilename, int page, String content) {
		String baseFilename = FilenameUtils.removeExtension(origFilename);
		baseFilename = "./page" + page + "_" + baseFilename + ".csv";
		try {
			Files.write(Paths.get(baseFilename), content.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
