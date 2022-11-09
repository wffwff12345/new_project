package com.cl.common.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OSSService {
    @Autowired
    private OSSProperties prop;

    @Autowired
    private OSS ossClient;

    private static final List<String> suffixes = Arrays.
            asList("image/png", "image/jpeg", "image/bmp");

    /**
     * 本地上传
     *
     * @param file
     * @return
     */
    public String upload(MultipartFile file) throws Exception {
        String contentType = file.getContentType();
        if (!suffixes.contains(contentType)) {
            throw new Exception("不支持文件类型");
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                throw new Exception("不支持文件类型");
            }
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + suffix;
            InputStream inputStream = file.getInputStream();
            PutObjectRequest putObjectRequest = new PutObjectRequest(prop.getBucket(), newFileName, inputStream);
            ossClient.putObject(putObjectRequest);
            String url = prop.getHost() + "/" + newFileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("上传文件失败");
        }
    }

    /**
     * 删除文件
     *
     * @param url
     * @return
     */
    public String deleteFile(String url) {
        String objectName = url.replace(prop.getHost() + "/", "");
        ossClient.deleteObject(prop.getBucket(), objectName);
        return "删除成功";
    }
}