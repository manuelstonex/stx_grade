package com.van.sysmcu;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.van.fileselector.helper.FileSelector;
import com.van.fileselector.util.FileSelectorUtils;
import com.van.jni.VanMcu;

import java.io.File;
import java.util.ArrayList;

public class AdvancedFragment extends Fragment implements View.OnClickListener {
    private TextView tvFilePath;
    private Button btnChoice;
    private Button btnUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_advanced, container, false);

        tvFilePath = root.findViewById(R.id.file_path);
        btnChoice = root.findViewById(R.id.choice);
        btnUpdate = root.findViewById(R.id.update);

        btnChoice.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choice:
                choice();
                break;
            case R.id.update:
                update();
                break;
        }
    }

    private void choice() {
        Intent intent = new Intent(getActivity(), FileSelectActivity.class);
        intent.putExtra("filter", "bin");
        FileSelectActivity.setFileSelectListener(mFileSelectListener);
        getActivity().startActivity(intent);
    }

    private void update() {
        String path = tvFilePath.getText().toString();
        if (TextUtils.isEmpty(path)) {
            return;
        }

        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(getActivity(), R.string.file_not_exists, Toast.LENGTH_SHORT).show();
            return;
        }

        if (file.isDirectory() || file.length()>50000) {
            Toast.makeText(getActivity(), R.string.illegal_file, Toast.LENGTH_SHORT).show();
            return;
        }

        btnUpdate.setEnabled(false);
        //final ProgressDialog dialog = ProgressDialog.show(getActivity(), "更新MCU", "正在更新...", true, false);
        boolean ret = VanMcu.UpdateFirmware(path);
        btnUpdate.setEnabled(true);
        //dialog.dismiss();
        Toast.makeText(getActivity(), String.format("更新%s", ret ? "成功" : "失败"), Toast.LENGTH_SHORT).show();
    }

    private FileSelector.OnFileSelectListener mFileSelectListener = new FileSelector.OnFileSelectListener() {
        @Override
        public void onSelected(ArrayList<String> list) {
            if (FileSelectorUtils.isEmpty(list)) {
                return;
            }

            String path = list.get(0);
            tvFilePath.setText(path);
        }
    };
}
