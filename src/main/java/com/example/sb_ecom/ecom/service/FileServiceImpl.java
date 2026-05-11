package com.example.sb_ecom.ecom.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {

        //retrieve orginal File Name

        String orginalFileName = image.getOriginalFilename();

        // generate unique file name and append extension of orginal file name
        String uniqueFileName = UUID.randomUUID().toString() +
                orginalFileName.substring(orginalFileName.lastIndexOf("."));


        //Create the finalPath

        String filePath = path + File.separator + uniqueFileName;

        // check if path(folder) exists -> if Not Create one
        //here we are just checking wether the image folder exists or not if not then create one
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }

        // then upload the image
        Files.copy(image.getInputStream(), Paths.get(filePath));


        // return the new file name so that it can be used by dtos to display
        return uniqueFileName;

    }
}
