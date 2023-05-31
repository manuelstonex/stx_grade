package com.van.sysmcu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;
import com.van.fileselector.activity.BasePermissionActivity;
import com.van.fileselector.helper.FileSelector;
import com.van.fileselector.util.FileUtils;
import com.van.fileselector.widget.FileSelectorLayout;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class FileSelectActivity extends BasePermissionActivity {
    private static FileSelector.OnFileSelectListener mFileSelectListener;
    private TabLayout tabs;
    private String mFilter;
    private FileSelectorLayout innerSelector;
    private FileSelectorLayout outerSelector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_file_select);

        tabs = findViewById(R.id.tabs);
        innerSelector = findViewById(R.id.inner_selector);
        outerSelector = findViewById(R.id.outer_selector);

        Intent intent = getIntent();
        mFilter = intent.getStringExtra("filter");

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();

                if (0 == index) {
                    innerSelector.setVisibility(View.VISIBLE);
                    outerSelector.setVisibility(View.INVISIBLE);
                } else {
                    innerSelector.setVisibility(View.INVISIBLE);
                    outerSelector.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    protected void onPermissionSuccess() {
        setFilter(innerSelector);
        setFilter(outerSelector);
    }

    public static void setFileSelectListener(FileSelector.OnFileSelectListener l) {
        mFileSelectListener = l;
    }

    private void setFilter(FileSelectorLayout selectorLayout) {
        FileSelector selector = FileSelector.with(selectorLayout).listen(listener);

        if (!TextUtils.isEmpty(mFilter)) {
            selector.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (file.isHidden()) {
                        return false;
                    }

                    if (file.isFile()) {
                        return file.getName().toLowerCase().endsWith(mFilter);
                    }

                    return true;
                }
            });
        }

        if (selectorLayout == outerSelector) {
            String str = getSdCardPath();
            selector.setRootPath(getSdCardPath());
        } else {
            selector.setRootPath(FileUtils.getInnerRootFile().getPath());
        }

        selector.setup();
    }

    private FileSelector.OnFileSelectListener listener = new FileSelector.OnFileSelectListener() {
        @Override
        public void onSelected(ArrayList<String> list) {
            finish();

            if (null != mFileSelectListener) {
                mFileSelectListener.onSelected(list);
            }
        }
    };

    private String getSdCardPath() {
        String path = "";
        StorageManager manager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
        List<StorageVolume> volumes = manager.getStorageVolumes();
        List<String> tfPath = new ArrayList<>();

        for (StorageVolume volume : volumes) {
            try {
                tfPath.add(StorageVolume.class.getMethod("getPath").invoke(volume).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (String s : tfPath) {
            if (!s.contains("emulated")) {
                path = s;
                break;
            }
        }

        return path;
    }
}
