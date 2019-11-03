package com.max.easyhub.cpux.tools;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

public class MemoryUtils {
	private static final Pattern DIR_SEPORATOR = Pattern.compile("/");

	public static boolean externalMemoryAvailable() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	public static long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return (availableBlocks * blockSize);
	}

	public static long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return (totalBlocks * blockSize);
	}

	public static long getFreeInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long total = (long) stat.getBlockCount() * (long) stat.getBlockSize();
		long available = (long) stat.getAvailableBlocks()
				* (long) stat.getBlockSize();
		long free = Math.abs(total - available);
		return free;
	}

//	public static long getAvailableExternalMemorySize(File path) {
//		if (externalMemoryAvailable()) {
//			StatFs stat = new StatFs(path.getPath());
//			long blockSize = stat.getBlockSize();
//			long availableBlocks = stat.getAvailableBlocks();
//			return (availableBlocks * blockSize);
//		} else {
//			return 0L;
//		}
//	}
//
	public static long getTotalExternalMemorySize(File path) {
		if (externalMemoryAvailable()) {
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return (totalBlocks * blockSize);
		} else {
			return 0L;
		}
	}

	public static long getFreeExternalMemorySize(File path) {
		if (externalMemoryAvailable()) {
			StatFs stat = new StatFs(path.getPath());
			long total = (long) stat.getBlockCount()* (long) stat.getBlockSize();
			long available = (long) stat.getAvailableBlocks()* (long) stat.getBlockSize();
			long free = 0L;
			free = Math.abs(total - available);
			return free;
		} else {
			return 0;
		}
	}

	public static String[] getStorageDirectories() {
		// Final set of paths
		final Set<String> rv = new HashSet<String>();
		// Primary physical SD-CARD (not emulated)
		final String rawExternalStorage = System.getenv("EXTERNAL_STORAGE");
		// All Secondary SD-CARDs (all exclude primary) separated by ":"
		final String rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE");
		// Primary emulated SD-CARD
		final String rawEmulatedStorageTarget = System.getenv("EMULATED_STORAGE_TARGET");
		if (TextUtils.isEmpty(rawEmulatedStorageTarget)) {
			// Device has physical external storage; use plain paths.
			if (TextUtils.isEmpty(rawExternalStorage)) {
				// EXTERNAL_STORAGE undefined; falling back to default.
				// rv.add("/storage/sdcard0");
			} else {
				// rv.add(rawExternalStorage);
			}
		} else {
			// Device has emulated storage; external storage paths should have
			// userId burned into them.
			final String rawUserId;
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
				rawUserId = "";
			} else {
				final String path = Environment.getExternalStorageDirectory()
						.getAbsolutePath();
				final String[] folders = DIR_SEPORATOR.split(path);
				final String lastFolder = folders[folders.length - 1];
				boolean isDigit = false;
				try {
					Integer.valueOf(lastFolder);
					isDigit = true;
				} catch (NumberFormatException ignored) {
				}
				rawUserId = isDigit ? lastFolder : "";
			}
			// /storage/emulated/0[1,2,...]
			if (TextUtils.isEmpty(rawUserId)) {
				// rv.add(rawEmulatedStorageTarget);
			} else {
				// rv.add(rawEmulatedStorageTarget + File.separator +
				// rawUserId);
			}
		}
		// Add all secondary storages
		if (!TextUtils.isEmpty(rawSecondaryStoragesStr)) {
			// All Secondary SD-CARDs splited into array
			final String[] rawSecondaryStorages = rawSecondaryStoragesStr
					.split(File.pathSeparator);
			Collections.addAll(rv, rawSecondaryStorages);
		}
		return rv.toArray(new String[rv.size()]);
	}

	public static String formatMemSize(long size, int value) {
		String result = "";
		if (1024L > size) {
			String info = String.valueOf(size);
			result = (new StringBuilder(info)).append(" B").toString();
		} else if (1048576L > size) {
			String s2 = (new StringBuilder("%.")).append(value).append("f")
					.toString();
			Object aobj[] = new Object[1];
			Float float1 = Float.valueOf((float) size / 1024F);
			aobj[0] = float1;
			String s3 = String.valueOf(String.format(s2, aobj));
			result = (new StringBuilder(s3)).append(" KB").toString();
		} else if (1073741824L > size) {
			String s4 = (new StringBuilder("%.")).append(value).append("f")
					.toString();
			Object aobj1[] = new Object[1];
			Float float2 = Float.valueOf((float) size / 1048576F);
			aobj1[0] = float2;
			String s5 = String.valueOf(String.format(s4, aobj1));
			result = (new StringBuilder(s5)).append(" MB").toString();
		} else {
			Object aobj2[] = new Object[1];
			Float float3 = Float.valueOf((float) size / 1.073742E+009F);
			aobj2[0] = float3;
			String s6 = String.valueOf(String.format("%.2f", aobj2));
			result = (new StringBuilder(s6)).append(" GB").toString();
		}
		return result;
	}
	
	//Total RAM
	public static String getTotalRam(){
	    int n = 0;
	    try{
	      String[] arrayOfString = new java.io.RandomAccessFile("/proc/meminfo", "r").readLine().split(" kB")[0].split(" ");
	      n = Integer.parseInt(arrayOfString[(-1 + arrayOfString.length)]);
	      int i2 = Math.round(n / 1024);
	      return i2 +" MB";
	    }catch (IOException localIOException) {
	      return 0+" MB";
	    }
	}
	
	//Available RAM
	public static String getAvailableRam(Context context){
	    ActivityManager localActivityManager = (ActivityManager)context.getSystemService("activity");
	    ActivityManager.MemoryInfo localMemoryInfo = new ActivityManager.MemoryInfo();
	    localActivityManager.getMemoryInfo(localMemoryInfo);
	    return (int)(localMemoryInfo.availMem / 1024L / 1024L) +" MB";
	}
}
