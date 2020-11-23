package com.yyicbc.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 文件压缩工具类
 */
@Slf4j
public class FileUtil {
    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * @param sourceFilePath 指定文件路径
     * @param zipFilePath    打成压缩包的路径
     * @param fileName       文件名称
     * @return
     */
    public static void fileToZip(String sourceFilePath, String zipFilePath, String fileName) {
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        if (!sourceFile.exists()) {
            log.info("待压缩的文件目录：" + sourceFilePath + "不存在.");
        } else {
            try {
                File zipFile = new File(zipFilePath + "/" + fileName);
                if (zipFile.exists()) {
                    log.info(zipFilePath + "目录下存在名字为:" + fileName + "打包文件.");
                } else {
                    File[] sourceFiles = sourceFile.listFiles();
                    if (null == sourceFiles || sourceFiles.length < 1) {
                        log.info("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
                    } else {
                        fos = new FileOutputStream(zipFile);
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));
                        byte[] bufs = new byte[1024 * 10];
                        for (int i = 0; i < sourceFiles.length; i++) {
                            //创建ZIP实体，并添加进压缩包
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                            zos.putNextEntry(zipEntry);
                            //读取待压缩的文件并写进压缩包里
                            fis = new FileInputStream(sourceFiles[i]);
                            bis = new BufferedInputStream(fis, 1024 * 10);
                            int read = 0;
                            while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                                zos.write(bufs, 0, read);
                            }
                            fis.close();
                            bis.close();
                        }
                    }
                    sourceFiles = null;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                //关闭流
                try {
                    if (null != zos) {
                        zos.flush();
                        zos.close();
                    }
                    if (null != fis) {
                        fis.close();
                    }
                    if (null != fos) {
                        fos.flush();
                        fos.close();
                    }
                    if (null != bis) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 解压文件到指定目录
     */
    @SuppressWarnings({"rawtypes", "resource"})
    public static void unZipFiles(File zipFile, String descDir) throws IOException {
        log.info("解压路径:{}. 解压开始.", descDir);
        long start = System.currentTimeMillis();
        try {
            System.err.println(zipFile.getName());
            if (!zipFile.exists()) {
                throw new IOException("需解压文件不存在.");
            }
            File pathFile = new File(descDir);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            ZipFile zip = new ZipFile(zipFile, Charset.forName("utf-8"));
            for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                System.err.println(zipEntryName);
                InputStream in = zip.getInputStream(entry);
                String outPath = (descDir + File.separator + zipEntryName).replaceAll("\\*", "/");
                System.err.println(outPath);
                // 判断路径是否存在,不存在则创建文件路径(windows 下获取最后路径为\)modify by fmm
                File file = new File(outPath.substring(0, (outPath.lastIndexOf('/') == -1) ? outPath.lastIndexOf('\\') : outPath.lastIndexOf('/')));

                if (!file.exists()) {
                    file.mkdirs();
                }
                // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if (new File(outPath).isDirectory()) {
                    continue;
                }
                // 输出文件路径信息
                OutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();


            }
            //add by fmm
            zip.close();
            log.info("解压路径:{}. 解压完成. 耗时:{}ms. ", descDir, System.currentTimeMillis() - start);
        } catch (Exception e) {
            log.info("解压路径:{}. 解压异常:{}. 耗时:{}ms. ", descDir, e, System.currentTimeMillis() - start);
            throw new IOException(e);
        }
    }

    /**
     * 解压文件到指定目录
     */
    @SuppressWarnings({"rawtypes", "resource"})
    public static void unZipFiles(String zipPath, String descDir) throws IOException {
        log.info("文件:{}. 解压路径:{}. 解压开始.", zipPath, descDir);
        long start = System.currentTimeMillis();
        try {
            File zipFile = new File(zipPath);
            System.err.println(zipFile.getName());
            if (!zipFile.exists()) {
                throw new IOException("需解压文件不存在.");
            }
            File pathFile = new File(descDir);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            ZipFile zip = new ZipFile(zipFile, Charset.forName("utf-8"));
            for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                System.err.println(zipEntryName);
                InputStream in = zip.getInputStream(entry);
                String outPath = (descDir + File.separator + zipEntryName).replaceAll("\\*", "/");
                System.err.println(outPath);
                // 判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                if (!file.exists()) {
                    file.mkdirs();
                }
                // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if (new File(outPath).isDirectory()) {
                    continue;
                }
                // 输出文件路径信息
                OutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
            }
            log.info("文件:{}. 解压路径:{}. 解压完成. 耗时:{}ms. ", zipPath, descDir, System.currentTimeMillis() - start);
        } catch (Exception e) {
            log.info("文件:{}. 解压路径:{}. 解压异常:{}. 耗时:{}ms. ", zipPath, descDir, e, System.currentTimeMillis() - start);
            throw new IOException(e);
        }
    }

    // 删除文件或文件夹以及文件夹下所有文件
    public static void delDir(String dirPath) throws IOException {
        log.info("删除文件开始:{}.", dirPath);
        long start = System.currentTimeMillis();
        try {
            File dirFile = new File(dirPath);
            if (!dirFile.exists()) {
                return;
            }
            if (dirFile.isFile()) {
                if (!dirFile.delete()) {
                    throw new IOException("删除文件失败.");
                }
                return;
            }
            File[] files = dirFile.listFiles();
            if (files == null) {
                return;
            }
            for (int i = 0; i < files.length; i++) {
                delDir(files[i].toString());
            }


            if (!dirFile.delete()) {
                throw new IOException("删除文件失败.");
            }
            log.info("删除文件:{}. 耗时:{}ms. ", dirPath, System.currentTimeMillis() - start);
        } catch (Exception e) {
            log.info("删除文件:{}. 异常:{}. 耗时:{}ms. ", dirPath, e, System.currentTimeMillis() - start);
            throw new IOException("删除文件异常.");
        }
    }

    /**
     * add by fmm
     * 删除文件夹下的所有文件
     *
     * @param dirPath
     * @throws IOException
     */
    public static void delDirFile(String dirPath) throws IOException {
        log.info("删除文件开始:{}.", dirPath);
        long start = System.currentTimeMillis();
        try {
            File dirFile = new File(dirPath);
            if (!dirFile.exists()) {
                return;
            }
            if (dirFile.isFile()) {
                if (!dirFile.delete()) {
                    throw new IOException("删除文件失败.");
                }
                return;
            }
            File[] files = dirFile.listFiles();
            if (files == null) {
                return;
            }
            for (int i = 0; i < files.length; i++) {
                delDir(files[i].toString());
            }

           /* modify by fmm 不需要删除文件夹
           if(!dirFile.delete()){
                throw new IOException("删除文件失败.");
            }*/
            log.info("删除文件:{}. 耗时:{}ms. ", dirPath, System.currentTimeMillis() - start);
        } catch (Exception e) {
            log.info("删除文件:{}. 异常:{}. 耗时:{}ms. ", dirPath, e, System.currentTimeMillis() - start);
            throw new IOException("删除文件异常.");
        }
    }

    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            try {
                ins = file.getInputStream();
                toFile = new File(file.getOriginalFilename());
                inputStreamToFile(ins, toFile);
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return toFile;
    }

    //复制文件
    public static void copyFile(String oldPath, String newPath, String fileName) throws IOException {
        File oldFile = new File(oldPath + "/" + fileName);
        if (!oldFile.exists()) {
            throw new IOException("需复制文件不存在.");
        }
        File newPathFolder = new File(newPath);
        if (!newPathFolder.exists()) {
            newPathFolder.mkdirs();
        }
        File newFile = new File(newPath + "/" + fileName);
        if (newFile.exists()) {
            delDir(newFile.getPath());
        }
        //modify by fmm 不需要创建文件，直接复制生成
       /* if(!newFile.createNewFile()){
            throw new IOException("创建复制目标目录失败");
        }*/
        Files.copy(oldFile.toPath(), newFile.toPath());
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * add by fmm
     * 创建文件顺便创建父目录
     *
     * @param file file类
     */
    public static void createFile(File file) {
        /*if (file.exists() && file.isFile()) {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }*/
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述:
     * 对指定文件进行打包
     *
     * @param serverPath // 压缩包的文件路径
     * @param srcfile    //需要压缩文件列表
     * @param zipName    //压缩文件名称
     * @throws IOException
     * @throws ServletException
     * @return:
     * @Author: vic
     * @Date: 2020/2/27 16:15
     */
    public static void createZipByFileList(String serverPath, List<File> srcfile, String zipName, boolean delete) throws IOException, ServletException {

        FileOutputStream outStream = null;
        ZipOutputStream toClient = null;
        try {
            File file = new File(serverPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(serverPath + "/" + zipName + ".zip");
            outStream = new FileOutputStream(file);
            toClient = new ZipOutputStream(outStream);
            zipFile(srcfile, toClient);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (toClient != null) {
                toClient.close();
            }
            if (outStream != null) {
                outStream.close();
            }
            /**是否删除文件列表**/
            if (delete) {
                for (int i = 0; i < srcfile.size(); i++) {
                    File f = srcfile.get(i);
                    f.delete();
                }
            }

        }
    }

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

                    final int MAX_BYTE = 50 * 1024; // 最大的流为10M
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
        } finally {
            if (outputstream != null) {
                outputstream.closeEntry();
            }
            if (bInStream != null) {
                bInStream.close(); // 关闭
            }
            if (bInStream != null) {
                inStream.close();
            }

        }
    }

    /**
     * add by fmm
     * 文件转成BYTE数组
     *
     * @param output
     * @return
     */
    public static byte[] fileToBytes(File output) {
        try {
            if (output == null) {
                return null;
            }
            byte[] bFile = new byte[(int) output.length()];
            FileInputStream fis = new FileInputStream(output);
            fis.read(bFile);
            fis.close();
            //删除生成的临时文件夹
            FileUtil.delDir(output.getParentFile().getPath());
            return bFile;
        } catch (Exception e) {
            log.error("生成文件失败!!");
            e.printStackTrace();
        }
        return null;

    }
}
