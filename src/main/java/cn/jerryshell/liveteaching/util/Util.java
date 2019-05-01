package cn.jerryshell.liveteaching.util;

public class Util {
    public static String getFileTypeByFilename(String filename) {
        String[] split = filename.split("\\.");
        return split[split.length - 1];
    }
}
