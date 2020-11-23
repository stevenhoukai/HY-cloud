package com.yyicbc.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileDownLoadUtil {

    /**
     * @param files
     * @param outputStream
     * @throws IOException
     * @throws ServletException
     */
    public static void zipFile(List<File> files, ZipOutputStream outputStream) throws IOException, ServletException {
        try {
            int size = files.size();
            // 压缩列表中的文件
            for (int i = 0; i < size; i++) {
                File file = (File) files.get(i);
                zipFile(file, outputStream);
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * @param inputFile
     * @param outputstream
     * @throws IOException
     * @throws ServletException
     */
    public static void zipFile(File inputFile, ZipOutputStream outputstream) throws IOException, ServletException {

        BufferedInputStream bInStream = null;
        FileInputStream inStream = null;
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                    inStream = new FileInputStream(inputFile);
                    bInStream = new BufferedInputStream(inStream);
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    outputstream.putNextEntry(entry);

                    final int MAX_BYTE = 50 * 1024 * 1024; // 最大的流为100M
                    long streamTotal = 0; // 接受流的容量
                    int streamNum = 0; // 流需要分开的数量
                    int leaveByte = 0; // 文件剩下的字符数
                    byte[] inOutbyte; // byte数组接受文件的数据

                    streamTotal = bInStream.available(); // 通过available方法取得流的最大字符数
                    streamNum = (int) Math.floor(streamTotal / MAX_BYTE); // 取得流文件需要分开的数量
                    leaveByte = (int) streamTotal % MAX_BYTE; // 分开文件之后,剩余的数量

                    if (streamNum > 0) {
                        for (int j = 0; j < streamNum; ++j) {
                            inOutbyte = new byte[MAX_BYTE];
                            // 读入流,保存在byte数组
                            bInStream.read(inOutbyte, 0, MAX_BYTE);
                            outputstream.write(inOutbyte, 0, MAX_BYTE); // 写出流
                        }
                    }
                    // 写出剩下的流数据
                    inOutbyte = new byte[leaveByte];
                    bInStream.read(inOutbyte, 0, leaveByte);
                    outputstream.write(inOutbyte);



                }
            } else {
                throw new ServletException("文件不存在！");
            }
        } catch (IOException e) {
            outputstream.close();
            throw e;
        }finally {
            if(outputstream != null){
                outputstream.closeEntry();
            }
            if(bInStream != null){
                bInStream.close(); // 关闭
            }
            if(bInStream != null){
                inStream.close();
            }

        }
    }

    /**
     * @param file
     * @param response
     * @param isDelete
     */
    public static void downloadFile(File file, HttpServletResponse response, boolean isDelete) throws IOException {
        try {
            // 以流的形式下载文件。
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            if (isDelete) {
                file.delete();        //是否将生成的服务器端文件删除
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IOException(ex.getMessage());
        }
    }

    /**
     * @param file
     * @param response
     */
    public static void downloadFile(File file, HttpServletResponse response) throws IOException {
        downloadFile(file,response,false);
    }


    /**
     * @param serverPath // 压缩包的文件路径
     * @param srcfile    //需要压缩文件列表
     * @param response
     * @param zipName    //压缩文件名称
     * @throws IOException
     * @throws ServletException
     */
    public static void exportFile(String serverPath, List<File> srcfile, HttpServletResponse response, String zipName) throws IOException, ServletException {
        try {
            File file = new File(serverPath);
            if (!file.exists())
                file.mkdirs();
            file = new File(serverPath + "/" + zipName + ".zip");
            FileOutputStream outStream = new FileOutputStream(file);
            ZipOutputStream toClient = new ZipOutputStream(outStream);
            zipFile(srcfile, toClient);
            toClient.close();
            outStream.close();
            downloadFile(file, response, false);

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
//            for (int i = 0; i < srcfile.size(); i++) {
//                File f = srcfile.get(i);
//                f.delete();
//            }
        }
    }

}
