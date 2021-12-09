package com.backend.service;

import java.io.*;
import java.util.Base64;

public class Base64ToImage {

    public void decodeImageFromBase64(String base64String, String imagePath) throws IOException {
        String part[] = base64String.split(",");
        byte[] data = Base64.getDecoder().decode(part[1]);

        try (OutputStream stream = new FileOutputStream(imagePath)) {
            stream.write(data);
        }
    }

    public String encodeImageToBase64(String relativePath) throws IOException {
        String path = getImagePath(relativePath);
        File file = new File(path);
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            String base64String= new String(Base64.getEncoder().encode(bytes), "UTF-8");
            String header = "data:image/jpeg;base64,";
            return header + base64String;
        } catch (IOException e) { e.printStackTrace(); }

        return "";
    }

    private String getImagePath(String relativePath) {
        String basePath = new File("images/").getAbsolutePath();
        String[] paths = relativePath.split("/"); //path is like /images/cottage/cottageNameIndex.jpg
        StringBuilder path = new StringBuilder().append(basePath).append("/").append(paths[2]).append("/").append(paths[3]);
        return path.toString();
    }
}