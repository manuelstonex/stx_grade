package com.van.fileselector.provide.size;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileFilter;

public abstract class FileSizeProvide {

    public String getFileSize(Context context, File file, FileFilter fileFilter) {
        if (context == null || file == null) {
            return null;
        }
        if (file.isFile()) {
            return getFileLengthIfFile(context, file);
        } else {
            return getFileCountIfFolder(context, file.listFiles(fileFilter));
        }
    }

    public abstract String getFileCountIfFolder(@NonNull Context context, @NonNull File[] files);

    public abstract String getFileLengthIfFile(@NonNull Context context, @NonNull File file);
}
