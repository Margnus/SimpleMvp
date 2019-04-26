package com.lxf.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hannesdorfmann.mosby3.base.BaseActivity;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author: 李小凡
 * created on: 2019/4/26 9:41
 * description:
 */
@Route(path = "/socket/test")
public class SocketTestActivity extends BaseActivity {

    @BindView(R.id.ip_et)
    EditText ipEt;
    @BindView(R.id.port_et)
    EditText portEt;
    @BindView(R.id.content_et)
    EditText contentEt;
    @BindView(R.id.sent_btn)
    Button sentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_test_activity);
        ButterKnife.bind(this);
        contentEt.setText("<Msg_Req><Action>1</Action></Msg_Req>");
    }

    @OnClick({R.id.sent_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sent_btn:
                final String ip = ipEt.getText().toString();
                final int port = Integer.valueOf(portEt.getText().toString());
                final String msg = contentEt.getText().toString();
                Single.create(new SingleOnSubscribe<String>() {

                    @Override
                    public void subscribe(SingleEmitter<String> singleEmitter){
                        singleEmitter.onSuccess(connectServerWithTCPSocket(ip, port, msg));
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()) // 由于interval默认在新线程，所以我们应该切回主线程
                        .subscribe(new SingleObserver<String>() {
                            @Override
                            public void onSubscribe(Disposable disposable) {

                            }

                            @Override
                            public void onSuccess(String result) {
                                Toast.makeText(SocketTestActivity.this, result, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                Toast.makeText(SocketTestActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }

    }

    protected String connectServerWithTCPSocket(String ip, int port, String msg) {
        Socket socket = null;
        InputStream inputStream = null;
        StringBuilder sb = new StringBuilder();
        try {
            socket = new Socket(ip, port);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream()));
            writer.write(msg.replace("\n", " ") + "\n");
            writer.flush();


            inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            len = inputStream.read(bytes);
            sb.append(new String(bytes, 0, len, "UTF-8"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
