package com.qingyu.qnote.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingyu.qnote.R;
import com.qingyu.qnote.vo.NoteVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class NoteAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<NoteVO> noteList;

	public NoteAdapter(Context context, ArrayList<NoteVO> noteList) {
		mInflater = LayoutInflater.from(context);
		/*if (noteList.size() == 0) {
			NoteVO note = new NoteVO();
			noteList.add(note);
		}*/
		this.noteList = noteList;
	}

	@Override
	public int getCount() {
		// Meeting Auto-generated method stub
		return noteList.size();
	}

	@Override
	public Object getItem(int position) {
		// Meeting Auto-generated method stub
		return noteList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// Meeting Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Meeting Auto-generated method stub
        if(noteList.size()==0){
            return null;
        }
		NoteVO note = noteList.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.notelist, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.tvNote_title = (TextView) convertView.findViewById(R.id.note_title);
			viewHolder.tvNote_desc = (TextView) convertView
					.findViewById(R.id.note_desc);
			viewHolder.tvNote_date = (TextView) convertView
					.findViewById(R.id.note_date);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
        viewHolder.tvNote_title.setText(note.getTitle());
        viewHolder.tvNote_date.setText(getDateStr(note.getDate()));
        viewHolder.tvNote_desc.setText(note.getContent());

		return convertView;
	}

	final class ViewHolder {
		TextView tvNote_title;
		TextView tvNote_desc;
		TextView tvNote_date;
	}
    public static String getDateStr(long milliseconds) {
        return new SimpleDateFormat("yyyy年MM月dd日 EEEE HH点mm分", Locale.CHINA).format(milliseconds);
    }
}
