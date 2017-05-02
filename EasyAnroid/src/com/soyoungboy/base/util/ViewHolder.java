package com.soyoungboy.base.util;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lvzho on 2015/4/29 0029.
 */
public class ViewHolder {
    private SparseArray<View> views;
    private View convertView;


    public ViewHolder(ViewGroup parent, LayoutInflater inflater, int layoutId) {
        this.views = new SparseArray<View>();
        this.convertView = inflater.inflate(layoutId, parent, false);
        this.convertView.setTag(this);
    }


    /**
     * 得到viewHolder
     */
    public static ViewHolder getViewHolder(ViewGroup parent, View convertView, LayoutInflater inflater, int layoutId) {
        if (convertView == null) {
            return new ViewHolder(parent, inflater, layoutId);
        }
        return (ViewHolder) convertView.getTag();
    }


    /**
     * 得到convertView
     */
    public View getConvertView() {
        return convertView;
    }


    /**
     * 根据Id得到view
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }


    /**
     * 根据id得到TextView
     */
    public TextView getTextView(int viewId) {
        return getView(viewId);
    }


    /**
     * 根据id得到ImageView
     */
    public ImageView getImageView(int viewId) {
        return getView(viewId);
    }


    /**
     * 根据id得到Button
     */
    public Button getButton(int viewId) {
        return getView(viewId);
    }


    /**
     * 根据id得到RadioButton
     */
    public RadioButton getRadioButton(int viewId) {
        return getView(viewId);
    }


    /**
     * 根据id得到CheckBox
     */
    public CheckBox getCheckBox(int viewId) {
        return getView(viewId);
    }


    /**
     * 根据id得到ImageButton
     */
    public ImageButton getImageButton(int viewId) {
        return getView(viewId);
    }


    /**
     * 根据id得到ImageButton
     */
    public EditText getEditText(int viewId) {
        return getView(viewId);
    }


    /**
     * 根据id得到RelativeLayout
     */
    public RelativeLayout getRelativeLayout(int viewId) {
        return getView(viewId);
    }
}
