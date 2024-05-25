//package com.github.houbb.nginx4j.util;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.net.Socket;
//
///**
// * TODO: 后面按照请求编码分发处理，重新实现。
// */
//public class InnerHttpUtil {
//
//    /**
//     * 符合 http 标准的字符串
//     * @param rawText 原始文本
//     * @return 结果
//     */
//    public static String http200Resp(String rawText) {
//        String format = "HTTP/1.1 200 OK\r\n" +
//                "Content-Type: text/plain\r\n" +
//                "\r\n" +
//                "%s";
//
//        return String.format(format, rawText);
//    }
//
//    /**
//     * 400 响应
//     * @return 结果
//     * @since 0.2.0
//     */
//    public static String http400Resp() {
//        return "HTTP/1.1 400 Bad Request\r\n" +
//                "Content-Type: text/plain\r\n" +
//                "\r\n" +
//                "400 Bad Request: The request could not be understood by the server due to malformed syntax.";
//    }
//
//    /**
//     * 404 响应
//     * @return 结果
//     * @since 0.2.0
//     */
//    public static String http404Resp() {
//        String response = "HTTP/1.1 404 Not Found\r\n" +
//                "Content-Type: text/plain\r\n" +
//                "\r\n" +
//                "404 Not Found: The requested resource was not found on this server.";
//
//        return response;
//    }
//
//    /**
//     * 500 响应
//     * @return 结果
//     * @since 0.2.0
//     */
//    public static String http500Resp() {
//        return "HTTP/1.1 500 Internal Server Error\r\n" +
//                "Content-Type: text/plain\r\n" +
//                "\r\n" +
//                "500 Internal Server Error: The server encountered an unexpected condition that prevented it from fulfilling the request.";
//    }
//
//    public static void sendResponse(Socket socket, int statusCode, String statusMessage, byte[] content) throws IOException {
//        OutputStream output = socket.getOutputStream();
//        PrintWriter writer = new PrintWriter(output, true);
//
//        // 发送HTTP响应头
//        writer.println("HTTP/1.1 " + statusCode + " " + statusMessage);
//        writer.println("Content-Type: text/plain");
//        writer.println("Content-Length: " + content.length);
//        writer.println("Connection: close");
//        writer.println();
//
//        // 发送HTTP响应体
//        output.write(content);
//        output.flush();
//    }
//
//}
