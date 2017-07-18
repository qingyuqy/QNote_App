package com.qingyu.qnote;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.qingyu.qnote.Adapter.NoteAdapter;
import com.qingyu.qnote.utils.NoteUtils;
import com.qingyu.qnote.vo.NoteVO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static SharedPreferences sp1;
    private ListView note_ListView;
    private FloatingActionButton fab;
    private ArrayList<NoteVO> noteList;
    NoteAdapter noteAdapter;
    private boolean firstStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    public void initData(){
        NoteUtils.getInstance().initDB(this);
        sp1 = getSharedPreferences("sign_note", MODE_PRIVATE);
        if(sp1.getBoolean("isFirstStart",true)){
            NoteVO note = new NoteVO();
            note.setTitle("意义");
            note.setContent("意义在于人");
            note.setDate(new Date().getTime());
            NoteUtils.getInstance().createNote(note);
            SharedPreferences.Editor editor = sp1.edit();
            editor.putBoolean("isFirstStart", false);
            editor.commit();
        }
        noteList = (ArrayList<NoteVO>) NoteUtils.getInstance().retreiveNotes();
    }
    public void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,NoteActivity.class);
                startActivityForResult(intent,0);
            }
        });

        if(noteList.size()==0){
            return;
        }
        noteAdapter = new NoteAdapter(this,noteList);
        note_ListView=(ListView)findViewById(R.id.note_listview);
        note_ListView.setAdapter(noteAdapter);
        note_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoteVO note = noteList.get(position);
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Note",note);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });

        note_ListView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                NoteVO note = noteList.get(position);
                if( NoteUtils.getInstance().deleteNote(note)){
                    Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                    refreshData();
                    return true;
                }
                else
                    return false;
            }
        });

    }

    public  void refreshData(){
        noteList.clear();
        noteList.addAll((ArrayList<NoteVO>) NoteUtils.getInstance().retreiveNotes());
        noteAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                refreshData();
            case 1:
                refreshData();
            default:
                refreshData();
        }
        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            personalized();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void personalized(){
        final Window win = getWindow();
        LayoutInflater mInflater = LayoutInflater.from(this);
        View popView = mInflater.inflate(R.layout.personalized, null);
        final EditText et_sign = (EditText) popView.findViewById(R.id.et_sign);
        Button bt_save = (Button) popView.findViewById(R.id.bt_save);
        final PopupWindow popWindow = new PopupWindow(popView,
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        popWindow.setFocusable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setOutsideTouchable(true);
        popWindow.showAtLocation(this.getCurrentFocus(), Gravity.CENTER_VERTICAL
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        setbackgroundAlpha(0.7f, win);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                setbackgroundAlpha(1f, win);
            }
        });
        et_sign.setText(sp1.getString("signature",""));
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp1.edit();
                editor.putString("signature",et_sign.getText().toString());
                editor.commit();
                Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_LONG).show();
                popWindow.dismiss();
            }
        });
    }
    public static void setbackgroundAlpha(float bgAlpha, Window win) {
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        win.setAttributes(lp);
    }

/*    public void draw(String text){
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
    }*/
}
