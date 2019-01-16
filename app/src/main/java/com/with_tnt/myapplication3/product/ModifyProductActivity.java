package com.with_tnt.myapplication3.product;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.with_tnt.db_connection.product.DB_ModifyProduct;
import com.with_tnt.db_connection.product.DB_ModifySet;
import com.with_tnt.myapplication3.R;

public class ModifyProductActivity extends AppCompatActivity {

    Spinner mcategorySpiner;
    Button mimageBtn;
    public TextView mimageTv;
    EditText mproductName;
    EditText mlistPrice;
    EditText msellingPrice;
    Spinner mstateSpiner;
    EditText mcontentEt;
    Button mregistrationBtn;
    Button mcancelBtn;
    EditText mhashTag;
    int mselectedIndexCategory;
    int mselectedIndexState;
    String msendBack;
    Context mupCon;
    int temper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_product);

        Intent get_meth = getIntent();
        temper = get_meth.getExtras().getInt("pnum");
        mupCon = this;
        new DB_ModifySet(mupCon, temper).execute();

        mcategorySpiner = (Spinner) findViewById(R.id.mspinner_category);
        mimageBtn = (Button) findViewById(R.id.mbutton_fileSelect);
        mimageTv = (TextView ) findViewById(R.id.mtextView_fileSelect);
        mproductName = (EditText) findViewById(R.id.meditText_product);
        mlistPrice = (EditText) findViewById(R.id.meditText_listPrice);
        msellingPrice = (EditText) findViewById(R.id.meditText_sellingPrice);
        mstateSpiner = (Spinner) findViewById(R.id.mspinner_state);
        mcontentEt = (EditText) findViewById(R.id.meditText_content);
        mregistrationBtn = (Button) findViewById(R.id.mbutton_registration);
        mcancelBtn = (Button) findViewById(R.id.mbutton_cancel);
        mhashTag = (EditText) findViewById(R.id.meditText_HashTag);


        mcategorySpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mselectedIndexCategory = adapterView.getSelectedItemPosition();
                Toast.makeText(ModifyProductActivity.this, adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        mstateSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mselectedIndexState = adapterView.getSelectedItemPosition();
                Toast.makeText(ModifyProductActivity.this, adapterView.getSelectedItem().toString(), 1000).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });


        mimageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] arrayOfString = new String[2];
                arrayOfString[0] = "사진 촬영";
                arrayOfString[1] = "갤러리에서 찾기";
                new AlertDialog.Builder(ModifyProductActivity.this).setTitle("업로드 방법 선택").setItems(arrayOfString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                        if (paramAnonymousInt == 0) {
                            Intent localIntent1 = new Intent("android.media.action.IMAGE_CAPTURE");
                            if (localIntent1.resolveActivity(ModifyProductActivity.this.getPackageManager()) != null) {
                                ModifyProductActivity.this.startActivityForResult(localIntent1, 1);
                            }
                        } else {
                            Intent localIntent2 = new Intent("android.intent.action.PICK");
                            localIntent2.setType("vnd.android.cursor.dir/image");
                            localIntent2.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            ModifyProductActivity.this.startActivityForResult(localIntent2, 0);
                        }
                    }
                }).show();
            }
        });

        mregistrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // product_Name, product_Information, product_Cost,  product_Cost_Origin,  product_Condition,  ID,  sendBack,  upCon
                if(mselectedIndexCategory==0) {
                    Toast.makeText(ModifyProductActivity.this,  "카테고리", 1000).show();
                }
                else if(mselectedIndexState==0){
                    Toast.makeText(ModifyProductActivity.this,  "상태", 1000).show();
                }
                else{
                    //DB_UploadProduct uploadTask = new DB_UploadProduct(product_Name.getText().toString(), product_Information.getText().toString(), product_Cost.getText().toString(), product_Cost_Origin.getText().toString(), selectedIndex, ID, sendBack, upCon);
                    DB_ModifyProduct uploadTask = new DB_ModifyProduct(mselectedIndexCategory, mproductName.getText().toString(), Integer.parseInt(mlistPrice.getText().toString()), Integer.parseInt(msellingPrice.getText().toString()), mselectedIndexState,
                            mcontentEt.getText().toString(), mupCon, temper, mhashTag.getText().toString());
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
                new Image_Uploader(mupCon, pathOfFile, "20140315").execute(); // 로그인
                // 연동해야됨
            }
        } else {
            String pathOfFile = getPath(data.getData());
            new Image_Uploader(mupCon, pathOfFile, "201403115").execute(); // 로그인
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
        this.msendBack = sendBack;
    }

    public void toasting2(String sendBack) {
        Toast.makeText(ModifyProductActivity.this, sendBack, 1000).show();
    }


    //category, productName, listPrice, sellingPrice, product_state, contents, hashTag

    public  void updateList(int categoty, String productName, int listPrice,int sellingPrice,int product_state, String contents, String hashTag)
    {
        float lp,sp,r;
        int ds;

        Resources res = getResources();
        //카테고리스피너
        this.mproductName.setText(productName);
        this.mlistPrice.setText(String.valueOf(listPrice));
        this.msellingPrice.setText(String.valueOf(sellingPrice));
        //상품상태스피너
        this.mcontentEt.setText(contents);
        this.mhashTag.setText(hashTag);

    }



}
