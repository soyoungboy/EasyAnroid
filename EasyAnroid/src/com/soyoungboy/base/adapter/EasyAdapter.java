package com.soyoungboy.base.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.soyoungboy.base.util.ViewHolder;

/**
 * 基础适配器
 * 
 * @author soyoungboy
 * @param <T>
 */
public abstract class EasyAdapter<T> extends BaseAdapter {
	private LayoutInflater inflater;
	private int layoutId;
	private List<T> mlist = new ArrayList<T>();

	public EasyAdapter(Context context, int layoutId, List<T> list) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.layoutId = layoutId;
		this.mlist = list;
	}

	/**
	 * 往顶部添加数据
	 * 
	 * @param list
	 */
	public void add2Head(List<T> list) {
		mlist.addAll(0, list);
		notifyDataSetChanged();
	}
	
	public void clearAll() {
		mlist.clear();
		notifyDataSetChanged();
	}

	public List<T> getAllList() {
		return mlist;
	}

	/**
	 * 往底部添加数据
	 * 
	 * @param list
	 */
	public void add2Bottom(List<T> list) {
		mlist.addAll(list);
		notifyDataSetChanged();
	}

	public void add2Bottom(T t) {
		mlist.add(t);
		notifyDataSetChanged();
	}

	/**
	 * @Title: updateListView
	 * @Description: TODO(更新BaseAdapter中的数据)
	 * @param @param list 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void updateListView(List<T> list) {
		mlist = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public T getItem(int position) {
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	/**
	 * 实际显示View的方法，使用抽象方法强制调用者覆写！
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = ViewHolder.getViewHolder(parent, convertView,
				inflater, layoutId);
		convert(viewHolder, mlist.get(position));
		return viewHolder.getConvertView();

	}

	public abstract void convert(ViewHolder viewHolder, T t);

}
