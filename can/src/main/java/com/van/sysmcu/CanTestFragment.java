package com.van.sysmcu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.van.jni.VanMcu;
import com.van.jni.VanMcu.OnCanListener;

import org.greenrobot.eventbus.EventBus;

public class CanTestFragment extends Fragment implements OnCanListener, View.OnClickListener {
    private int mChannel;
    private volatile long mReceivedCount;
    private volatile long mSentCount;
    private VanHandler mHandler;
    private Spinner spBaudRate;
    private EditText etCount;
    private TextView tvReceived;
    private TextView tvSent;
    private Button btnSetBaudRate;
    private Button btnSend;
    private Button btnClearReceived;
    private Button btnClearSent;
    private volatile boolean sending;
    private SendThread mThread;

    public CanTestFragment() {
        mChannel = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_can_test, container, false);
        TextView title = root.findViewById(R.id.title);
        title.setText(String.format("CAN %d", mChannel+1));

        mHandler = new VanHandler();

        spBaudRate = root.findViewById(R.id.baud_rate);
        etCount = root.findViewById(R.id.send_count);
        tvReceived = root.findViewById(R.id.received_count);
        tvSent = root.findViewById(R.id.sent_count);

        btnSetBaudRate = root.findViewById(R.id.set_baud_rate);
        btnSend = root.findViewById(R.id.send);
        btnClearReceived = root.findViewById(R.id.received_clear);
        btnClearSent = root.findViewById(R.id.sent_clear);
        btnSetBaudRate.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        btnClearReceived.setOnClickListener(this);
        btnClearSent.setOnClickListener(this);

        spBaudRate.setSelection(1);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();

        VanMcu.setOnCanListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_baud_rate:
                setBaudRate();
                break;
            case R.id.send:
                send();
                break;
            case R.id.received_clear:
                clearReceived();
                break;
            case R.id.sent_clear:
                clearSent();
                break;
        }
    }

    private void setBaudRate() {
        int index = spBaudRate.getSelectedItemPosition();
        int baudRate = ((int)Math.pow(2, index))*125000;

        boolean ret = VanMcu.setCanSpeed(mChannel, baudRate);

        Log.e("CAN", "setBaudRate: " + mChannel + " - " + baudRate);

        Toast.makeText(getActivity(), String.format("Set BaudRate %s", ret ? "Success" : "Failed"),
                Toast.LENGTH_SHORT).show();
    }

    private void send() {
        if (sending) {
            sending = false;
            if (null != mThread) {
                try {
                    mThread.join();
                } catch (Exception e) {
                }
            }

            btnSend.setText("Send");
            etCount.setEnabled(true);
        } else {
            String strCount = etCount.getText().toString();
            if (TextUtils.isEmpty(strCount)) {
                Toast.makeText(getActivity(), "发送条数不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            long count = Long.parseLong(strCount);
            if (count <= 0) {
                Toast.makeText(getActivity(), "发送条数不合法", Toast.LENGTH_SHORT).show();
                return;
            }

            btnSend.setText("Cancel");
            etCount.setEnabled(false);
            sending = true;
            mThread = new SendThread(count);
            mThread.start();
        }
    }

    private void clearReceived() {
        mReceivedCount = 0;
        tvReceived.setText(String.valueOf(mReceivedCount));
    }

    private void clearSent() {
        mSentCount = 0;
        tvSent.setText(String.valueOf(mSentCount));
    }

    private void refresh() {
        tvReceived.setText(String.valueOf(mReceivedCount));
        tvSent.setText(String.valueOf(mSentCount));

        mHandler.sendEmptyMessageDelayed(VanHandler.REFRESH, 500);
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

    @Override
    public void OnCan(VanMcu.CanMsg msg) {
        boolean isExtFrame = true;
        int id = msg.id;
        byte[] data = msg.data;

        if ((id & VanMcu.CAN_EFF_FLAG) != 0) {
            id &= 0x1FFFFFFF;
        } else {
            id &= 0x7FF;
            isExtFrame = false;
        }

        String frameId, frameType;

        if (isExtFrame) {
            frameId = String.format("%08X", id);
            frameType = "Ext";
        } else {
            frameId = String.format("%04X", id);
            frameType = "Std";
        }

        int dlc = 0;
        String strData = "";
        if (null != data) {
            dlc = data.length;
            strData = getHexString(data);
        }

        System.out.println(String.format("Channel=%d, ID=%s, Type=%s, DLC=%d, Data=%s", mChannel,
                frameId, frameType, dlc, strData));

        ++mReceivedCount;
    }

    class VanHandler extends Handler {
        public static final int REFRESH = 1;
        public static final int SEND_FINISH = 2;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH:
                    refresh();
                    break;
                case SEND_FINISH:
                    btnSend.setText("Send");
                    etCount.setEnabled(true);
                    break;
            }
        }
    }

    class SendThread extends Thread {
        private long mCount;

        SendThread(long count) {
            mCount = count;
        }

        @Override
        public void run() {
            int id = 0;
            long data = 0;
            byte[] bytes = new byte[8];

            for (int i=0; (i<mCount) && sending; ++i) {
                id |= VanMcu.CAN_EFF_FLAG;

                for (int j=0; j<8; ++j) {
                    bytes[j] = (byte)((data>>(j*8)) & 0xFF);
                }

                VanMcu.CanWrite(mChannel, id, bytes);
                ++id;
                ++data;

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
            }

            sending = false;
            mThread = null;
            mHandler.sendEmptyMessage(VanHandler.SEND_FINISH);
        }
    }
}
