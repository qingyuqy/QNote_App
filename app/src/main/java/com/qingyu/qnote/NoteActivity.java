package com.qingyu.qnote;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qingyu.qnote.vo.NoteVO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class NoteActivity extends Activity {
    private static final String DEFAULT_DIR = Environment.getExternalStorageDirectory() + File.separator + "QNote";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoteVO note = (NoteVO) getIntent().getSerializableExtra("Note");
        if(note==null){
            Toast.makeText(this, "Null", Toast.LENGTH_LONG).show();
        }
        setContentView(R.layout.activity_note);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNoteasPic();
            }
        });
    }

    public void saveNoteasPic(){
        LinearLayout layout = (LinearLayout)findViewById(R.id.layout_note);
        int width = layout.getWidth(), height = 0;
        for (int i = 0; i < layout.getChildCount(); i++) {
            height += layout.getChildAt(i).getHeight();
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        layout.draw(canvas);

        String filepath = DEFAULT_DIR + File.separator + new Date().getTime() + ".jpg";
        try {
            FileOutputStream stream = new FileOutputStream(filepath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
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
