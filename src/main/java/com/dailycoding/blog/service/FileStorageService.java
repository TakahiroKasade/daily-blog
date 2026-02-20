package com.dailycoding.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService() {
        // 設定檔案儲存路徑為專案根目錄下的 uploads 資料夾
        // System.getProperty("user.dir") 取得目前專案路徑
        this.fileStorageLocation = Paths.get(System.getProperty("user.dir") + "/uploads")
                .toAbsolutePath().normalize();

        try {
            // 如果資料夾不存在則建立
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("無法建立上傳目錄！", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null; // 沒有上傳檔案
        }

        // 取得原始檔名
        String originalFileName = file.getOriginalFilename();
        
        // 取得副檔名 (例如 .jpg)
        String fileExtension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        // 產生唯一檔名 (UUID) 防止檔名衝突
        String fileName = UUID.randomUUID().toString() + fileExtension;

        try {
            // 複製檔案到目標路徑 (如果有同名檔案則覆蓋)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName; // 回傳儲存後的檔名
        } catch (IOException ex) {
            throw new RuntimeException("無法儲存檔案 " + fileName + ". 請重試！", ex);
        }
    }
}
