
package com.soyoungboy.base.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
/**
 * 邮箱自动填充控件
 * @author soyoungboy
 *
 */
public class EmailAutoCompleteTextview extends AutoCompleteTextView {
	private static final String[] emailSuffix = {"@qq.com", "@163.com",
		"@126.com", "@gmail.com", "@sina.com", "@hotmail.com", "@yahoo.cn",
		"@sohu.com", "@foxmail.com", "@139.com", "@yeah.net", "@vip.qq.com",
	"@vip.sina.com"};

	public EmailAutoCompleteTextview(Context context) {
		super(context);
		init(context);
	}

	public EmailAutoCompleteTextview(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		final MyAdatper adapter = new MyAdatper(context);
		setAdapter(adapter);
		addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				String input = s.toString();
				adapter.mList.clear();
				if (input.length() > 0) {
					for (int i = 0; i < emailSuffix.length; ++i) {
						adapter.mList.add(input + emailSuffix[i]);
					}
				}
				adapter.notifyDataSetChanged();
				showDropDown();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});
		// default=2 当输入一个字符的时候就开始检测
				setThreshold(1);
	}

	class MyAdatper extends BaseAdapter implements Filterable {
		List<String> mList;

		private Context mContext;

		private MyFilter mFilter;

		public MyAdatper(Context context) {
			mContext = context;
			mList = new ArrayList<String>();
		}

		@Override
		public int getCount() {
			return mList == null ? 0 : mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList == null ? null : mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				TextView tv = new TextView(mContext);
				tv.setTextColor(Color.BLACK);
				tv.setTextSize(20);
				convertView = tv;
			}
			TextView txt = (TextView)convertView;
			txt.setText(mList.get(position));
			return txt;
		}

		@Override
		public Filter getFilter() {
			if (mFilter == null) {
				mFilter = new MyFilter();
			}
			return mFilter;
		}

		private class MyFilter extends Filter {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();
				if (mList == null) {
					mList = new ArrayList<String>();
				}
				results.values = mList;
				results.count = mList.size();
				return results;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				if (results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}
		}
	}
}
