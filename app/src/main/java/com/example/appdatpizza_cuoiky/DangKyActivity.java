package com.example.appdatpizza_cuoiky;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DangKyActivity extends AppCompatActivity {
// khai báo biến giao diện
    EditText editsdtdky, editmatkhaudky, editnhaplaimk;
    Button btndangky;
    TextView txtdangnhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);
        // anh xạ biến giao diên
        editsdtdky = (EditText)findViewById(R.id.editsdtdky);
        editmatkhaudky = (EditText) findViewById(R.id.editmatkhaudky);
        editnhaplaimk = (EditText) findViewById(R.id.editnhaplaimk);
        btndangky =( Button) findViewById(R.id.btndangky);
        txtdangnhap = (TextView) findViewById(R.id.txtdangnhap);

        // xử lý thao tác người dùng
    btndangky.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String sdt = editsdtdky.getText().toString().trim();
            String matkhau = editmatkhaudky.getText().toString().trim();
            String nhapLaiMatKhau = editnhaplaimk.getText().toString().trim();

            // Kiểm tra số điện thoại có đúng 10 ký tự không
            if (sdt.length() != 10) {
                editsdtdky.requestFocus();
                editsdtdky.selectAll();
                Toast.makeText(DangKyActivity.this, "Số điện thoại phải đúng 10 ký tự!", Toast.LENGTH_LONG).show();
                return;
            }

            // Kiểm tra mật khẩu không được bỏ trống
            if (matkhau.isEmpty()) {
                editmatkhaudky.requestFocus();
                Toast.makeText(DangKyActivity.this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_LONG).show();
                return;
            }
            // Kiểm tra mật khẩu nhập lại có khớp với mật khẩu không
            if (!matkhau.equals(nhapLaiMatKhau)) {
                editnhaplaimk.requestFocus();
                editnhaplaimk.selectAll();
                Toast.makeText(DangKyActivity.this, "Mật khẩu nhập lại không khớp!", Toast.LENGTH_LONG).show();
                return;
            }
            // Lưu thông tin người dùng vào SQLite
            DatabaseHelper dbHelper = new DatabaseHelper(DangKyActivity.this);
            dbHelper.addUser(sdt, matkhau);
            // Nếu tất cả hợp lệ, tiếp tục đăng nhập (thêm logic đăng nhập tại đây)
            Toast.makeText(DangKyActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(DangKyActivity.this);
            builder.setMessage("Bạn muốn đăng nhập không?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent myIntent = new Intent(DangKyActivity.this, MainActivity.class);
                            startActivity(myIntent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }

    });
    txtdangnhap.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}