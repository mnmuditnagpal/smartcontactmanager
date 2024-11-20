package com.scm.Services;


import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    public String uploadImage(MultipartFile imgFile,String filename);

    public String getImage(String publicId);

}
