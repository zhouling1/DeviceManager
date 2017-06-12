package com.ppdl.devicemanager;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
  private Button bt_start,bt_lock,bt_wipedata,bt_uninstall;
    private ComponentName deviceCom;
    private DevicePolicyManager mDpm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        deviceCom=new ComponentName(this,AdminReceiver.class);
        mDpm= (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

    }

    private void initView() {

        Button bt_start = (Button) findViewById(R.id.bt_start);
        Button bt_lock = (Button) findViewById(R.id.bt_lock);
        Button bt_wipedata = (Button) findViewById(R.id.bt_wipedata);
        Button bt_uninstall = (Button) findViewById(R.id.bt_uninstall);

        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceCom);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"设备管理器");
                startActivity(intent);
            }
        });
        bt_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否激活
                 if(mDpm.isAdminActive(deviceCom)){
                     mDpm.lockNow();
                     mDpm.resetPassword("123",0);
                 }else{
                     Toast.makeText(MainActivity.this,"",Toast.LENGTH_SHORT).show();
                 }


            }
        });
        bt_uninstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("android.intent.action.DELETE");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse("package:"+getPackageName()));
                startActivity(intent);
            }
        });
        bt_wipedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDpm.isAdminActive(deviceCom)){
                    mDpm.wipeData(0);
                }else{
                    Toast.makeText(MainActivity.this,"",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
