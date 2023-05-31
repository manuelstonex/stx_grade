package com.van.sysmcu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.van.jni.VanMcu;

import java.lang.ref.WeakReference;

import static com.van.sysmcu.R.id;
import static com.van.sysmcu.R.layout;

public class UsualFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "UsualFragment";
    private VanHandler mHandler;
    private TextView tvVersion;
    private TextView tvPower;
    private TextView tvTemp;
    private TextView tvAcc;
    private View inputView;
    private View outputView;
    private Spinner spInput;
    private TextView tvInput;
    private Button btnInputGet;
    private Spinner spOutput;
    private TextView tvOutput;
    private Button btnOutputGet;
    private Button btnOutputSet0;
    private Button btnOutputSet1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(layout.fragment_usual, container, false);

        tvVersion = root.findViewById(id.version);
        tvPower = root.findViewById(id.power);
        tvTemp = root.findViewById(id.temp);
        tvAcc = root.findViewById(id.acc);
        inputView = root.findViewById(id.layout_input);
        outputView = root.findViewById(id.layout_output);
        spInput = root.findViewById(id.input);
        tvInput = root.findViewById(id.input_value);
        btnInputGet = root.findViewById(id.input_get);
        spOutput = root.findViewById(id.output);
        tvOutput = root.findViewById(id.output_value);
        btnOutputGet = root.findViewById(id.output_get);
        btnOutputSet0 = root.findViewById(id.output_set_0);
        btnOutputSet1 = root.findViewById(id.output_set_1);

        btnInputGet.setOnClickListener(this);
        btnOutputGet.setOnClickListener(this);
        btnOutputSet0.setOnClickListener(this);
        btnOutputSet1.setOnClickListener(this);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHandler = new VanHandler(this);

        String version = VanMcu.getVersion();
        tvVersion.setText(version);

        initInput();
        initOutput();
    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.e(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();

        mHandler.sendEmptyMessage(VanHandler.REFRESH);
    }

    @Override
    public void onPause() {
        mHandler.removeMessages(VanHandler.REFRESH);

        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Log.e(TAG, "onStop");
    }

    private void initInput() {
        int count = VanMcu.getInputCount();
        if (count <= 0) {
            inputView.setVisibility(View.GONE);
            return;
        }

        String[] items = new String[count];
        for (int i=0; i<count; ++i) {
            items[i] = String.format("Input %d", i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(layout.support_simple_spinner_dropdown_item);
        spInput.setAdapter(adapter);
        spInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvInput.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initOutput() {
        int count = VanMcu.getOutputCount();
        if (count <= 0) {
            outputView.setVisibility(View.GONE);
            return;
        }

        String[] items = new String[count];
        for (int i=0; i<count; ++i) {
            items[i] = String.format("Output %d", i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(layout.support_simple_spinner_dropdown_item);
        spOutput.setAdapter(adapter);
        spOutput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvOutput.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.input_get:
                getInput();
                break;
            case id.output_get:
                getOutput();
                break;
            case id.output_set_0:
                setOutput(0);
                break;
            case id.output_set_1:
                setOutput(1);
                break;
        }
    }

    private void getInput() {
        if (0 == spInput.getCount()) {
            return;
        }

        int value = VanMcu.InputGet(spInput.getSelectedItemPosition());
        tvInput.setText(String.valueOf(value));
    }

    private void getOutput() {
        if (0 == spOutput.getCount()) {
            return;
        }

        int value = VanMcu.OutputGet(spOutput.getSelectedItemPosition());
        tvOutput.setText(String.valueOf(value));
    }

    private void setOutput(int value) {
        if (0 == spOutput.getCount()) {
            return;
        }

        boolean ret = VanMcu.OutputSet(spOutput.getSelectedItemPosition(), value);
        Toast.makeText(getActivity(), String.format("设置%s", ret ? "成功" : "失败"), Toast.LENGTH_SHORT);
    }

    private void refresh() {
        int voltage = VanMcu.getPowerVoltage();
        tvPower.setText(String.format("%.1f", voltage/1000.0));

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
        }

        int temp = VanMcu.getTemperature();
        tvTemp.setText(String.format("%.1f", temp/100.0));

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
        }

        int state = VanMcu.getAccState();
        tvAcc.setText(state!=0 ? "ON" : "OFF");

        mHandler.sendEmptyMessageDelayed(VanHandler.REFRESH, 1000);
    }

    class VanHandler extends Handler {
        public static final int REFRESH  = 0;

        private WeakReference<UsualFragment> mOut;

        public VanHandler(UsualFragment outer) {
            mOut = new WeakReference<UsualFragment>(outer);
        }

        @Override
        public void handleMessage(Message msg) {
            UsualFragment out = mOut.get();
            if (null == out) {
                return;
            }

            switch (msg.what) {
                case REFRESH:
                    refresh();
                    break;
                default:
                    break;
            }
        }
    }
}
