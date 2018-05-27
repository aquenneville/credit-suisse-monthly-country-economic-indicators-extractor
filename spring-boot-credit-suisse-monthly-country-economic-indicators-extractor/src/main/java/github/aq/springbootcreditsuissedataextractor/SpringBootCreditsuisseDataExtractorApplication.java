package github.aq.springbootcreditsuissedataextractor;

import github.aq.springbootcreditsuissedataextractor.storage.StorageService;
import github.aq.springbootcreditsuissedataextractor.storage.StorageProperties;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringBootCreditsuisseDataExtractorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCreditsuisseDataExtractorApplication.class, args);
	}
	
	@Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
