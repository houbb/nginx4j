package com.github.houbb.nginx4j.support.server;

import com.github.houbb.heaven.util.io.StreamUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.api.INginxServer;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.exception.Nginx4jException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.List;

public class NginxServerSocket implements INginxServer {

    private static final Log log = LogFactory.getLog(NginxServerSocket.class);


    private NginxConfig nginxConfig;

    @Override
    public void init(NginxConfig nginxConfig) {
        this.nginxConfig = nginxConfig;
    }

    @Override
    public void start() {
        try {
            // 服务器监听的端口号
            int port = nginxConfig.getHttpServerListen();
            ServerSocket serverSocket = new ServerSocket(port);
            log.info("[Nginx4j] listen on port={}", port);

            while (true) {
                Socket socket = serverSocket.accept();
                log.info("[Nginx4j] Accepted connection from address={}", socket.getRemoteSocketAddress());
                handleClient(socket);
            }
        } catch (Exception e) {
            log.info("[Nginx4j] meet ex", e);

            throw new RuntimeException(e);
        }
    }

    private void handleClient(Socket socket) {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String header = reader.readLine();
            String[] parts = header.split(" ");
            String method = parts[0];
            String path = parts[1];
            String protocol = parts[2];

            // 根路径
            final String basicPath = nginxConfig.getHttpServerRoot();
            // 只处理GET请求
            if ("GET".equalsIgnoreCase(method)) {
                //root path
                if(StringUtil.isEmpty(path) || "/".equals(path)) {
                    log.info("[Nginx4j] current path={}, match index path", path);
                    byte[] fileContent = tryGetIndexContent();
                    sendResponse(socket, 200, "OK", fileContent);
                    return;
                }

                // other
                File file = new File(basicPath + path);
                if (file.exists()) {
                    byte[] fileContent = Files.readAllBytes(file.toPath());
                    sendResponse(socket, 200, "OK", fileContent);
                } else {
                    sendResponse(socket, 404, "Not Found", "File not found.".getBytes());
                }
            } else {
                sendResponse(socket, 405, "Method Not Allowed", "Method not allowed.".getBytes());
            }
        } catch (Exception e) {
            log.error("[Nginx4j] handleClient meet ex", e);

            try {
                sendResponse(socket, 500, "Internal Server Error", "Internal server error.".getBytes());
            } catch (Exception ex) {
                log.error("[Nginx4j] sendResponse meet ex", e);
            }
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log.error("[Nginx4j] socket close meet ex", e);
            }
        }
    }

    private byte[] tryGetIndexContent() throws IOException {
        try {
            List<String> indexHtmlList = nginxConfig.getHttpServerIndexList();

            String basicPath = nginxConfig.getHttpServerRoot();
            for(String indexHtml : indexHtmlList) {
                String fullPath = basicPath + basicPath + indexHtml;
                File file = new File(fullPath);
                if(file.exists()) {
                    log.info("[Nginx4j] meet indexPath={}", fullPath);
                    return Files.readAllBytes(file.toPath());
                }
            }

            // 默认
            return StreamUtil.getFileBytes("index.html");
        } catch (IOException e) {
            log.error("[Nginx4j] tryGetIndexContent meet ex", e);
            throw new Nginx4jException(e);
        }
    }

    private void sendResponse(Socket socket, int statusCode, String statusMessage, byte[] content) throws IOException {
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);

        // 发送HTTP响应头
        writer.println("HTTP/1.1 " + statusCode + " " + statusMessage);
        writer.println("Content-Type: text/plain");
        writer.println("Content-Length: " + content.length);
        writer.println("Connection: close");
        writer.println();

        // 发送HTTP响应体
        output.write(content);
        output.flush();
    }

}
