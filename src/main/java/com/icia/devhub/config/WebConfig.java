package com.icia.devhub.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 업로드 파일(프로필 사진, 게시판 첨부)을 소스/빌드 폴더가 아닌 외부 디렉터리(uploads/)에 두고 서빙.
 * 저장 위치와 서빙 위치를 일치시켜 업로드 즉시 표시되며, JAR 패키징 후에도 동작한다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String UPLOAD_DIR =
            Paths.get(System.getProperty("user.dir"), "uploads").toString().replace("\\", "/");

    // 업로드 디렉터리가 없으면 최초 실행 시 생성
    @PostConstruct
    public void initUploadDirs() throws IOException {
        Files.createDirectories(Paths.get(UPLOAD_DIR, "profile"));
        Files.createDirectories(Paths.get(UPLOAD_DIR, "upload"));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 회원 프로필 사진: /profile/** -> uploads/profile/
        registry.addResourceHandler("/profile/**")
                .addResourceLocations("file:" + UPLOAD_DIR + "/profile/");
        // 게시판 첨부 파일: /upload/** -> uploads/upload/
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + UPLOAD_DIR + "/upload/");
    }
}
