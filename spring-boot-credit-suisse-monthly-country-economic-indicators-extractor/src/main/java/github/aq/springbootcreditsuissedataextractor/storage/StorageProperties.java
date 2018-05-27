package github.aq.springbootcreditsuissedataextractor.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String uploadLocation = "upload-dir";
    private String downloadLocation = "download-dir";
    
    public String getUploadLocation() {
        return uploadLocation;
    }

    public void setUploadLocation(String location) {
        this.uploadLocation = location;
    }

    public String getDownloadLocation() {
        return downloadLocation;
    }

    public void setDownloadLocation(String downloadLocation) {
        this.downloadLocation = downloadLocation;
    }

    
}
