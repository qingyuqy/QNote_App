package com.qingyu.qnote;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qingyu.qnote.Adapter.NoteAdapter;
import com.qingyu.qnote.vo.NoteVO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ListView note_ListView;
    private FloatingActionButton fab;
    private ArrayList<NoteVO> noteList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        noteList = new ArrayList<>();
        NoteVO note = new NoteVO();
        note.setTitle("意义");
        note.setContent("意义在于人");
        note.setDate(new Date().getTime());
        noteList.add(note);
        note_ListView=(ListView)findViewById(R.id.note_listview);
        note_ListView.setAdapter(new NoteAdapter(this,noteList));
        note_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoteVO note = noteList.get(position);
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Note",note);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,NoteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void draw(String text){
        Bitmap bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
       // paint.setStrokeWidth(2);
        paint.setColor(Color.RED);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        Bitmap bit = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/Qnote/22.jpg");
        canvas.drawBitmap(bit,50,50,paint);
        canvas.drawText(text,20,20,paint);
        canvas.save(Canvas.ALL_SAVE_FLAG);

        canvas.restore();
        String path = Environment.getExternalStorageDirectory() + "/Qnote";//图片保存路径
        File mfile=new File(path);

        if(!mfile.exists()){
            mfile.mkdir();
        }
        try {
            FileOutputStream fos = new FileOutputStream(mfile+"/share.png");
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);//以png格式保存
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
