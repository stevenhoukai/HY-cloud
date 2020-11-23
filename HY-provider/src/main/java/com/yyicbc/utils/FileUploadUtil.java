package com.yyicbc.utils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.ArrayList;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUploadUtil {

    static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 遍历目录下面指定的文件名
     *
     * @param pathName 需要遍历的目录，必须以"/"开始和结束
     * @param ext      文件的扩展名
     * @throws IOException
     */
    public List getListFile(String pathName, String ext) throws IOException {
        List filesList = new ArrayList();
        if ((pathName.startsWith("D:") || pathName.startsWith("/")) && pathName.endsWith("/")) {
            File file = new File(pathName);
            //获取该路径下的文件和文件夹所对应的文件对象
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isFile()) {
                    if (f.getName().endsWith(ext)) {
                        filesList.add(pathName + f.getName());
                    }
                }
            }
        }
        return filesList;
    }


    /**
     * 创建文件夹目录
     *
     * @param pathName
     * @return
     * @throws IOException
     */
    public void createDirectory(String pathName) throws Exception {
        File f = new File(pathName);
        if (!f.exists()) {
            f.mkdirs(); //创建目录
        }
    }

    /**
     * 使用FileChannel复制文件
     *
     * @param source
     * @param dest
     * @throws IOException
     */
    public void copyFileUsingFileChannels(File source, File dest) throws Exception {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        }catch(Exception e ){
            logger.info(e.getMessage());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    /**
     * 删除文件 *
     *
     * @param filename 要删除的文件名称 *
     * @return
     */
    public void deleteFile(String filename) throws Exception {
        //切换FTP目录
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 上传文件
     *
     * @param pathname    保存地址
     * @param fileName    上传文件名
     * @param inputStream 输入文件流
     * @return
     */
    public void uploadToFile(String pathname, String fileName, InputStream inputStream) {
        FileOutputStream fs = null;
        try {
            String path = pathname + fileName;// 文件保存地址
            fs = new FileOutputStream(path);
            byte[] buffer = new byte[1024 * 1024];
//            int bytesum = 0;
            int byteread = 0;
            while ((byteread = inputStream.read(buffer)) != -1) {
//                bytesum += byteread;
                fs.write(buffer, 0, byteread);
                fs.flush();
            }
            fs.close();
            inputStream.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(e.getMessage());
//            System.out.println("上传文件失败");
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fs) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}