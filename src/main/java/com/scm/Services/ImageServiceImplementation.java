package com.scm.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class ImageServiceImplementation implements ImageService{

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile imgFile,String filename) {

        try {
            byte[] data = new byte[imgFile.getInputStream().available()];
            imgFile.getInputStream().read(data);
            cloudinary.uploader().upload(
                    data,
                    ObjectUtils.asMap(
                          "public_id",filename
                    )
            );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return getImage(filename);
    }

    @Override
    public String getImage(String publicId) {
        return cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(300).height(300).crop("fill")
                )
                .generate(publicId);
    }
}
