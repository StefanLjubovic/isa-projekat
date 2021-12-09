package com.backend.service;

import java.io.*;
import java.util.Base64;

public class Base64ToImage {

    public void Base64DecodeAndSave(String base64String, String imagePath) throws FileNotFoundException, IOException {

        String part[] = base64String.split(",");
        String path = imagePath;

        byte[] data = Base64.getDecoder().decode(part[1]);

        System.out.println(part[1]);
        try (OutputStream stream = new FileOutputStream(path)) {
            stream.write(data);
        }
    }

    public String readImagesFromPath(String imagePath) throws FileNotFoundException, IOException {

        String base64String;
        String basePath = new File("images/").getAbsolutePath();
        String[] paths = imagePath.split("/"); //path is like /images/cottage/cottageNameIndex.jpg
        imagePath = "/" + paths[2] + "/" + paths[3];
        String path = basePath + imagePath;
        File file = new File(path);
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            base64String= new String(Base64.getEncoder().encode(bytes), "UTF-8");
            String imageData = "data:image/jpeg;base64,";

            int pathLen = base64String.length();
            String newBase64 = base64String.substring(2, pathLen);
            return imageData + base64String;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}