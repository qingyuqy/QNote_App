package com.qingyu.qnote;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.qingyu.qnote.vo.NoteVO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {
    private static final String DEFAULT_DIR = Environment.getExternalStorageDirectory() + File.separator + "QNote/img";
    EditText etNote_title;
    EditText etNote_content;
    TextView tv_sign;
    private static SharedPreferences sp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initView();
    }

    public void initView(){
        sp1 = getSharedPreferences("signature",MODE_PRIVATE);
        NoteVO note = (NoteVO) getIntent().getSerializableExtra("Note");
        etNote_title = (EditText) findViewById(R.id.edText_title);
        etNote_content = (EditText) findViewById(R.id.edText_content);
        tv_sign = (TextView)findViewById(R.id.txView_sign);
        if(note==null){
            Toast.makeText(this, "Null", Toast.LENGTH_LONG).show();
        }else{
            etNote_title.setText(note.getTitle());
            etNote_content.setText(note.getContent());
            tv_sign.setText("By " + sp1.getString("signature","QingYu"));
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNoteasPic();
            }
        });
    }
    public void saveNoteasPic(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_note);
        etNote_title.setFocusable(false);
        etNote_content.setFocusable(false);
        int width = layout.getWidth();
        int height = 0;
        for (int i = 0; i < layout.getChildCount(); i++) {
            height += layout.getChildAt(i).getHeight();
        }
        System.out.println("width"+width+"");
        System.out.println("height"+height+"");
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        layout.draw(canvas);

        String filepath = DEFAULT_DIR + File.separator + new Date().getTime() + ".jpg";


        try {
            File outFile = new File(filepath);
            if(!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdirs();
            }
            FileOutputStream stream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            //refresh the pictures in devices
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(new File(filepath));
            intent.setData(uri);
            this.getApplicationContext().sendBroadcast(intent);
            Toast.makeText(this, "成功保存到:" + filepath, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
