package com.van.sysmcu;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.van.jni.VanMcu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CanFragment extends Fragment implements View.OnClickListener, VanMcu.OnCanListener {
    private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS", Locale.CHINA);
    private Spinner spChannel;
    private Spinner spFrameType;
    private Spinner spBaudRate;
    private Spinner spFilterType;
    private EditText etId;
    private EditText etData;
    private Button btnSend;
    private Button btnSetBaudRate;
    private Button btnSetFilterType;
    private Spinner spFilterFormat;
    private EditText etRefId;
    private EditText etMask;
    private Button btnAddHwFilter;
    private Button btnClearHwFilter;
    private Button btnAddSwFilter;
    private Button btnClearSwFilter;
    private RecyclerView mListView;
    private List<VanMcu.CanMsg> mCanMsgList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_can, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCanMsgList = new ArrayList<>();
        initChannel();

        etId.setFilters(new InputFilter[]{mInputFilter});
        etData.setFilters(new InputFilter[]{mInputFilter});
        etRefId.setFilters(new InputFilter[]{mInputFilter});
        etMask.setFilters(new InputFilter[]{mInputFilter});

        spFrameType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (0 == position) {
                    etId.setText("1FFFFFFF");
                } else {
                    etId.setText("7FF");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mListView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListView.setAdapter(new CanMsgListAdapter());
    }

    private void initView(View root) {
        spChannel = root.findViewById(R.id.channel);
        spFrameType = root.findViewById(R.id.frame_type);
        spBaudRate = root.findViewById(R.id.baud_rate);
        spFilterType = root.findViewById(R.id.filter_type);

        etId = root.findViewById(R.id.id);
        etData = root.findViewById(R.id.data);
        btnSend = root.findViewById(R.id.send);
        btnSetBaudRate = root.findViewById(R.id.set_baud_rate);
        btnSetFilterType = root.findViewById(R.id.set_filter_type);
        spFilterFormat = root.findViewById(R.id.filter_format);
        etRefId = root.findViewById(R.id.ref_id);
        etMask = root.findViewById(R.id.mask);
        btnAddHwFilter = root.findViewById(R.id.add_hw_filter);
        btnClearHwFilter = root.findViewById(R.id.clear_hw_filter);
        btnAddSwFilter = root.findViewById(R.id.add_sw_filter);
        btnClearSwFilter = root.findViewById(R.id.clear_sw_filter);
        mListView = root.findViewById(R.id.listView);

        btnSend.setOnClickListener(this);
        btnSetBaudRate.setOnClickListener(this);
        btnSetFilterType.setOnClickListener(this);
        btnAddHwFilter.setOnClickListener(this);
        btnClearHwFilter.setOnClickListener(this);
        btnAddSwFilter.setOnClickListener(this);
        btnClearSwFilter.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);
        VanMcu.setOnCanListener(this);
    }

    @Override
    public void onPause() {
        VanMcu.setOnCanListener(null);
        EventBus.getDefault().unregister(this);

        super.onPause();
    }

    private void initChannel() {
        int count = VanMcu.getCanCount();
        if (count <= 0) {
            return;
        }

        String[] items = new String[count];
        for (int i=0; i<count; ++i) {
            items[i] = String.format("通道 %d", i+1);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spChannel.setAdapter(adapter);

        int channel = spChannel.getSelectedItemPosition();
        int baudRate = VanMcu.getCanSpeed(channel);
        int index = baudRate/125000;
        spBaudRate.setSelection((int)(Math.log(index)/Math.log(2)));
    }

    @Override
    public void onClick(View v) {
        int count = spChannel.getCount();
        if (0 == count) {
            return;
        }

        switch (v.getId()) {
            case R.id.send:
                send();
                break;
            case R.id.set_baud_rate:
                setBaudRate();
                break;
            case R.id.set_filter_type:
                setFilterType();
                break;
            case R.id.add_hw_filter:
                addHwFilter();
                break;
            case R.id.clear_hw_filter:
                clearHwFilter();
                break;
            case R.id.add_sw_filter:
                addSwFilter();
                break;
            case R.id.clear_sw_filter:
                clearSwFilter();
                break;
        }
    }

    private void send() {
        String strId = etId.getText().toString();
        if (TextUtils.isEmpty(strId)) {
            Toast.makeText(getActivity(), "The ID cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        int channel = spChannel.getSelectedItemPosition();
        int id = Integer.parseInt(strId, 16);
        boolean isExtFrame = (0 == spFrameType.getSelectedItemPosition());
        if (isExtFrame) {
            id |= VanMcu.CAN_EFF_FLAG;
        }

        byte[] bytes = null;
        String strData = etData.getText().toString();
        if (!TextUtils.isEmpty(strData)) {
            bytes = getBytes(strData);
        }

        Log.e("CAN", "send: " + channel + " - " + id);

        VanMcu.CanWrite(channel, id, bytes);
    }

    private void setBaudRate() {
        int channel = spChannel.getSelectedItemPosition();
        int index = spBaudRate.getSelectedItemPosition();
        int baudRate = ((int)Math.pow(2, index))*125000;

        boolean ret = VanMcu.setCanSpeed(channel, baudRate);

        Log.e("CAN", "setBaudRate: " + channel + " - " + baudRate);

        Toast.makeText(getActivity(), String.format("Set BaudRate %s", ret ? "Success" : "Failed"),
                Toast.LENGTH_SHORT).show();
    }

    private void setFilterType() {
        int channel = spChannel.getSelectedItemPosition();
        int index = spFilterType.getSelectedItemPosition();

        boolean ret = VanMcu.CanFilterCtrl(channel, index);

        Toast.makeText(getActivity(), String.format("Set FilterType %s", ret ? "Success" : "Failed"),
                Toast.LENGTH_SHORT).show();
    }

    private void addHwFilter() {
        String strRefId = etRefId.getText().toString();
        String strMask = etMask.getText().toString();
        if (TextUtils.isEmpty(strRefId) || TextUtils.isEmpty(strMask)) {
            return;
        }

        int channel = spChannel.getSelectedItemPosition();
        boolean isExtFrame = (0 == spFilterFormat.getSelectedItemPosition());
        int refId = Integer.parseInt(strRefId, 16);
        int mask = Integer.parseInt(strMask, 16);

        if (isExtFrame) {
            refId |= VanMcu.CAN_EFF_FLAG;
            mask  |= VanMcu.CAN_EFF_FLAG;
        }

        boolean ret = VanMcu.CanHwFilterAdd(channel, refId, mask);
        Toast.makeText(getActivity(), String.format("Add HwFilter %s", ret ? "Success" : "Failed"),
                Toast.LENGTH_SHORT).show();
    }

    private void clearHwFilter() {
        int channel = spChannel.getSelectedItemPosition();

        boolean ret = VanMcu.CanHwFilterClear(channel);
        Toast.makeText(getActivity(), String.format("Clear HwFilter %s", ret ? "Success" : "Failed"),
                Toast.LENGTH_SHORT).show();
    }

    private void addSwFilter() {
        String strRefId = etRefId.getText().toString();
        String strMask = etMask.getText().toString();
        if (TextUtils.isEmpty(strRefId) || TextUtils.isEmpty(strMask)) {
            return;
        }

        int channel = spChannel.getSelectedItemPosition();
        boolean isExtFrame = (0 == spFilterFormat.getSelectedItemPosition());
        int refId = Integer.parseInt(strRefId, 16);
        int mask = Integer.parseInt(strMask, 16);

        if (isExtFrame) {
            refId |= VanMcu.CAN_EFF_FLAG;
            mask  |= VanMcu.CAN_EFF_FLAG;
        }

        boolean ret = VanMcu.CanSwFilterAdd(channel, refId, mask);
        Toast.makeText(getActivity(), String.format("Add SwFilter %s", ret ? "Success" : "Failed"),
                Toast.LENGTH_SHORT).show();
    }

    private void clearSwFilter() {
        int channel = spChannel.getSelectedItemPosition();

        boolean ret = VanMcu.CanSwFilterClear(channel);
        Toast.makeText(getActivity(), String.format("Clear SwFilter %s", ret ? "Success" : "Failed"),
                Toast.LENGTH_SHORT).show();
    }

    private InputFilter mInputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (TextUtils.isEmpty(source)) {
                return null;
            }

            String digits = "[0-9a-fA-F ]+";
            return source.toString().matches(digits) ? null : "";
        }
    };

    private byte[] getBytes(String strData) {
        byte[] bytes = null;

        strData = strData.replace(" ", "");

        int length = strData.length();
        if (0 != length%2) {
            Toast.makeText(getActivity(), "数据格式错误", Toast.LENGTH_SHORT).show();
            return null;
        }

        bytes = new byte[length/2];

        for (int i = 0; i < length/2; i++) {
            String value = strData.substring(i*2, i*2+2);
            bytes[i] = (byte)Integer.parseInt(value, 16);
        }

        return bytes;
    }

    @Override
    public void OnCan(VanMcu.CanMsg msg) {
        /*boolean isExtFrame = true;
        int channel = msg.channel;
        int id = msg.id;
        byte[] data = msg.data;

        if ((msg.id&VanMcu.CAN_EFF_FLAG) != 0) {
            id &= 0x1FFFFFFF;
        } else {
            id &= 0x7FF;
            isExtFrame = false;
        }

        String str = String.format("channel=%d, Type=%s, ID=%08X, Data=%s", channel, isExtFrame ? "Ext" : "Std", id, getHexString(data));
        System.out.println(str);*/

        EventBus.getDefault().post(msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCanMsg(VanMcu.CanMsg msg) {
        mCanMsgList.add(msg);
        mListView.getAdapter().notifyDataSetChanged();
    }

    private String getHexString(byte[] data) {
        if (null == data) {
            return "";
        }

        StringBuilder sb = new StringBuilder(data.length*3);

        final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7',
                    '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        for (int i = 0; i < data.length; i++) {
            int value = data[i] & 0xff;
            sb.append(HEX[value/16]).append(HEX[value%16]).append(' ');
        }

        return sb.toString();
    }

    public class CanMsgListAdapter extends RecyclerView.Adapter<CanMsgListAdapter.ViewHolder> {
        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvNo;
            private TextView tvTime;
            private TextView tvChannel;
            private TextView tvId;
            private TextView tvType;
            private TextView tvDlc;
            private TextView tvData;

            public ViewHolder(View itemView) {
                super(itemView);

                tvNo = itemView.findViewById(R.id.no);
                tvTime = itemView.findViewById(R.id.time);
                tvChannel = itemView.findViewById(R.id.channel);
                tvId = itemView.findViewById(R.id.id);
                tvType = itemView.findViewById(R.id.type);
                tvDlc = itemView.findViewById(R.id.dlc);
                tvData = itemView.findViewById(R.id.data);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.can_msg,
                    viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, int i) {
            VanMcu.CanMsg item = mCanMsgList.get(i);

            boolean isExtFrame = true;
            int id = item.id;
            byte[] data = item.data;

            if ((id & VanMcu.CAN_EFF_FLAG) != 0) {
                id &= 0x1FFFFFFF;
            } else {
                id &= 0x7FF;
                isExtFrame = false;
            }

            viewHolder.tvNo.setText(String.valueOf(i));
            viewHolder.tvTime.setText(formatter.format(new Date()));
            viewHolder.tvChannel.setText(String.valueOf(item.channel+1));

            if (isExtFrame) {
                viewHolder.tvId.setText(String.format("%08X", id));
                viewHolder.tvType.setText("扩展帧");
            } else {
                viewHolder.tvId.setText(String.format("%04X", id));
                viewHolder.tvType.setText("标准帧");
            }

            if (null != data) {
                viewHolder.tvDlc.setText(String.valueOf(data.length));
                viewHolder.tvData.setText(getHexString(data));
            } else {
                viewHolder.tvDlc.setText("0");
                viewHolder.tvData.setText("");
            }
        }

        @Override
        public int getItemCount() {
            return mCanMsgList.size();
        }
    }
}
