package com.clone.OneC.service;

import com.clone.OneC.dto.ConfigDTO;
import com.clone.OneC.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Service
public class ConfigurationService{
    @Autowired
    private ConfigRepository configRepository;

    @Value(value = "${project.path}")
    private String path;
    private static int BUFFER_SIZE = 4096;
    private final String url = "https://start.spring.io/starter.zip?" +
            "type=%s" +
            "&language=%s" +
            "&bootVersion=%s" +
            "&baseDir=%s" +
            "&groupId=%s" +
            "&artifactId=%s" +
            "&name=%s" +
            "&description=%s" +
            "&packageName=%s" +
            "&packaging=%s" +
            "&javaVersion=%s" +
            "&dependencies=%s";

    public void toConfigure(byte[] zip) throws IOException {
        String projectPath = path;
        System.out.println(projectPath);
        unzip(zip,path);
    }

    public String getUri(ConfigDTO config) {
        String projectUrl = String.format(url,
                "maven-project",
                "java",
                "3.0.4",
                config.name(),
                config.groupId(),
                config.name(),
                config.name(),
                "Demo%20project%20for%20Spring%20Boot",
                config.groupId() + "." + config.name(),
                "jar",
                config.javaVersion(),
                "web,lombok,data-jpa,validation");
        System.out.println(projectUrl);
        return projectUrl;
    }


    public static void unzip(byte[] data, String dirName) throws IOException {
        File destDir = new File(dirName);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(data));
        ZipEntry entry = zipIn.getNextEntry();

        while (entry != null) {
            String filePath = dirName + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    public void test() throws IOException {
        String uri = "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.0.4&baseDir=1c&groupId=com.clone&artifactId=1c&name=1c&description=1C%20clone%20for%20some%20class%20in%20my%20college&packageName=com.clone.1c&packaging=jar&javaVersion=19&dependencies=web,postgresql,lombok,data-jpa,validation";
        RestTemplate restTemplate = new RestTemplate();
        byte[] forObject = restTemplate.getForObject(uri, byte[].class);
        unzip(forObject,"./downloads");
    }


}
