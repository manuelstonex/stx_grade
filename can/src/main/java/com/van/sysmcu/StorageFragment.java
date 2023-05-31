package com.van.sysmcu;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.van.jni.VanMcu;

import java.io.UnsupportedEncodingException;

import static com.van.sysmcu.R.layout;

public class StorageFragment extends Fragment implements View.OnClickListener {
    private Spinner spBlock;
    private Button btnErase;
    private Button btnWrite;
    private Button btnRead;
    private EditText etWrite;
    private TextView tvRead;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(layout.fragment_storage, container, false);

        spBlock = root.findViewById(R.id.block);
        btnErase = root.findViewById(R.id.erase);
        btnWrite = root.findViewById(R.id.write);
        btnRead = root.findViewById(R.id.read);

        etWrite = root.findViewById(R.id.etWrite);
        tvRead = root.findViewById(R.id.tvRead);

        btnErase.setOnClickListener(this);
        btnWrite.setOnClickListener(this);
        btnRead.setOnClickListener(this);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initBlock();
    }

    @Override
    public void onClick(View v) {
        int count = spBlock.getCount();
        if (0 == count) {
            return;
        }

        switch (v.getId()) {
            case R.id.erase:
                erase();
                break;
            case R.id.write:
                write();
                break;
            case R.id.read:
                read();
                break;
        }
    }

    private void initBlock() {
        int count = VanMcu.getBlockCount();
        if (count <= 0) {
            return;
        }

        String[] items = new String[count];
        for (int i=0; i<count; ++i) {
            items[i] = String.format("Block %d", i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(layout.support_simple_spinner_dropdown_item);
        spBlock.setAdapter(adapter);
    }

    private void erase() {
        int index = spBlock.getSelectedItemPosition();

        boolean ret = VanMcu.BlockWrite(index, null);
        Toast.makeText(getActivity(), String.format("Block erase %s", ret ? "Success" : "Failed"),
                Toast.LENGTH_SHORT).show();
    }

    private void write() {
        String strData = etWrite.getText().toString();
        if (TextUtils.isEmpty(strData)) {
            return;
        }

        int index = spBlock.getSelectedItemPosition();
        byte[] data = null;

        try {
            data = strData.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            return;
        }

        boolean ret = VanMcu.BlockWrite(index, data);
        Toast.makeText(getActivity(), String.format("Block write %s", ret ? "Success" : "Failed"),
                Toast.LENGTH_SHORT).show();
    }

    private void read() {
        int index = spBlock.getSelectedItemPosition();
        byte[] data = new byte[250];

        boolean ret = VanMcu.BlockRead(index, data, data.length);
        if (!ret) {
            Toast.makeText(getActivity(), "读取失败",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder sb = new StringBuilder(data.length*3);

        final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        for (int i = 0; i < data.length; i++) {
            int value = data[i] & 0xff;
            sb.append(HEX[value/16]).append(HEX[value%16]).append(' ');
        }

        tvRead.setText(sb.toString());
    }
}
