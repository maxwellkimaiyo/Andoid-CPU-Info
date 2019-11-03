package com.max.easyhub.cpux.tools;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.telephony.TelephonyManager;

public class SensorUtil {
	
	public static String getBluetoothSupport(PackageManager pm) {
		if (pm.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getWiFiSupport(PackageManager pm) {
		if (pm.hasSystemFeature(PackageManager.FEATURE_WIFI)) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getGPSSupport(PackageManager pm) {
		if (pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getLiveWallpapersSupport(PackageManager pm) {
		if (pm.hasSystemFeature(PackageManager.FEATURE_LIVE_WALLPAPER)) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getLiveMicrophoneSupport(PackageManager pm) {
		if (pm.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getLiveAcceleratorMeterSupport(PackageManager pm) {
		if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER)) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getBarometerSupport(PackageManager pm) {
		if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_BAROMETER)) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getCompassSupport(PackageManager pm) {
		if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS)) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getGyscopeSupport(PackageManager pm) {
		if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE)) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getLightsSupport(PackageManager pm) {
		if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_LIGHT)) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getProximitySupport(PackageManager pm) {
		if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY)) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}


	public static String getNFCSupport(PackageManager pm) {
		if (pm.hasSystemFeature(PackageManager.FEATURE_NFC)) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getMagneticFieldSupport(SensorManager sm) {
		if (sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getLinearAccelerationSupport(SensorManager sm) {
		if (sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getOrientationSupport(SensorManager sm) {
		if (sm.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getPressureSupport(SensorManager sm) {
		if (sm.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getRotationVectorSupport(SensorManager sm) {
		if (sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getTemparatureSupport(SensorManager sm) {
		if (sm.getDefaultSensor(Sensor.TYPE_TEMPERATURE) != null) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}

	public static String getGravitySupport(SensorManager sm) {
		if (sm.getDefaultSensor(Sensor.TYPE_GRAVITY) != null) {
			return "Supported";
		} else {
			return "Not Supported";
		}
	}
}
