package com.soyoungboy.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

/**
 * @Description 文件操作类
 * @author soyoungboy
 */
public class FileHelper {
	private final static String TAG = FileHelper.class.getSimpleName();

	private static final int FILE_BUFFER_SIZE = 51200;

	/** 
	 * @Description: 是否挂载SD�?
	 * @return：boolean    
	 */
	public static boolean isSDCardReady() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * @Description 获取内置SD卡根路径
	 * @return String
	 */
	public static String getSDCardPath() {
		if (isSDCardReady()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return null;
	}

	public static class SDCardInfo {
		/**总大�?*/
		public long total;
		/**剩余空间大小 */
		public long free;
	}

	/** 
	 * @Description: 获取SD卡空间信�?
	 * @return：SDCardInfo    
	 */
	public static SDCardInfo getSDCardInfo() {
		String sDcString = android.os.Environment.getExternalStorageState();

		if (sDcString.equals(android.os.Environment.MEDIA_MOUNTED)) {
			File pathFile = android.os.Environment.getExternalStorageDirectory();

			try {
				android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());

				// 获取SDCard上BLOCK总数
				long nTotalBlocks = statfs.getBlockCount();

				// 获取SDCard上每个block的SIZE
				long nBlocSize = statfs.getBlockSize();

				// 获取可供程序使用的Block的数�?
				long nAvailaBlock = statfs.getAvailableBlocks();

				// 获取剩下的所有Block的数�?包括预留的一般程序无法使用的�?
				long nFreeBlock = statfs.getFreeBlocks();

				SDCardInfo info = new SDCardInfo();
				// 计算SDCard 总容量大小MB
				info.total = nTotalBlocks * nBlocSize;

				// 计算 SDCard 剩余大小MB
				info.free = nAvailaBlock * nBlocSize;

				return info;
			} catch (IllegalArgumentException e) {
				Log.e(TAG, e.toString());
			}
		}
		return null;
	}

	/**
	 * @Description 获取文件的目�?
	 * @param pathandname 路径加文件全�?
	 * @return String
	 */
	public static String getPath(String pathandname) {
		if (TextUtils.isEmpty(pathandname)) {
			Log.e(TAG, "param invalid, filePath: " + pathandname);
			return null;
		}
		int end = pathandname.lastIndexOf("/");
		if (end != -1) {
			return pathandname.substring(0, end);
		} else {
			return null;
		}
	}

	/** 
	 * @Description: 根据路径获取文件�?
	 * @param filepath
	 * @return：String    
	 */
	public static String getName(String filepath) {
		if (TextUtils.isEmpty(filepath)) {
			Log.e(TAG, "param invalid, filePath: " + filepath);
			return null;
		}
		int pos = filepath.lastIndexOf('/');
		if (pos != -1) {
			return filepath.substring(pos + 1);
		}
		return null;
	}

	/** 
	 * @Description: 获取文件扩展�?
	 * @param filepath
	 * @return：String    
	 */
	public static String getExt(String filepath) {
		if (TextUtils.isEmpty(filepath)) {
			Log.e(TAG, "param invalid, filePath: " + filepath);
			return null;
		}
		int dotPosition = filepath.lastIndexOf('.');
		if (dotPosition != -1) {
			return filepath.substring(dotPosition + 1, filepath.length());
		}
		return null;
	}

	/**
	 * @Description 文件是否存在
	 * @param filePath
	 * @return boolean
	 */
	public static boolean fileIsExist(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			Log.e(TAG, "param invalid, filePath: " + filePath);
			return false;
		}
		File f = new File(filePath);
		return f.exists();
	}

	/**
	 * @Description 读文�?
	 * @param filePath
	 * @return InputStream
	 */
	public static InputStream readFile(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			Log.e(TAG, "Invalid param. filePath: " + filePath);
			return null;
		}
		InputStream is = null;
		try {
			if (fileIsExist(filePath)) {
				File f = new File(filePath);
				is = new FileInputStream(f);
			} else {
				return null;
			}
		} catch (Exception ex) {
			Log.e(TAG, "Exception, ex: " + ex.toString());
			return null;
		}
		return is;
	}

	/**
	 * @Description 创建目录
	 * @param filePath
	 * @return boolean
	 */
	public static boolean CreateDir(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			Log.e(TAG, "Invalid param. filePath: " + filePath);
			return false;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			return file.mkdirs(); // 文件不存在则创建目录            
		}
		return true;
	}

	/**
	 * 创建文件
	 */

	public static boolean createFile(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			Log.e(TAG, "Invalid param. filePath: " + filePath);
			return false;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				return file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * @Description 删除目录或文�?
	 * @param filePath
	 * @return boolean
	 */
	public static boolean deleteDirectory(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			Log.e(TAG, "Invalid param. filePath: " + filePath);
			return false;
		}

		File file = new File(filePath);

		if (file == null || !file.exists()) {
			return false;
		}

		if (file.isDirectory()) {
			File[] list = file.listFiles();
			for (int i = 0; i < list.length; i++) {
				Log.d(TAG, "delete filePath: " + list[i].getAbsolutePath());
				if (list[i].isDirectory()) {
					deleteDirectory(list[i].getAbsolutePath());
				} else {
					list[i].delete();
				}
			}
		}
		Log.d(TAG, "delete filePath: " + file.getAbsolutePath());
		file.delete();
		return true;
	}

	/**
	 * @Description 写文件（会删除原有同名文件）
	 * @param filePath
	 * @param inputStream
	 * @return boolean
	 */
	public static boolean writeFile(String filePath, InputStream inputStream) {

		if (TextUtils.isEmpty(filePath) || inputStream == null) {
			Log.e(TAG, "Invalid param. filePath: " + filePath);
			return false;
		}

		try {
			File file = new File(filePath);
			if (file.exists()) {
				deleteDirectory(filePath);
			}

			String pth = getPath(filePath);
			boolean ret = CreateDir(pth);
			if (!ret) {
				Log.e(TAG, "createDirectory fail path = " + pth);
				return false;
			}

			boolean ret1 = file.createNewFile();
			if (!ret1) {
				Log.e(TAG, "createNewFile fail filePath = " + filePath);
				return false;
			}

			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int c = inputStream.read(buf);
			while (-1 != c) {
				fileOutputStream.write(buf, 0, c);
				c = inputStream.read(buf);
			}

			fileOutputStream.flush();
			fileOutputStream.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @Description 写文�?会删除原有同名文�?
	 * @param filePath
	 * @param fileContent
	 * @return boolean
	 */
	public static boolean writeFile(String filePath, String fileContent) {
		return writeFile(filePath, fileContent, false);
	}

	/**
	 * @Description 写文�?
	 * @param filePath		文件路径
	 * @param fileContent	要写入的内容
	 * @param append		是否在原内容后追�?
	 * @return boolean
	 */
	public static boolean writeFile(String filePath, String fileContent, boolean append) {
		if (TextUtils.isEmpty(filePath) || TextUtils.isEmpty(fileContent)) {
			Log.e(TAG, "Invalid param. filePath: " + filePath + ", fileContent: " + fileContent);
			return false;
		}

		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (!file.createNewFile()) {
					return false;
				}
			}

			BufferedWriter output = new BufferedWriter(new FileWriter(file, append));
			output.write(fileContent);
			output.flush();
			output.close();
		} catch (IOException ioe) {
			Log.e(TAG, "writeFile ioe: " + ioe.toString());
			return false;
		}
		return true;
	}

	/**
	 * @Description 写文�?覆盖已存在文�?
	 * @param filePath
	 * @param content
	 * @return boolean
	 */
	public static boolean writeFile(String filePath, byte[] content) {
		if (TextUtils.isEmpty(filePath) || null == content) {
			Log.e(TAG, "Invalid param. filePath: " + filePath + ", content: " + content);
			return false;
		}

		FileOutputStream fos = null;
		try {
			String pth = getPath(filePath);
			File pf = null;
			pf = new File(pth);
			if (pf.exists() && !pf.isDirectory()) {
				pf.delete();
			}
			pf = new File(filePath);
			if (pf.exists()) {
				if (pf.isDirectory())
					deleteDirectory(filePath);
				else
					pf.delete();
			}

			pf = new File(pth + File.separator);
			if (!pf.exists()) {
				if (!pf.mkdirs()) {
					Log.e(TAG, "Can't make dirs, path=" + pth);
				}
			}

			fos = new FileOutputStream(filePath);
			fos.write(content);
			fos.flush();
			fos.close();
			fos = null;
			pf.setLastModified(System.currentTimeMillis());

			return true;

		} catch (Exception ex) {
			Log.e(TAG, "Exception, ex: " + ex.toString());
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (Exception ex) {
				}
				;
			}
		}
		return false;
	}

	/**
	 * @Description 获取文件大小
	 * @param filePath
	 * @return long 文件大小（B�?
	 */
	public static long getFileSize(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			Log.e(TAG, "Invalid param. filePath: " + filePath);
			return 0;
		}
		File file = new File(filePath);
		if (file == null || !file.exists()) {
			return 0;
		}
		return file.length();
	}

	/** 
	 * @Description: 文件大小单位转换
	 * @param size
	 * @return：String    
	 */
	public static String convertStorage(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;

		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}

	/**
	 * @Description 获取文件�?��修改时间
	 * @param filePath
	 * @return long
	 */
	public static long getFileModifyTime(String filePath) {
		if (TextUtils.isEmpty(filePath)) {
			Log.e(TAG, "Invalid param. filePath: " + filePath);
			return 0;
		}

		File file = new File(filePath);
		if (file == null || !file.exists()) {
			return 0;
		}
		return file.lastModified();
	}

	/**
	 * @Description 设置文件�?��修改时间
	 * @param filePath
	 * @param modifyTime
	 * @return boolean
	 */
	public static boolean setFileModifyTime(String filePath, long modifyTime) {
		if (TextUtils.isEmpty(filePath) || modifyTime < 0) {
			Log.e(TAG, "Invalid param. filePath: " + filePath + "modifyTime=" + modifyTime);
			return false;
		}

		File file = new File(filePath);
		if (file == null || !file.exists()) {
			return false;
		}
		return file.setLastModified(modifyTime);
	}

	/**
	 * @Description copy文件(会覆盖原文件或目�?
	 * @param cr
	 * @param fromPath
	 * @param destUri
	 * @return boolean
	 */
	public static boolean copyFile(ContentResolver cr, String fromPath, String destUri) {
		if (null == cr || TextUtils.isEmpty(fromPath) || TextUtils.isEmpty(destUri)) {
			Log.e(TAG, "copyFile Invalid param. cr=" + cr + ", fromPath=" + fromPath + ", destUri=" + destUri);
			return false;
		}

		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(fromPath);

			// check output uri
			String path = null;
			Uri uri = null;

			String lwUri = destUri.toLowerCase();
			if (lwUri.startsWith("content://")) {
				uri = Uri.parse(destUri);
			} else if (lwUri.startsWith("file://")) {
				uri = Uri.parse(destUri);
				path = uri.getPath();
			} else {
				path = destUri;
			}

			// open output
			if (null != path) {
				File fl = new File(path);
				String pth = getPath(path);
				File pf = new File(pth);

				if (pf.exists() && !pf.isDirectory()) {
					pf.delete();
				}

				pf = new File(pth + File.separator);

				if (!pf.exists()) {
					if (!pf.mkdirs()) {
						Log.e(TAG, "Can't make dirs, path=" + pth);
					}
				}

				pf = new File(path);
				if (pf.exists()) {
					if (pf.isDirectory())
						deleteDirectory(path);
					else
						pf.delete();
				}

				os = new FileOutputStream(path);
				fl.setLastModified(System.currentTimeMillis());
			} else {
				os = new ParcelFileDescriptor.AutoCloseOutputStream(cr.openFileDescriptor(uri, "w"));
			}

			// copy file
			byte[] dat = new byte[1024];
			int i = is.read(dat);
			while (-1 != i) {
				os.write(dat, 0, i);
				i = is.read(dat);
			}

			is.close();
			is = null;

			os.flush();
			os.close();
			os = null;

			return true;

		} catch (Exception ex) {
			Log.e(TAG, "Exception, ex: " + ex.toString());
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception ex) {
				}
				;
			}
			if (null != os) {
				try {
					os.close();
				} catch (Exception ex) {
				}
				;
			}
		}
		return false;
	}

	/**  
	 * 复制单个文件  
	 * @param oldPath String 原文件路�?如：c:/fqf.txt  
	 * @param newPath String 复制后路�?如：f:/fqf.txt  
	 * @return boolean  
	 */
	public static boolean copyFile(String oldPath, String newPath) {
		if (TextUtils.isEmpty(oldPath) || TextUtils.isEmpty(newPath)) {
			Log.e(TAG, "Invalid param. oldPath: " + oldPath + "newPath=" + newPath);
			return false;
		}
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { //文件存在�?  
				InputStream inStream = new FileInputStream(oldPath); //读入原文�?  
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; //字节�?文件大小   
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				return true;
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @Description InputStream转换为byte[]
	 * @param is
	 * @throws Exception
	 * @return byte[]
	 */
	public static byte[] inputStreamToByte(InputStream is) {
		if (is == null) {
			Log.e(TAG, "inputStreamToByte failed");
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		try {
			byte[] buf = new byte[1024];
			int c = is.read(buf);
			while (-1 != c) {
				baos.write(buf, 0, c);
				c = is.read(buf);
			}
			return baos.toByteArray();
		} catch (Exception e) {
			Log.e(TAG, "inputStreamToByte failed");
			e.printStackTrace();
		} finally {
			try {
				baos.flush();
				baos.close();
			} catch (Exception e2) {
			}
		}
		return null;
	}

	/**
	 * @Description 读文�?
	 * @param ctx
	 * @param uri
	 * @return byte[]
	 */
	public static byte[] readFile(Context ctx, Uri uri) {
		if (null == ctx || null == uri) {
			Log.e(TAG, "Invalid param. ctx: " + ctx + ", uri: " + uri);
			return null;
		}

		InputStream is = null;
		String scheme = uri.getScheme().toLowerCase();
		if (scheme.equals("file")) {
			is = readFile(uri.getPath());
		}

		try {
			is = ctx.getContentResolver().openInputStream(uri);
			if (null == is) {
				return null;
			}

			byte[] bret = inputStreamToByte(is);
			is.close();
			is = null;

			return bret;
		} catch (FileNotFoundException fne) {
			Log.e(TAG, "FilNotFoundException, ex: " + fne.toString());
		} catch (Exception ex) {
			Log.e(TAG, "Exception, ex: " + ex.toString());
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (Exception ex) {
				}
			}
		}
		return null;
	}

	/************* ZIP file operation ***************/

	/**
	 * @Description 读ZIP文件
	 * @param zipFileName
	 * @param crc
	 * @return
	 * @return boolean
	 */
	public static boolean readZipFile(String zipFileName, StringBuffer crc) {
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName));
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				long size = entry.getSize();
				crc.append(entry.getCrc() + ", size: " + size);
			}
			zis.close();
		} catch (Exception ex) {
			Log.e(TAG, "Exception: " + ex.toString());
			return false;
		}
		return true;
	}

	/**
	 * @Description 读GZIP文件
	 * @param zipFileName
	 * @return byte[]
	 */
	public static byte[] readGZipFile(String zipFileName) {
		if (fileIsExist(zipFileName)) {
			Log.i(TAG, "zipFileName: " + zipFileName);
			try {
				FileInputStream fin = new FileInputStream(zipFileName);
				int size;
				byte[] buffer = new byte[1024];
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((size = fin.read(buffer, 0, buffer.length)) != -1) {
					baos.write(buffer, 0, size);
				}
				return baos.toByteArray();
			} catch (Exception ex) {
				Log.i(TAG, "read zipRecorder file error");
			}
		}
		return null;
	}

	/**
	 * @Description 把文件压缩为ZIP文件
	 * @param baseDirName
	 * @param fileName
	 * @param targerFileName
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean zipFile(String baseDirName, String fileName, String targerFileName) throws IOException {
		if (baseDirName == null || "".equals(baseDirName)) {
			return false;
		}
		File baseDir = new File(baseDirName);
		if (!baseDir.exists() || !baseDir.isDirectory()) {
			return false;
		}

		String baseDirPath = baseDir.getAbsolutePath();
		File targerFile = new File(targerFileName);
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targerFile));
		File file = new File(baseDir, fileName);

		boolean zipResult = false;
		if (file.isFile()) {
			zipResult = fileToZip(baseDirPath, file, out);
		} else {
			zipResult = dirToZip(baseDirPath, file, out);
		}
		out.close();
		return zipResult;
	}

	/**
	 * @Description 解压ZIP文件
	 * @param fileName
	 * @param unZipDir
	 * @throws Exception
	 * @return boolean
	 */
	public static boolean unZipFile(String fileName, String unZipDir) throws Exception {
		File f = new File(unZipDir);

		if (!f.exists()) {
			f.mkdirs();
		}

		BufferedInputStream is = null;
		ZipEntry entry;
		ZipFile zipfile = new ZipFile(fileName);
		Enumeration<?> enumeration = zipfile.entries();
		byte data[] = new byte[FILE_BUFFER_SIZE];
		Log.i(TAG, "unZipDir: " + unZipDir);

		while (enumeration.hasMoreElements()) {
			entry = (ZipEntry) enumeration.nextElement();

			if (entry.isDirectory()) {
				File f1 = new File(unZipDir + "/" + entry.getName());
				Log.i(TAG, "entry.isDirectory XXX " + f1.getPath());
				if (!f1.exists()) {
					f1.mkdirs();
				}
			} else {
				is = new BufferedInputStream(zipfile.getInputStream(entry));
				int count;
				String name = unZipDir + "/" + entry.getName();
				RandomAccessFile m_randFile = null;
				File file = new File(name);
				if (file.exists()) {
					file.delete();
				}

				file.createNewFile();
				m_randFile = new RandomAccessFile(file, "rw");
				int begin = 0;

				while ((count = is.read(data, 0, FILE_BUFFER_SIZE)) != -1) {
					try {
						m_randFile.seek(begin);
					} catch (Exception ex) {
						Log.e(TAG, "exception, ex: " + ex.toString());
					}

					m_randFile.write(data, 0, count);
					begin = begin + count;
				}

				file.delete();
				m_randFile.close();
				is.close();
			}
		}

		return true;
	}

	/**
	 * @Description 文件压缩为ZipOutputStream�?
	 * @param baseDirPath
	 * @param file
	 * @param out
	 * @throws IOException
	 * @return boolean
	 */
	private static boolean fileToZip(String baseDirPath, File file, ZipOutputStream out) throws IOException {
		FileInputStream in = null;
		ZipEntry entry = null;

		byte[] buffer = new byte[FILE_BUFFER_SIZE];
		int bytes_read;
		try {
			in = new FileInputStream(file);
			entry = new ZipEntry(getEntryName(baseDirPath, file));
			out.putNextEntry(entry);

			while ((bytes_read = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytes_read);
			}
			out.closeEntry();
			in.close();
		} catch (IOException e) {
			Log.e(TAG, "Exception, ex: " + e.toString());
			return false;
		} finally {
			if (out != null) {
				out.closeEntry();
			}

			if (in != null) {
				in.close();
			}
		}
		return true;
	}

	/**
	 * @Description 目录文件夹压缩为ZipOutputStream�?
	 * @param baseDirPath
	 * @param file
	 * @param out
	 * @throws IOException
	 * @return boolean
	 */
	private static boolean dirToZip(String baseDirPath, File dir, ZipOutputStream out) throws IOException {
		if (!dir.isDirectory()) {
			return false;
		}

		File[] files = dir.listFiles();
		if (files.length == 0) {
			ZipEntry entry = new ZipEntry(getEntryName(baseDirPath, dir));

			try {
				out.putNextEntry(entry);
				out.closeEntry();
			} catch (IOException e) {
				Log.e(TAG, "Exception, ex: " + e.toString());
			}
		}

		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				fileToZip(baseDirPath, files[i], out);
			} else {
				dirToZip(baseDirPath, files[i], out);
			}
		}
		return true;
	}

	private static String getEntryName(String baseDirPath, File file) {
		if (!baseDirPath.endsWith(File.separator)) {
			baseDirPath = baseDirPath + File.separator;
		}

		String filePath = file.getAbsolutePath();
		if (file.isDirectory()) {
			filePath = filePath + "/";
		}

		int index = filePath.indexOf(baseDirPath);
		return filePath.substring(index + baseDirPath.length());
	}

	/*
	 * 采用了新的办法获取APK图标，之前的失败是因为android中存在的�?��BUG,通过 appInfo.publicSourceDir = apkPath;来修正这个问题，详情参见:
	 * http://code.google.com/p/android/issues/detail?id=9151
	 */
	public static Drawable getApkIcon(Context context, String apkPath) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
		if (info != null) {
			ApplicationInfo appInfo = info.applicationInfo;
			appInfo.sourceDir = apkPath;
			appInfo.publicSourceDir = apkPath;
			try {
				return appInfo.loadIcon(pm);
			} catch (OutOfMemoryError e) {
				Log.e(TAG, e.toString());
			}
		}
		return null;
	}

	/**
	 * uri转String
	 * @param contentUri
	 * @param mContext  
	 * @return
	 */
	public static String getRealPathFromURI(Uri contentUri, Context mContext) {
		String res = "";
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = mContext.getContentResolver().query(contentUri, proj, null, null, null);
		if (cursor != null) {
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			if (cursor.moveToFirst()) {
				res = cursor.getString(column_index);
			}
			cursor.close();
		}		
		return res;
	}
}