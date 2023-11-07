package com.deviceDetails;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import com.utility.LoggingUtils;
import org.testng.Reporter;

public class DeviceDetails {

	public static String outputText;
	public static String DeviceModel;
	static String cmd = "";
	static String AppDetails = "";
	static ArrayList<String> devices = new ArrayList<String>();
	static ArrayList<String> deviceManufacturer = new ArrayList<String>();
	static HashSet<String> hs = new HashSet<String>();
	static ArrayList<String> deviceOS = new ArrayList<String>();
	static HashSet<String> hsOS = new HashSet<String>();
	public static String OEM;
	public static String osDevices=System.getProperty("os.name").toLowerCase();

	/** The Constant logger. */
	static LoggingUtils logger = new LoggingUtils();

	
	//Get Application Details
	public static String getAppDetails(String str) {
		Process p;
		try {
			getListOfDevicesConnected();
			String cmd = "";
			if (AppDetails.isEmpty()) {
				cmd = "adb -s " + devices.get(0) + " shell \"dumpsys package " + str + " | grep versionName\"";
				AppDetails = str;
			} else if (!AppDetails.isEmpty()) {
				cmd = "adb -s " + devices.get(1) + " shell \"dumpsys package " + str + " | grep versionName\"";
			}
			if(osDevices.contains("mac")) {
				p =Runtime.getRuntime().exec(new String[] {"bash","-l","-c",cmd});
			}else {
				 p = Runtime.getRuntime().exec(cmd);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

			while ((DeviceModel = br.readLine()) != null) {
				logger.info("App Details :: " + DeviceModel.trim());
				break;
			}
		} catch (Exception e) {
		}

		return DeviceModel;
	}
	
	//Get Application Version
	public static String getAppVersion(String packageName) {
		Process p;
		try {
			cmd = "adb shell \"dumpsys package " + packageName + " | grep versionName\"";
			if(osDevices.contains("mac")) {
				 p =Runtime.getRuntime().exec(new String[] {"bash","-l","-c",cmd});
			}else {
				 p = Runtime.getRuntime().exec(cmd);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((DeviceModel = br.readLine()) != null) {
				logger.info("Build Version : " + DeviceModel.trim());
				return DeviceModel.trim();
			}
		} catch (Exception e) {
			
		}
		return "";
	}

	//Device Brand
	public static void getTheDeviceManufacturer() {
		devices.removeAll(devices);
		deviceManufacturer.removeAll(deviceManufacturer);
		getListOfDevicesConnected();
		Process process;
		try {
			for (int i = 0; i <= getListOfDevicesConnected().size() - 1; i++) {
				String cmd3 = "adb -s " + devices.get(i) + " shell getprop ro.product.manufacturer";
				if(osDevices.contains("mac")) {
					process =Runtime.getRuntime().exec(new String[] {"bash","-l","-c",cmd3});
				}else {
					 process = Runtime.getRuntime().exec(cmd3);
				}
				
				
				BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
				deviceManufacturer.add(br.readLine());
				OEM = deviceManufacturer.get(0);
			}
			hs.addAll(deviceManufacturer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Get The OS Version 
	public static String getTheOSVersion() {
		try {
			String cmd1 = "adb shell getprop ro.build.version.release";
			Process p1;
			if(osDevices.contains("mac")) {
				 p1 =Runtime.getRuntime().exec(new String[] {"bash","-l","-c",cmd1});
			}else {
				 p1 = Runtime.getRuntime().exec(cmd1);
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			while ((outputText = br.readLine()) != null) {
				logger.info("OS Version :: " + outputText.toString());
				Thread.sleep(3000);
				break;
			}

		} catch (Exception e) {
		}
		return outputText;
	}

	//Remove Permission
	public static void removePermisson(String packagename) {
		String cmd2 = "adb shell pm clear " + packagename;
		Process process;
		try {
			if(osDevices.contains("mac")) {
				process =Runtime.getRuntime().exec(new String[] {"bash","-l","-c",cmd2});
			}else {
				process=Runtime.getRuntime().exec(cmd2);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//List Of Devices Connected
	public static ArrayList<String> getListOfDevicesConnected() {
		Process process; 
		try {
			if (osDevices.contains("mac")) {
			} else {
				String cmd2 = "adb devices";

				if (osDevices.contains("mac")) {
					process = Runtime.getRuntime().exec(new String[]{"bash", "-l", "-c", cmd2});
				} else {
					process = Runtime.getRuntime().exec(cmd2);
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String s = "";
				Thread.sleep(1000);
				devices.removeAll(devices);
				while (!(s = br.readLine()).isEmpty()) {
					if (!s.equals("List of devices attached")) {
						devices.add(s.replaceAll("device", "").trim());
					}
				}

				return devices;
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return devices;
	}

	//Device Name
	public static String deviceNames() {
		ArrayList<String> a = new ArrayList<String>();
		a.addAll(hs);
		return a.get(0);

	}

	//Device OS
	public static String deviceOS() {
		ArrayList<String> a = new ArrayList<String>();
		a.addAll(hsOS);
			return a.get(0);
	}

	//Get Device OS Version
	public static void getTheDeviceOSVersion() {

		devices.removeAll(devices);
		deviceOS.removeAll(deviceOS);
		getListOfDevicesConnected();
		Process process;

		try {
			for (int i = 0; i <= getListOfDevicesConnected().size() - 1; i++) {
				String cmd3 = "adb -s " + devices.get(i) + " shell getprop ro.build.version.release";
				if(osDevices.contains("mac")) {
					 process =Runtime.getRuntime().exec(new String[] {"bash","-l","-c",cmd3});
				}else {
					 process = Runtime.getRuntime().exec(cmd3);
				}
					
				BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
				deviceOS.add(br.readLine());
			}
			hsOS.addAll(deviceOS);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//Get Device Name and Version
	public static String DeviceInfo() {

		String DeviceInfo;
		String getDeviceName = deviceNames();
		String getOSName = deviceOS();
		DeviceInfo = "Device Name - " + getDeviceName + " Version - " + getOSName;
		logger.info("Device Name : "+getDeviceName+"\n"+"OS Version : "+getOSName);
		return DeviceInfo;
	}

	//List Of DevicesConnected
	public static String getListOfDevicesConnected1() {
		String deviceID = null;
		try {
			String cmd2 = "adb devices";
			if(osDevices.contains("mac")) {
				Process p1 =Runtime.getRuntime().exec(new String[] {"bash","-l","-c",cmd2});
			}else {
				Process p1 = Runtime.getRuntime().exec(cmd2);
			}
			
			Process p1 = Runtime.getRuntime().exec(cmd2);
			BufferedReader br = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			String s = "";
			Thread.sleep(1000);
			while (!(s = br.readLine()).isEmpty()) {
				if (!s.equals("List of devices attached")) {
					if(!s.contains(".")) {
						deviceID = s.replaceAll("device", "").trim();
						System.out.println(deviceID);
					}
				}
			}
			return deviceID;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return deviceID;
	}
	
	//Device Details
	public static void deviceDetails() {
		String cmd3 = "adb shell getprop ro.product.manufacturer";
		Process process;
		try {
			if(osDevices.contains("mac")) {
				process=Runtime.getRuntime().exec(new String[] {"bash","-l","-c",cmd3});
			}else {
				process = Runtime.getRuntime().exec(cmd3);
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String DeviceName = br.readLine();
			logger.info("Device Name : "+DeviceName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		getTheOSVersion();
	}
	
	//Application Size
	@SuppressWarnings("unused")
	public static void appSize() {
		String cmd = "adb shell pm path com.graymatrix.did";
		Process process;
		String pathAPK = null;
		String SizeAfterInstalling = null;
		try {
			if(osDevices.contains("mac")) {
				process=Runtime.getRuntime().exec(new String[] {"bash","-l","-c",cmd});
			}else {
				process = Runtime.getRuntime().exec(cmd);
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			pathAPK = br.readLine().replaceAll("package:", "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(pathAPK);
		String cmd1 = "adb shell stat "+pathAPK+" |grep Size";
		Process p1;
		try {
			
				if(osDevices.contains("mac")) {
					p1=Runtime.getRuntime().exec(new String[] {"bash","-l","-c",cmd1});
				}else {
					p1 = Runtime.getRuntime().exec(cmd1);
				}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			SizeAfterInstalling = br.readLine().split("	")[0];
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void Test() {
		File f = new File("news.easynews.com.newsrc");
		System.out.println(f.length());
		System.out.println((f.length()) + " Mb");
		long MEGABYTE = 1024L * 1024L;
		System.out.println(f.length() / MEGABYTE);
	}
	
	//List Of Devices Visibility
	public static String multiDeviceDetails(String deviceUdid) {
		String DeviceName="";
		String cmd3 = "adb -s "+deviceUdid+" shell getprop ro.product.manufacturer";
		Process process;
		try {
			if(osDevices.contains("mac")) {
			process=Runtime.getRuntime().exec(new String[] {"bash","-l","-c",cmd3});
			}else {
				
				process = Runtime.getRuntime().exec(cmd3);
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			DeviceName = br.readLine();
			logger.info("Device Name  : "+DeviceName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return DeviceName;
	}
	
	//Platform Version
	public static String platformVersion() {
		String platformVersion=null;
		String cmd3 = "adb shell getprop ro.build.version.release";
		Process process;
		try {
			if(osDevices.contains("mac")) {
				process=Runtime.getRuntime().exec(new String[] {"bash","-l","-c",cmd3});
			}else {
				process = Runtime.getRuntime().exec(cmd3);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			platformVersion = br.readLine();
			logger.info("OS Version : "+platformVersion);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return platformVersion;
	}
}
