package com.van.fileselector.constant;

public class LengthConstant {
    public interface Size {
        public static final long B = 1024;
        public static final long KB = B * 1024;
        public static final long MB = KB * 1024;
        public static final long GB = MB * 1024;
        public static final long TB = GB * 1024;
    }

    public interface Name {
        public static final String B = "B";
        public static final String KB = "KB";
        public static final String MB = "MB";
        public static final String GB = "GB";
        public static final String TB = "TB";
    }
}
