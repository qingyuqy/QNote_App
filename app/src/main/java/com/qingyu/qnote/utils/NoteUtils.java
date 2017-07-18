package com.qingyu.qnote.utils;

import com.qingyu.qnote.vo.NoteVO;
import android.content.Context;
import java.util.List;

/**
 * Created by QingYu on 2017/2/28 0028.
 */

public class NoteUtils {

    private static NoteUtils noteUtil;
    public void initDB(Context context){
        NoteDBHelper.getInstance().open(context);
    }
    public static NoteUtils getInstance(){
        if(noteUtil == null)
            noteUtil = new NoteUtils();
        return noteUtil;
    }
    public boolean createNote(NoteVO vo){
        return NoteDBHelper.getInstance().insert(vo);
    }
    public boolean updateNote(NoteVO vo){
        return NoteDBHelper.getInstance().update(vo);
    }
    public boolean deleteNote(NoteVO vo){
        return NoteDBHelper.getInstance().delete(vo);
    }
    public List<NoteVO> retreiveNotes(){
        return NoteDBHelper.getInstance().query();
    }
    public NoteVO getNote(long key){
        return null;
    }
}
