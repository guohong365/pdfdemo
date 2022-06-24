package com.nantian.pdf.content;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IPdfReplacer {
    /**
     * PDF文件替换器
     * @param sourceFile 源PDF全路径名称
     * @param outputFile 结果PDF全路径文件名称。目录及文件名具有可写权限，不能与源文件相同
     * @throws Exception
     */
    void replace(String sourceFile, String outputFile) throws Exception;

    /**
     * PDF文件替换器
     * @param inputStream 源PDF文件输入流
     * @param outputStream 结果PDF文件输入流。输出流和输入流不能指向同一文件
     * @throws Exception
     */
    void replace(InputStream inputStream, OutputStream outputStream) throws Exception;
}

