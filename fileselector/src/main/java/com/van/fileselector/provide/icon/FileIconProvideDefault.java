package com.van.fileselector.provide.icon;

import android.content.Context;

import com.van.fileselector.R;
import com.van.fileselector.util.FileSelectorUtils;
import com.van.fileselector.util.FileUtils;

import java.io.File;

public class FileIconProvideDefault extends FileIconProvide {

    //文件类型扩展名，对应于后面数组图片资源
    private static final String[] DRAWABLE_TYPES = {
            "mp4_avi_rm_rmvb_wma_wav",
            "png_jpg_jpeg",
            "doc_docx",
            "htm_html",
            "xls_xlsx",
            "ppt_pptx",
            "rar_zip_7z",
            "mp3",
            "txt",
            "pdf",
            "xml"};

    //各种类型文件的图片
    private static final int[] DRAWABLE_RES_ID = {
            R.mipmap.file_type_mp4,
            R.mipmap.file_type_png,
            R.mipmap.file_type_doc,
            R.mipmap.file_type_htm,
            R.mipmap.file_type_xls,
            R.mipmap.file_type_ppt,
            R.mipmap.file_type_rar,
            R.mipmap.file_type_mp3,
            R.mipmap.file_type_txt,
            R.mipmap.file_type_pdf,
            R.mipmap.file_type_xml};

    //文件夹图片 和 未知文件类型图片
    private static final int TYPE_FOLDER = R.mipmap.file_type_folder;
    private static final int TYPE_UNKNOWN = R.mipmap.file_type_unknow;


    /**
     * 获取文件类型图片
     *
     * @param context
     * @param file
     * @return
     */
    public int getFileDrawableResId(Context context, File file) {
        if (context == null || file == null) {
            return TYPE_UNKNOWN;
        }
        if (file.isDirectory()) {
            return TYPE_FOLDER;
        }
        String suffix = FileUtils.getFileSuffixWithOutPoint(file);
        if (FileSelectorUtils.isEmpty(suffix)) {
            return TYPE_UNKNOWN;
        }
        for (int i = 0; i < DRAWABLE_TYPES.length; i++) {
            String[] typeArray = DRAWABLE_TYPES[i].split("_");
            if (typeArray.length == 0) {
                continue;
            }
            for (String aTypeArray : typeArray) {
                if (aTypeArray.equals(suffix)) {
                    return DRAWABLE_RES_ID[i];
                }
            }
        }
        return TYPE_UNKNOWN;
    }
}
