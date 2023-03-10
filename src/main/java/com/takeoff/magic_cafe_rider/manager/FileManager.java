package com.takeoff.magic_cafe_rider.manager;

import com.takeoff.magic_cafe_rider.model.QuestImage;
import com.takeoff.magic_cafe_rider.utils.FileUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class FileManager {
    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.upload.root}")
    private String uploadRoot;

    @Value("${file.upload.thumbnail}")
    private String dirThumbnail;

    @Value("${file.upload.cs}")
    private String dirCs;

    @Value("${file.upload.sign}")
    private String roadSign;
    @Value("menu")
    private String menuPaper;

    public String getRandomString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public String makePath(String dir) throws Exception {
        String dbPath = FileUploader.makeUploadDir(this.uploadPath, this.uploadRoot, dir, this.dirThumbnail);
        return dbPath;
    }
    public String makeMenuPaperPath(long qid) throws Exception {
        String dbPath = FileUploader.makeMenuPaperUploadDir(this.uploadPath, this.menuPaper, qid);
        return dbPath;
    }
    public String makeRoadSignPath(String uid) throws Exception {
        String dbPath = FileUploader.makeRoadSignUploadDir(this.uploadPath, this.roadSign, uid);
        return dbPath;
    }

    public String makeCsPath() throws Exception {
        String dbPath = FileUploader.makeCsUploadDir(this.uploadPath, this.dirCs);
        return dbPath;
    }

    public String pushFile(MultipartFile file, String dbPath, boolean isThumb) throws Exception {
        if (file.getSize() <= 0) {
            log.debug("push file error: {}", file.getOriginalFilename());
            return "";
        }

        String fullPath = this.uploadPath + File.separator + dbPath;
        String fileName = "";
        if (isThumb) {
            fileName = FileUploader.fileUploadWithThumbnail(fullPath, file.getOriginalFilename(), file.getBytes(), this.dirThumbnail);
        }
        else {
            fileName = FileUploader.fileUpload(fullPath, file.getOriginalFilename(), file.getBytes());
        }

        return fileName;
    }

    public String pushThumbnailWith(MultipartFile file, String dbPath) throws Exception {
        if (file.getSize() <= 0) {
            log.debug("push file error: {}", file.getOriginalFilename());
            return "";
        }

        String fullPath = this.uploadPath + File.separator + dbPath;
        String fileName = "";
        fileName = FileUploader.fileUploadWithThumbnail(fullPath, file.getOriginalFilename(), file.getBytes(), this.dirThumbnail);

        return fileName;
    }


    public String pushCommonFile(MultipartFile file, String dbPath) throws Exception {
        if (file.getSize() <= 0) {
            log.debug("push file error: {}", file.getOriginalFilename());
            return "";
        }

        String fullPath = this.uploadPath + File.separator + dbPath;

        return FileUploader.fileUpload(fullPath, file.getOriginalFilename(), file.getBytes());
    }

    public String getThumbPath(String dbPath, String fileName) {
        return dbPath + File.separator + this.dirThumbnail + File.separator + "s_" + fileName;
    }

    public String getThumbPathAttr(String dbPath) {
        return dbPath + File.separator + this.dirThumbnail;
    }

    public String getThumbFileAttr(String fileName) {
        return "s_" + fileName;
    }

    public String updateThumbnail(QuestImage decImage) throws IOException {
        String fullPath = this.uploadPath + File.separator + decImage.getImgUrl();
        File target = new File(fullPath, decImage.getImgFile());
        String fileName = FileUploader.fileUpdateThumbnail(target, fullPath, decImage.getImgFile(), this.dirThumbnail);
        return decImage.getImgUrl() + File.separator + this.dirThumbnail + File.separator + fileName;
    }
}
