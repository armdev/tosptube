package com.mongo.filestorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author armen
 */
public class FileStorage {

    private static String basepath = null;

    public static String getBasepath() {
        return basepath;
    }

    public static void setBasepath(String basepath) {
        FileStorage.basepath = basepath;
    }

    public FileStorage(String path) {
        if (path == null) {
            throw new IllegalArgumentException("File storage path is null");
        }
        File file = new File(path);
        if (file.exists()) {
            basepath = path;
        } else {
            throw new IllegalArgumentException("Path to file storage directory doesn't exist or is not a directory");
        }
    }

    public String storeFile(String title, byte[] content) throws Exception {
        String filepath = null;
        String abspath = null;
        String hashString = title + String.valueOf(System.currentTimeMillis());
        File file = null;
        do {
            StringBuilder sb = new StringBuilder();
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                byte[] bs;
                bs = messageDigest.digest(hashString.getBytes());
                for (int i = 0; i < bs.length; i++) {
                    String hexVal = Integer.toHexString(0xFF & bs[i]);
                    if (hexVal.length() == 1) {
                        sb.append("0");
                    }
                    sb.append(hexVal);
                }
                hashString = sb.toString();
            } catch (NoSuchAlgorithmException ex) {
                System.out.println("cannot fine implementation of SHA algorigm: " + ex);
                return filepath;
            }
            filepath = hashString;
            abspath = basepath + hashString.substring(0, 2) + "/" + hashString.substring(2, 4) + "/";
            file = new File(abspath + hashString);
        } while (file.exists());
        try {
            File dirs = new File(abspath);
            if (!dirs.exists()) {
                boolean mkdir = dirs.mkdirs();
                if (!mkdir) {
                    throw new Exception("Could not create directory for file storage");
                }
            }
            boolean createNewFile = file.createNewFile();
            if (createNewFile) {
                try (FileOutputStream fo = new FileOutputStream(file)) {
                    fo.write(content);
                }
            }
        } catch (IOException e) {
        }
        return hashString.substring(0, 2) + "/" + hashString.substring(2, 4) + "/" + filepath;
    }

    public void removeFile(String filepath) throws Exception {
        if (basepath != null && filepath != null) {
            String path = basepath + filepath;
            File file = new File(path);
            if (file.exists()) {
                boolean check = file.delete();
                if (!check) {
                    throw new Exception("Could not remove file " + filepath);
                }
            } else {
                throw new Exception("File does not exist " + filepath);
            }
        }
    }

    public byte[] readFile(String filepath) {
        byte[] content = null;
        if (basepath != null && filepath != null) {
            String abspath = basepath + filepath;
            File file = new File(abspath);
            if (!file.exists()) {
                return content;
            } else {
                //log
            }
            try {
                try (FileInputStream io = new FileInputStream(file)) {
                    int bytesize = io.available();
                    content = new byte[bytesize];
                    int read = io.read(content);
                }
            } catch (IOException e) {
            }
        }
        return content;
    }

}
