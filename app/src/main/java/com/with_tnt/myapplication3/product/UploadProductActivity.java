package com.with_tnt.myapplication3.product;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.with_tnt.db_connection.product.DB_UploadProduct;
import com.with_tnt.myapplication3.R;

public class UploadProductActivity extends AppCompatActivity {

    Spinner categorySpiner;
    Button imageBtn;
    public TextView imageTv;
    EditText productName;
    EditText listPrice;
    EditText sellingPrice;
    Spinner stateSpiner;
    EditText contentEt;
    Button registrationBtn;
    Button cancelBtn;
    EditText hashTag;
    int selectedIndexCategory;
    int selectedIndexState;
    String sendBack;
    Context upCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.common_custom_actionbar);

        TextView tt = (TextView) findViewById(R.id.act_text);
        tt.setText("상품 등록");

        categorySpiner = (Spinner) findViewById(R.id.spinner_category);
        imageBtn = (Button) findViewById(R.id.button_fileSelect);
        imageTv = (TextView) findViewById(R.id.textView_fileSelect);
        productName = (EditText) findViewById(R.id.editText_product);
        listPrice = (EditText) findViewById(R.id.editText_listPrice);
        sellingPrice = (EditText) findViewById(R.id.editText_sellingPrice);
        stateSpiner = (Spinner) findViewById(R.id.spinner_state);
        contentEt = (EditText) findViewById(R.id.editText_content);
        registrationBtn = (Button) findViewById(R.id.button_registration);
        cancelBtn = (Button) findViewById(R.id.button_cancel);
        hashTag = (EditText) findViewById(R.id.editText_HashTag);
        upCon = this;

        categorySpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedIndexCategory = adapterView.getSelectedItemPosition();
                Toast.makeText(UploadProductActivity.this, adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        stateSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedIndexState = adapterView.getSelectedItemPosition();
                Toast.makeText(UploadProductActivity.this, adapterView.getSelectedItem().toString(), 1000).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] arrayOfString = new String[2];
                arrayOfString[0] = "사진 촬영";
                arrayOfString[1] = "갤러리에서 찾기";
                new AlertDialog.Builder(UploadProductActivity.this).setTitle("업로드 방법 선택").setItems(arrayOfString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                        if (paramAnonymousInt == 0) {
                            Intent localIntent1 = new Intent("android.media.action.IMAGE_CAPTURE");
                            if (localIntent1.resolveActivity(UploadProductActivity.this.getPackageManager()) != null) {
                                UploadProductActivity.this.startActivityForResult(localIntent1, 1);
                            }
                        } else {
                            Intent localIntent2 = new Intent("android.intent.action.PICK");
                            localIntent2.setType("vnd.android.cursor.dir/image");
                            localIntent2.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            UploadProductActivity.this.startActivityForResult(localIntent2, 0);
                        }
                    }
                }).show();
            }
        });

        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // product_Name, product_Information, product_Cost,
                // product_Cost_Origin, product_Condition, ID, sendBack, upCon
                if (selectedIndexCategory == 0) {
                    Toast.makeText(UploadProductActivity.this, "카테고리", 1000).show();
                } else if (selectedIndexState == 0) {
                    Toast.makeText(UploadProductActivity.this, "상태", 1000).show();
                } else {
                    // DB_UploadProduct uploadTask = new
                    // DB_UploadProduct(product_Name.getText().toString(),
                    // product_Information.getText().toString(),
                    // product_Cost.getText().toString(),
                    // product_Cost_Origin.getText().toString(), selectedIndex,
                    // ID, sendBack, upCon);
                    DB_UploadProduct uploadTask = new DB_UploadProduct(selectedIndexCategory, "이미지", productName.getText().toString(), Integer.parseInt(listPrice.getText().toString()), Integer.parseInt(sellingPrice.getText().toString()), selectedIndexState, hashTag.getText().toString(),
                            contentEt.getText().toString(), upCon, sendBack, getSharedPreferences("UserInfo", 0).getString("UserID", ""));
                    uploadTask.execute();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == 0) {
            if (resultCode == -1) {
                // getSharedPreferences("UserInfo", 0).getString("UserName",
                // "").
                String pathOfFile = getPath(data.getData());
                new Image_Uploader(upCon, pathOfFile, "20140315").execute(); // 로그인
                // 연동해야됨
            }
        } else {
            String pathOfFile = getPath(data.getData());
            new Image_Uploader(upCon, pathOfFile, "201403115").execute(); // 로그인
            // 연동해야됨
        }
    }

    public String getPath(Uri paramUri) {
        String[] arrayOfString = new String[1];
        arrayOfString[0] = "_data";
        Cursor localCursor = managedQuery(paramUri, arrayOfString, null, null, null);
        startManagingCursor(localCursor);
        int i = localCursor.getColumnIndexOrThrow("_data");
        localCursor.moveToFirst();
        return localCursor.getString(i);
    }

    public void toasting(String sendBack) {
        this.sendBack = sendBack;
    }

    public void toasting2(String sendBack) {
        Toast.makeText(UploadProductActivity.this, sendBack, 1000).show();
    }
}
