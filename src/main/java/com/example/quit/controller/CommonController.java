package com.example.quit.controller;

import com.example.quit.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Wang Zhiming
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${quit.path}")
    private String basePath;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("Get file:" + file.toString());
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String originalFileName = file.getOriginalFilename();
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID() + suffix;
        try {
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        FileInputStream fis = null;
        ServletOutputStream sos = null;
        try {
            fis = new FileInputStream(basePath + name);
            sos = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fis.read(bytes)) != -1) {
                sos.write(bytes, 0, len);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (sos != null) {
                try {
                    sos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
