package com.sinocall.phonerecordera.util;

import android.os.Environment;

import com.sinocall.phonerecordera.PhonerecorderaApplication;
import com.sinocall.phonerecordera.dao.FileDownloadBean;
import com.sinocall.phonerecordera.greendao.gen.DaoSession;
import com.sinocall.phonerecordera.greendao.gen.FileDownloadBeanDao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 * Created by mac on 2017/12/25.
 */

public class FileUtils {

    public  static  final String DOWNLOAD_PATH  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PhoneRecord/";

    public static void renameFile(String srcFileName, String dstFileName, String fileExt){
        String filePath = FileUtils.DOWNLOAD_PATH + srcFileName;
        if(fileIsExist(filePath) == 1){
            File f = new File(filePath);
            f.renameTo(new File(FileUtils.DOWNLOAD_PATH + dstFileName + "." + fileExt));
        }
    }

    //判断文件是否在数据库中有下载记录。
    //有，返回存放路径。
    public static String getFilePath(String fid){
        String fPath = null;
        DaoSession daoSession;
        daoSession = PhonerecorderaApplication.getInstance().getDaoSession();
        FileDownloadBeanDao fileDownloadBeanDao = daoSession.getFileDownloadBeanDao();
        List<FileDownloadBean>  list =  fileDownloadBeanDao.queryRaw(" where _id = ?", fid);
        if (list != null && list.size() > 0){
            FileDownloadBean fb = list.get(0);
            fPath = fb.getFilePath();
        }
        return fPath;
    }

    public static void updateDownloadFileDBName(String fid, String newName){
        DaoSession daoSession;
        daoSession = PhonerecorderaApplication.getInstance().getDaoSession();
        FileDownloadBeanDao fileDownloadBeanDao = daoSession.getFileDownloadBeanDao();
        List<FileDownloadBean>  list =  fileDownloadBeanDao.queryRaw(" where _id = ?", fid);
        if (list != null && list.size() > 0){
            FileDownloadBean fb = list.get(0);
            fb.setFileName(newName);
            fb.setFilePath(FileUtils.DOWNLOAD_PATH + newName);
            fileDownloadBeanDao.insertOrReplace(fb);
        }
    }

    public static int  fileIsExist(String strFile)
    {
        int ret = 0;
        try
        {
            File f=new File(strFile);
            if(f.exists())
            {
                ret = 1;
            }

        }
        catch (Exception e) {
            ret = 0;
        }
        return ret;
    }

    public static void insertDownloadToDB(FileDownloadBean entity){
        DaoSession daoSession;
        daoSession = PhonerecorderaApplication.getInstance().getDaoSession();
        FileDownloadBeanDao fileDownloadBeanDao = daoSession.getFileDownloadBeanDao();
        fileDownloadBeanDao.insert(entity);
    }


    public final static String ENCRYPTION_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String ALGORITHM = "AES";
    private static final int CACHE_SIZE = 1024;


    private static Key toKey(byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
        return secretKey;
    }

    public static void decryptFile(String key, String sourceFilePath,
                                   String destFilePath) throws Exception {
        File sourceFile = new File(sourceFilePath);
        File destFile = new File(destFilePath);
        FileInputStream in = null;
        FileOutputStream out = null;
        CipherOutputStream cout = null;
        try {
            if (sourceFile.exists() && sourceFile.isFile()) {
                if (!destFile.getParentFile().exists()) {
                    destFile.getParentFile().mkdirs();
                }
                destFile.createNewFile();
                in = new FileInputStream(sourceFile);
                out = new FileOutputStream(destFile);

                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(key.getBytes());
                byte[] digest = md.digest();
                Key k = toKey(digest);
                byte[] raw = k.getEncoded();
                SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
                Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                cout = new CipherOutputStream(out, cipher);
                byte[] cache = new byte[CACHE_SIZE];
                int nRead = 0;
                while ((nRead = in.read(cache)) != -1) {
                    cout.write(cache, 0, nRead);
                    cout.flush();
                }
                cout.close();
                out.close();
                in.close();

            } else {
//                throw new FileNotFoundException("原文件" + sourceFilePath
//                        + "不存在，解密文件失败");
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (null != in) {
                in.close();
            }
            if (null != out) {
                out.close();
            }
            if (null != cout) {
                cout.close();
            }
        }
    }

    public static void deleteFile(String path){
        File f = new File(path);
        f.delete();
    }
    
    public static void deleteDBDownloadRecord(String fid){
        DaoSession daoSession;
        daoSession = PhonerecorderaApplication.getInstance().getDaoSession();
        FileDownloadBeanDao fileDownloadBeanDao = daoSession.getFileDownloadBeanDao();
        fileDownloadBeanDao.deleteByKey(Long.valueOf(fid));
    }


}
