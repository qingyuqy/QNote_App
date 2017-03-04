package com.qingyu.qnote.utils;

import android.provider.ContactsContract;

import com.qingyu.qnote.vo.NoteVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QingYu on 2017/2/28 0028.
 */

public class NoteUtils {
    private DBHelper dbHelper;
    public boolean saveNote(NoteVO vo){
        return dbHelper.insert(vo);
    }
    public boolean updateNote(NoteVO vo){
        return dbHelper.insert(vo);
    }
    public List<NoteVO> retreiveNotes(){

        return dbHelper.query() ;
    }
    public NoteVO getNote(long key){

        return new NoteVO();
    }
}
