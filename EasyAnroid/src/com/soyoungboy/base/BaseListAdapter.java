
package com.soyoungboy.base;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

/** 
* @ClassName: BaseListAdapter 
* @Description: 基础baseAdapter
* @author soyoungboy
* @date 2014-6-19 上午9:48:35  
*/
public abstract class BaseListAdapter extends BaseAdapter {
    protected LayoutInflater mInflater;
    
    protected List<BaseModel> list;
    
    protected Context mContext;
    
    public BaseListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        
    }
    
    public List<BaseModel> getList() {
        return list;
    }
    
    public void setList(List<BaseModel> list) {
        this.list = list;
    }
    
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }
    
    public Object getItem(int position) {
        if (list != null && list.size() > 0 && position < list.size()
            && position > -1) {
            return list.get(position);
        }
        return null;
    }
    
    public long getItemId(int position) {
        return position;
    }
    
}
