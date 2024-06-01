package com.github.houbb.nginx4j.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NginxConfigMyTest {

    public static void main(String[] args) {
        Map<String, String> configMap = parseNginxConfig("D:\\github\\nginx4j\\src\\test\\resources\\nginx-demo.conf");
        System.out.println("Nginx configuration settings:");
        for (Map.Entry<String, String> entry : configMap.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }

    public static Map<String, String> parseNginxConfig(String filePath) {
        Map<String, String> configMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentBlock = "";
            Pattern pattern = Pattern.compile("^\\s*([^#\\s][^\\s]*)\\s*([^#]+)?");
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String directive = matcher.group(1);
                    String value = matcher.group(2);
                    if (value != null) {
                        value = value.trim();
                        if (value.endsWith(";")) {
                            value = value.substring(0, value.length() - 1).trim();
                        }
                    }
                    if (directive.equals("server")) {
                        currentBlock = "server";
                    } else if (directive.equals("location")) {
                        currentBlock = "location";
                    }
                    if (!directive.isEmpty()) {
                        configMap.put(currentBlock + "." + directive, value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configMap;
    }

}
