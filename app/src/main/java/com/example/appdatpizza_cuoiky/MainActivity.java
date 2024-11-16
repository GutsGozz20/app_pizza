package com.example.appdatpizza_cuoiky;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // khởi tao biến giao diện
    EditText editsdt, editmatkhau;
    TextView txtquenmk, txttaotk;
    Button btndangnhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // ánh xạ biến giao diện
        editsdt = (EditText) findViewById(R.id.editsdt);
        editmatkhau = (EditText) findViewById(R.id.editmatkhau);
        txttaotk = (TextView) findViewById(R.id.txttaotk);
        txtquenmk = (TextView) findViewById(R.id.txtquenmk);
        btndangnhap = (Button) findViewById(R.id.btndangnhap);

        // xử lý thao tác người dùng
        // Xử lý thao tác người dùng khi nhấn vào nút đăng nhập
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt = editsdt.getText().toString().trim();
                String matkhau = editmatkhau.getText().toString().trim();

                DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);

                // Kiểm tra số điện thoại có tồn tại trong cơ sở dữ liệu không
                boolean isUserExist = dbHelper.isUserExist(sdt);

                if (!isUserExist) {
                    // Nếu số điện thoại chưa được đăng ký
                    Toast.makeText(MainActivity.this, "Số điện thoại tài khoản chưa được đăng ký", Toast.LENGTH_LONG).show();
                } else {
                    // Nếu số điện thoại đã tồn tại, kiểm tra mật khẩu
                    boolean isValidUser = dbHelper.checkUser(sdt, matkhau);

                    if (isValidUser) {
                        // Đăng nhập thành công
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        // Chuyển sang màn hình HomeActivity
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish(); // Dừng MainActivity để không quay lại khi nhấn nút "Back"
                    } else {
                        // Nếu sai mật khẩu
                        Toast.makeText(MainActivity.this, "Sai mật khẩu", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        // mỞ Activity Đăng ký
        txttaotk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, DangKyActivity.class);
                startActivity(myIntent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}