package com.hothouse.encripttool.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hothouse.encripttool.R;
import com.hothouse.encripttool.utils.AESFinal;
import com.hothouse.encripttool.utils.RSA;

/**
 * Created by Administrator on 2017/12/13.
 */

public class RSAFragment extends Fragment implements View.OnClickListener{
    private final String TAG = RSAFragment.class.getName();

    private Button createBtn;
    private EditText pubKeyEt;
    private EditText priKeyEt;
    private EditText contentEt;
    private TextView encriptedTv;
    private TextView decriptedTv;

    public static RSAFragment newInstance() {
        Bundle args = new Bundle();
        RSAFragment fragment = new RSAFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rsa,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createBtn = view.findViewById(R.id.create_btn);
        Button encriptBtn = view.findViewById(R.id.encript_btn);
        createBtn.setOnClickListener(this);
        encriptBtn.setOnClickListener(this);

        pubKeyEt = view.findViewById(R.id.pub_key_et);
        priKeyEt = view.findViewById(R.id.pri_key_et);
        contentEt = view.findViewById(R.id.content_et);
        encriptedTv = view.findViewById(R.id.encripted_tv);
        decriptedTv = view.findViewById(R.id.decripted_tv);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_btn:
                try {
                    RSA.RSAKeyPair rsaKeyPair = RSA.generateKeyPair();
                    pubKeyEt.setText(rsaKeyPair.publicKey);
                    priKeyEt.setText(rsaKeyPair.privateKey);
                    Log.i(TAG,"PublicKey:"+rsaKeyPair.publicKey);
                    Log.i(TAG,"PrivateKey:"+rsaKeyPair.privateKey);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.encript_btn:
                String pubKeyStr = pubKeyEt.getText().toString();
                String priKeyStr = priKeyEt.getText().toString();
                String content = contentEt.getText().toString();
                try {
                   String encriptedStr =  RSA.encrypt(content,pubKeyStr);
                   encriptedTv.setText(encriptedStr);
                   String decriptedStr = RSA.decrypt(encriptedStr,priKeyStr);
                   decriptedTv.setText(decriptedStr);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                }

                break;
        }
    }
}
