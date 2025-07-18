package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Common Interface
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "Common Interface")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @Value("${file.upload-path}")
    private String imgUrl;

    /**
     * File upload
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("File upload")
    public Result<String> upload(MultipartFile file){
        log.info("File uploaded: {}",file);

        // If using aliyun OSS service
        /*
        try {
            //Original file name
            String originalFilename = file.getOriginalFilename();
            //Extract the suffix of the original file name
            String extension = originalFilename = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID().toString() + extension;

            //File request path
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success();

        } catch (IOException e) {
            log.error("File upload failed: {}", e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
        */

        //Extract the suffix of the original file name
        String originalFilename = file.getOriginalFilename();
        //Set the request prefix
        String requestHeader = "http://localhost:8080/images/";
        //Concatenate new file names
        String objectName = UUID.randomUUID().toString() + originalFilename;
        try {
            //Save the file to the specified directory
            file.transferTo(new File(imgUrl + objectName));
            log.info("File uploaded success");
            log.info("The address of the image return backend is:" + requestHeader + objectName);
            return Result.success(requestHeader + objectName);
        }catch (Exception e){
            log.error("File upload failed: {}", e);
        }
        return null;
    }
}
