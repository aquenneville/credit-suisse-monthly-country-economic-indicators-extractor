package github.aq.springbootcreditsuissedataextractor.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import github.aq.springbootcreditsuissedataextractor.excel.POIExcelService;
import github.aq.springbootcreditsuissedataextractor.pdf.PdfBoxService;
import github.aq.springbootcreditsuissedataextractor.storage.StorageFileNotFoundException;
import github.aq.springbootcreditsuissedataextractor.storage.StorageProperties;
import github.aq.springbootcreditsuissedataextractor.storage.StorageService;

@Controller
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile pdfFile,
            @RequestParam("pageNumber") String pageNumberParam,
            RedirectAttributes redirectAttributes) {

        String filename = null;
        
        if (pdfFile.getSize() == 0) {
            redirectAttributes.addFlashAttribute("message", "The file to upload field is empty, it requires the user to select a pdf file!");
            return "redirect:/";
        } else if (!pdfFile.getOriginalFilename().endsWith(".pdf")) {
            redirectAttributes.addFlashAttribute("message", "The file to upload field requires the user to select a pdf file!");
            return "redirect:/";
        }
        filename = storageService.store(pdfFile);
        try {
            int pageNumber = Integer.parseInt(pageNumberParam.trim());
            String content = PdfBoxService.extractTextFromPage(new StorageProperties().getUploadLocation() + "/"+filename, pageNumber);
            POIExcelService.saveTextInExcelFile(new StorageProperties().getDownloadLocation(), 
                    filename, pageNumber, content);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + pdfFile.getOriginalFilename() + "! Your Excel sheet is ready to be downloaded!");
        } catch (NumberFormatException exc) {
            redirectAttributes.addFlashAttribute("message", "The page number field requires a number!");
        }
        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
