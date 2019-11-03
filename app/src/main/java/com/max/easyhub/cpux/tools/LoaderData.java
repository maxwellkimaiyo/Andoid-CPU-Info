package com.max.easyhub.cpux.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;

import com.max.easyhub.cpux.data.SharedPref;
import com.max.easyhub.cpux.model.TheInfo;

public class LoaderData {
	private static Context context;
	private static Activity activity;
	PackageManager pm ;
	SensorManager mSensorManager;
	
	public LoaderData(Activity activity) {
		super();
		this.context = activity;
		this.activity = activity;
		pm = context.getPackageManager();
		mSensorManager=(SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		
	}
	
	
	public void loadCpuInfo(){
		String s="";
		StringBuffer sb = new StringBuffer();
	    sb.append("Architecture : ").append(Build.CPU_ABI).append("\n");
	    sb.append("CPU Cores : ").append(GetNumberOfCores()+" cores").append("\n");
	    if (new File("/proc/cpuinfo").exists()) {
	        try {
	            BufferedReader br = new BufferedReader(new FileReader(new File("/proc/cpuinfo")));
	            String aLine;
	            while ((aLine = br.readLine()) != null) {
	                sb.append(aLine + "\n");
	            }
	            if (br != null) {
	                br.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	    }
		s=sb.toString();
		SharedPref.setCPUData(context, s);
	}
	
	public void loadDeviceInfo(){
		String s="";
		s=s+"Model : " + Build.MODEL + " (" + Build.PRODUCT + ")" +"\n";
		s=s+"Brand : " + Build.MANUFACTURER+"\n";
		s=s+"Board : " + Build.BOARD+"\n";
		s=s+"Bootloader : " + Build.BOOTLOADER+"\n"; 
		
		s=s+"Screen Resolution : " + getScreenResolution()+"\n";
		s=s+"Total RAM : " + MemoryUtils.getTotalRam()+"\n";
		s=s+"Available RAM : " + MemoryUtils.getAvailableRam(context)+"\n";
		s=s+"Networks Type : "+ getNetworkType(context)+"\n";
		
		s = s+ "-:-\n";// give separator
		s=s+"Internal Storage : \n";
		s=s+"# : Total ("+Formatter.formatFileSize(context,MemoryUtils.getTotalInternalMemorySize())+")\n";
		s=s+"# : Usage ("+Formatter.formatFileSize(context,MemoryUtils.getFreeInternalMemorySize())+")\n";
		// external
		String[] dir = MemoryUtils.getStorageDirectories();
		for (int j = 0; j < dir.length; j++) {
			if (!TextUtils.isEmpty(dir[j])) {
				File file = new File(dir[j]);
				if (file.exists() && file.length() > 0) {
					s = s+ "-:-\n";// give separator
					s=s+"External Storage : \n";
					s = s+ "# : Total ("+ Formatter.formatFileSize(context,MemoryUtils.getTotalExternalMemorySize(file))+ ")\n";
					s = s+ "# : Usage ("+ Formatter.formatFileSize(context,MemoryUtils.getFreeExternalMemorySize(file))+ ")\n";
				}
			}
		}
		SharedPref.setDeviceData(context, s);
	}
	
	public void loadSystemInfo(){
		String s="";
		s=s+"Android Version : " + Build.VERSION.RELEASE+"\n";
		s=s+"API Level : " + Build.VERSION.SDK_INT+"\n";
		s=s+"Kernel Architectute : " + System.getProperty("os.arch")+"\n";
		s=s+"Build ID : " + Build.DISPLAY+"\n";
		s=s+"Runtime Value : "+getJavaVM()+"\n";
		s=s+"Root Acces : "+new RootChecker().isDeviceRooted()+"\n";
		SharedPref.setSystemData(context, s);
	}
	
	public void loadBateryInfo(){
		String s="";
	    Intent intent = activity.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	    boolean isPresent = intent.getBooleanExtra("present", false);
		String technology = intent.getStringExtra("technology");
		int plugged = intent.getIntExtra("plugged", -1);
		int scale = intent.getIntExtra("scale", -1);
		int health = intent.getIntExtra("health", 0);
		int status = intent.getIntExtra("status", 0);
		int rawlevel = intent.getIntExtra("level", -1);
		int voltage = intent.getIntExtra("voltage", 0);
		int temperature = ( intent.getIntExtra("temperature", 0)/10);
		int level = 0;

		Bundle bundle = intent.getExtras();

		if (isPresent) {
			if (rawlevel >= 0 && scale > 0) {
				level = (rawlevel * 100) / scale;
			}

			s = s+"Battery Level: " + level + "%\n";
			s = s+"Technology: " + (technology = technology == "" ? "Not present" : technology) + "\n";
			s = s+"Plugged: " + BateryUtils.getPlugTypeString(plugged) + "\n";
			s = s+"Health: " + BateryUtils.getHealthString(health) + "\n";
			s = s+"Status: " + BateryUtils.getStatusString(status) + "\n";
			s = s+"Voltage: " + voltage + " mV\n";
			s = s+"Temperature: " + temperature + Character.toString ((char) 176) + " C\n";
		} else {
			s="<battery not present>";
		}
		SharedPref.setBateryData(context, s);
	}
	
	
	public void loadSupportInfo(){
		
		String s="";
		s=s+"Bluetooth : "+SensorUtil.getBluetoothSupport(pm)+"\n";
		s=s+"WiFi : "+SensorUtil.getWiFiSupport(pm)+"\n";
		s=s+"GPS : "+SensorUtil.getGPSSupport(pm)+"\n";
		s=s+"Live Wallpapers : "+SensorUtil.getLiveWallpapersSupport(pm)+"\n";
		s=s+"Microphone : "+SensorUtil.getLiveMicrophoneSupport(pm)+"\n";
		s = s+ "-:-\n";// give separator
		s=s+"Accelerometer : "+SensorUtil.getLiveAcceleratorMeterSupport(pm)+"\n";
		s=s+"Barometer : "+SensorUtil.getBarometerSupport(pm)+"\n";
		s=s+"Compass : "+SensorUtil.getCompassSupport(pm)+"\n";
		s=s+"Gyroscope : "+SensorUtil.getGyscopeSupport(pm)+"\n";
		s=s+"Light : "+SensorUtil.getLightsSupport(pm)+"\n";
		s=s+"Magnetic Field : "+SensorUtil.getMagneticFieldSupport(mSensorManager)+"\n";
		s=s+"Linear Accel. : "+SensorUtil.getLinearAccelerationSupport(mSensorManager)+"\n";
		s=s+"Orientation : "+SensorUtil.getOrientationSupport(mSensorManager)+"\n";
		s=s+"Pressure : "+SensorUtil.getPressureSupport(mSensorManager)+"\n";
		s=s+"Proximity : "+SensorUtil.getProximitySupport(pm)+"\n";
		s=s+"NFC : "+SensorUtil.getNFCSupport(pm)+"\n";
		s=s+"Rotation Vector : "+SensorUtil.getRotationVectorSupport(mSensorManager)+"\n";
		s=s+"Temparature : "+SensorUtil.getTemparatureSupport(mSensorManager)+"\n";
		s=s+"Gravity : "+SensorUtil.getGravitySupport(mSensorManager)+"\n";
		SharedPref.setSensorData(context, s);
	}
	
	
	
	public static ArrayList<TheInfo> getArrList(String string){
		ArrayList<TheInfo> infos = new ArrayList<TheInfo>();
		String arr[]=StrFormatter.splitEveryLIne(string);
		for (int i = 0; i < arr.length; i++) {
			if(!arr[i].trim().equals("")){
				String arr2[]=StrFormatter.splitWitDoubleDot(arr[i]);
				if(arr2.length>1 && arr2[1].trim().length()<50){
					if(arr2[0].trim().toLowerCase().equals("processor")){
						infos.add(new TheInfo("-", "-"));
					}
					infos.add(new TheInfo(StrFormatter.getFormattedName(arr2[0].trim()), arr2[1].trim()));
				}
			}
			
		}
		return infos;
	}
	
	private static String getScreenResolution(){
		DisplayMetrics displaymetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		return width+" x "+height+" pixels";
	}
	

	private String getJavaVM() {
		final String SELECT_RUNTIME_PROPERTY = "persist.sys.dalvik.vm.lib";
	    final String LIB_DALVIK = "libdvm.so";
	    final String LIB_ART = "libart.so";
	    final String LIB_ART_D = "libartd.so";
	    
        try {
            Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            try {
                Method get = systemProperties.getMethod("get",String.class, String.class);
                if (get == null) {
                    return "<unknown>";
                }
                try {
                    final String value = (String)get.invoke(systemProperties, SELECT_RUNTIME_PROPERTY,"Dalvik");
                    if (LIB_DALVIK.equals(value)) {
                        return "Dalvik";
                    } else if (LIB_ART.equals(value)) {
                        return "ART";
                    } else if (LIB_ART_D.equals(value)) {
                        return "ART debug build";
                    }

                    return value;
                } catch (IllegalAccessException e) {
                    return "IllegalAccessException";
                } catch (IllegalArgumentException e) {
                    return "IllegalArgumentException";
                } catch (InvocationTargetException e) {
                    return "InvocationTargetException";
                }
            } catch (NoSuchMethodException e) {
                return "SystemProperties.get(String key, String def) method is not found";
            }
        } catch (ClassNotFoundException e) {
            return "SystemProperties class is not found";
        }
    }
	
	public static String getNetworkType(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		switch (tm.getNetworkType()) {
		case TelephonyManager.PHONE_TYPE_CDMA:
			return "CDMA";

		case TelephonyManager.PHONE_TYPE_GSM:
			return "GSM";

		case TelephonyManager.PHONE_TYPE_NONE:
			return "None";
		}
		return null;
	}

	public static int GetNumberOfCores() {
		return (new File("/sys/devices/system/cpu/"))
				.listFiles(new FileFilter() {
					@Override
					public boolean accept(File f) {
						return Pattern.matches("cpu[0-9]+", f.getName());
					}
				}).length;
	}


}
