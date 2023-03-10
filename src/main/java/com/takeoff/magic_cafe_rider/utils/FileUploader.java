package com.takeoff.magic_cafe_rider.utils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

@Slf4j
@Component
public class FileUploader {
    static final int THUMB_WIDTH = 300;
    static final int THUMB_HEIGHT = 300;

    public static String fileUpload(String uploadPath,
                                    String fileName,
                                    byte[] fileData) throws Exception {

        UUID uid = UUID.randomUUID();
        String ext = FilenameUtils.getExtension(fileName);
        String newFileName = uid + "." + ext;

        File target = new File(uploadPath, newFileName);
        FileCopyUtils.copy(fileData, target);


        return newFileName;
    }

    public static String makeCsUploadDir(String uploadPath, String rootPath) throws Exception {
        Calendar cal = Calendar.getInstance();
        String yearPath = ""+cal.get(Calendar.YEAR);
        String monthPath = yearPath + "_" + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);

        String path = uploadPath + File.separator + rootPath;
        makeDir(path);
        path += (File.separator + monthPath);
        makeDir(path);

        String returnPath = rootPath + File.separator + monthPath;
        return returnPath;
    }
    public static String makeMenuPaperUploadDir(String uploadPath, String rootPath, long qid) throws Exception {
        String path = uploadPath + File.separator + rootPath;
        makeDir(path);
        path += (File.separator + qid);
        makeDir(path);

        String returnPath = rootPath + File.separator + qid;
        return returnPath;
    }
    public static String makeRoadSignUploadDir(String uploadPath, String rootPath, String uid) throws Exception {
        String path = uploadPath + File.separator + rootPath;
        makeDir(path);
        path += (File.separator + uid);
        makeDir(path);

        String returnPath = rootPath + File.separator + uid;
        return returnPath;
    }

    public static String makeUploadDir(String uploadPath, String rootPath, String idPath, String thumbPath) throws Exception {
        Calendar cal = Calendar.getInstance();
        String yearPath = ""+cal.get(Calendar.YEAR);
        String monthPath = yearPath + "_" + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);

        String path = uploadPath + File.separator + rootPath;
        makeDir(path);
        path += (File.separator + monthPath);
        makeDir(path);
        path += (File.separator + idPath);
        makeDir(path);
        path += (File.separator + thumbPath);
        makeDir(path);

        String returnPath = rootPath + File.separator + monthPath + File.separator + idPath;
        return returnPath;
    }

    public static String fileUploadWithThumbnail(String uploadPath,
                                                 String fileName,
                                                 byte[] fileData, String thumbPath) throws Exception {

        UUID uid = UUID.randomUUID();
        String ext = FilenameUtils.getExtension(fileName);
        String newFileName = uid + "." + ext;

        File target = new File(uploadPath, newFileName);
        FileCopyUtils.copy(fileData, target);

        if (target.exists()) {
            String thumbFileName = "s_" + newFileName;
            File thumbnail = new File(uploadPath + File.separator + thumbPath + File.separator + thumbFileName);
            Thumbnails.of(target).size(THUMB_WIDTH, THUMB_HEIGHT).toFile(thumbnail);
        }

        return newFileName;
    }

    public static String fileUpdateThumbnail(File target, String uploadPath, String newFileName, String thumbPath) throws IOException {
        String thumbFileName = "s_" + newFileName;
        String fullPath = uploadPath + File.separator + thumbPath + File.separator + thumbFileName;
        File thumbnail = new File(fullPath);
        Thumbnails.of(target).size(THUMB_WIDTH, THUMB_HEIGHT).toFile(thumbnail);

        return thumbFileName;
    }

    public static void makeDir(String path) {
        File dirPath = new File(path);

        if (!dirPath.exists()) {
            boolean b = dirPath.mkdir();
        }
    }

}
