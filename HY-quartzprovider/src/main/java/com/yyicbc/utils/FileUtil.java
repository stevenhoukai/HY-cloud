package com.yyicbc.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Enumeration;
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
                //add by fmm 如果文件夹不存在则创建
                if(!zipFile.getParentFile().exists()){
                    zipFile.getParentFile().mkdirs();
                }

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
    @SuppressWarnings({ "rawtypes", "resource" })
    public static void unZipFiles(File zipFile, String descDir) throws IOException {
        log.info("解压路径:{}. 解压开始.",descDir);
        long start = System.currentTimeMillis();
        try{
            System.err.println(zipFile.getName());
            if(!zipFile.exists()){
                throw new IOException("需解压文件不存在.");
            }
            File pathFile = new File(descDir);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            ZipFile zip = new ZipFile(zipFile, Charset.forName("utf-8"));
            for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                System.err.println(zipEntryName);
                InputStream in = zip.getInputStream(entry);
                String outPath = (descDir + File.separator + zipEntryName).replaceAll("\\*", "/");
                System.err.println(outPath);
                // 判断路径是否存在,不存在则创建文件路径(windows 下获取最后路径为\)modify by fmm
                File file = new File(outPath.substring(0, (outPath.lastIndexOf('/')==-1)?outPath.lastIndexOf('\\'):outPath.lastIndexOf('/')));

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
            log.info("解压路径:{}. 解压完成. 耗时:{}ms. ",descDir,System.currentTimeMillis()-start);
        }catch(Exception e){
            log.info("解压路径:{}. 解压异常:{}. 耗时:{}ms. ",descDir,e,System.currentTimeMillis()-start);
            throw new IOException(e);
        }
    }

    /**
     * 解压文件到指定目录
     */
    @SuppressWarnings({ "rawtypes", "resource" })
    public static void unZipFiles(String zipPath, String descDir) throws IOException {
        log.info("文件:{}. 解压路径:{}. 解压开始.",zipPath,descDir);
        long start = System.currentTimeMillis();
        try{
            File zipFile = new File(zipPath);
            System.err.println(zipFile.getName());
            if(!zipFile.exists()){
                throw new IOException("需解压文件不存在.");
            }
            File pathFile = new File(descDir);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            ZipFile zip = new ZipFile(zipFile, Charset.forName("utf-8"));
            for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {
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
            log.info("文件:{}. 解压路径:{}. 解压完成. 耗时:{}ms. ",zipPath,descDir,System.currentTimeMillis()-start);
        }catch(Exception e){
            log.info("文件:{}. 解压路径:{}. 解压异常:{}. 耗时:{}ms. ",zipPath,descDir,e,System.currentTimeMillis()-start);
            throw new IOException(e);
        }
    }

    // 删除文件或文件夹以及文件夹下所有文件
    public static void delDir(String dirPath) throws IOException {
        log.info("删除文件开始:{}.",dirPath);
        long start = System.currentTimeMillis();
        try{
            File dirFile = new File(dirPath);
            if (!dirFile.exists()) {
                return;
            }
            if (dirFile.isFile()) {
                if(!dirFile.delete()){
                    throw new IOException("删除文件失败.");
                }
                return;
            }
            File[] files = dirFile.listFiles();
            if(files==null){
                return;
            }
            for (int i = 0; i < files.length; i++) {
                delDir(files[i].toString());
            }


           if(!dirFile.delete()){
                throw new IOException("删除文件失败.");
            }
            log.info("删除文件:{}. 耗时:{}ms. ",dirPath,System.currentTimeMillis()-start);
        }catch(Exception e){
            log.info("删除文件:{}. 异常:{}. 耗时:{}ms. ",dirPath,e,System.currentTimeMillis()-start);
            throw new IOException("删除文件异常.");
        }
    }



    /**
     * add by fmm
     * 删除文件夹下的所有文件
     * @param dirPath
     * @throws IOException
     */
    public static void delDirFile(String dirPath) throws IOException {
        log.info("删除文件开始:{}.",dirPath);
        long start = System.currentTimeMillis();
        try{
            File dirFile = new File(dirPath);
            if (!dirFile.exists()) {
                return;
            }
            if (dirFile.isFile()) {
                if(!dirFile.delete()){
                    throw new IOException("删除文件失败.");
                }
                return;
            }
            File[] files = dirFile.listFiles();
            if(files==null){
                return;
            }
            for (int i = 0; i < files.length; i++) {
                delDir(files[i].toString());
            }

           /* modify by fmm 不需要删除文件夹
           if(!dirFile.delete()){
                throw new IOException("删除文件失败.");
            }*/
            log.info("删除文件:{}. 耗时:{}ms. ",dirPath,System.currentTimeMillis()-start);
        }catch(Exception e){
            log.info("删除文件:{}. 异常:{}. 耗时:{}ms. ",dirPath,e,System.currentTimeMillis()-start);
            throw new IOException("删除文件异常.");
        }
    }
    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file){

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
        if(!oldFile.exists()){
            throw new IOException("需复制文件不存在.");
        }
        File newPathFolder = new File(newPath);
        if (!newPathFolder.exists()) {
            newPathFolder.mkdirs();
        }
        File newFile = new File(newPath + "/" + fileName);
        if(newFile.exists()){
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
     * 获取文件后缀名
     * @param file
     * @return
     */
    public static String getFileSuffix(File file){
        if(file == null){
            return "";
        }
        String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        return suffix;
    }

    /**
     * add by fmm
     * 创建文件夹
     * @param path
     * @return
     */
    public static void createFolder(String path){
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }

}
