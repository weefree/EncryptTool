package com.hothouse.encripttool.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hothouse.encripttool.R;
import com.hothouse.encripttool.utils.AESFinal;

/**
 * Created by Administrator on 2017/12/13.
 */

public class AESFragment extends Fragment implements View.OnClickListener{

    private Button modeBtn;
    private EditText keyEt;
    private EditText ivEt;
    private EditText contentEt;
    private TextView encriptedTv;
    private TextView decriptedTv;

    public static AESFragment newInstance() {
        Bundle args = new Bundle();
        AESFragment fragment = new AESFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_aes,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        modeBtn = view.findViewById(R.id.mode_btn);
        Button encriptBtn = view.findViewById(R.id.encript_btn);
        modeBtn.setOnClickListener(this);
        encriptBtn.setOnClickListener(this);

        keyEt = view.findViewById(R.id.key_et);
        ivEt = view.findViewById(R.id.iv_et);
        contentEt = view.findViewById(R.id.content_et);
        encriptedTv = view.findViewById(R.id.encripted_tv);
        decriptedTv = view.findViewById(R.id.decripted_tv);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mode_btn:
                final String[] modeArr = new String[]{"AES/ECB/PKCS5Padding","AES/ECB/PKCS7Padding","AES/CBC/PKCS5Padding","AES/CBC/PKCS7Padding"};
                new AlertDialog.Builder(getActivity()).setItems(modeArr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        modeBtn.setText(modeArr[i]);
                    }
                }).create().show();
                break;
            case R.id.encript_btn:
                String keyStr = keyEt.getText().toString();
                String ivStr = ivEt.getText().toString();
                String content = contentEt.getText().toString();
                String aesMode = modeBtn.getText().toString();
                try {
                   String encriptedStr =  AESFinal.encript(aesMode,ivStr,content,keyStr);
                   encriptedTv.setText(encriptedStr);
                   String decriptedStr = AESFinal.decriptBase64Data(aesMode,ivStr,encriptedStr,keyStr);
                   decriptedTv.setText(decriptedStr);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                }

                break;
        }
    }
}
