package com.van.fileselector.provide.size;

import android.content.Context;

import androidx.annotation.NonNull;

import com.van.fileselector.R;
import com.van.fileselector.constant.LengthConstant;

import java.io.File;
import java.text.DecimalFormat;

public class FileSizeProvideDefault extends FileSizeProvide {
    private DecimalFormat format = new DecimalFormat("#.00");

    @Override
    public String getFileCountIfFolder(@NonNull Context context, @NonNull File[] files) {
        return files.length + " " + context.getResources().getString(R.string.folder_item);
    }

    @Override
    public String getFileLengthIfFile(@NonNull Context context, @NonNull File file) {
        long length = file.length();
        String fileLength;
        if (length < LengthConstant.Size.B) {
            fileLength = String.valueOf(length) + LengthConstant.Name.B;
        } else if (length < LengthConstant.Size.KB) {
            fileLength = format.format(length * 1.0 / LengthConstant.Size.B) + LengthConstant.Name.KB;
        } else if (length < LengthConstant.Size.MB) {
            fileLength = format.format(length * 1.0 / LengthConstant.Size.KB) + LengthConstant.Name.MB;
        } else if (length < LengthConstant.Size.GB) {
            fileLength = format.format(length * 1.0 / LengthConstant.Size.MB) + LengthConstant.Name.GB;
        } else {
            fileLength = format.format(length * 1.0 / LengthConstant.Size.GB) + LengthConstant.Name.TB;
        }
        return fileLength;
    }
}
