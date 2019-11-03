package com.max.easyhub.cpux.tools;

import java.io.File;

import com.max.easyhub.cpux.tools.ExecShell.SHELL_CMD;

public class RootChecker {
	private static String LOG_TAG = RootChecker.class.getName();

    public String isDeviceRooted() {
        if (checkRootMethod1()){
        	return "Yes";
        }
        if (checkRootMethod2()){
        	return "Yes";
        }
        if (checkRootMethod3()){
        	return "Yes";
        }
        return "No";
    }

    public boolean checkRootMethod1(){
        String buildTags = android.os.Build.TAGS;

        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }
        return false;
    }

    public boolean checkRootMethod2(){
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            }
        } catch (Exception e) { }

        return false;
    }

    public boolean checkRootMethod3() {
        if (new ExecShell().executeCommand(SHELL_CMD.check_su_binary) != null){
            return true;
        }else{
            return false;
        }
    }
}

