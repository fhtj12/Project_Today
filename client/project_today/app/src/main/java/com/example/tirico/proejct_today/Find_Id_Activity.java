package com.example.tirico.proejct_today;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Find_Id_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        final TextView tv_email = (TextView) findViewById(R.id.find_id_email);
        final TextView tv_birth = (TextView) findViewById(R.id.find_id_birth);

        Button btn_find_id = (Button) findViewById(R.id.btnFindId);
        Button btn_update_pwd = (Button) findViewById(R.id.btnUpdatePwd);
        Button btn_cancel = (Button) findViewById(R.id.btnCancel);

        btn_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tv_email.getText().toString();
                String birth = tv_birth.getText().toString();
            }
        });

        btn_update_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Update_Pwd_Activity.class);
                startActivity(intent);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
