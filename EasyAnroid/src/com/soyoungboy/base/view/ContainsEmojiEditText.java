package com.soyoungboy.base.view;

import android.content.Context;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * @author soyoungboy
 * @name ContainsEmojiEditText
 * @description 用于处理表情的自定义控件，并不完美
 * @date 2015-6-17
 */
public class ContainsEmojiEditText extends EditText {
    int maxLength = -1;


    public ContainsEmojiEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addListener(attrs);
    }


    public ContainsEmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        addListener(attrs);
    }


    public ContainsEmojiEditText(Context context) {
        super(context);
        addListener(null);
    }


    private void addListener(AttributeSet attrs) {
        if (attrs != null) {
            maxLength = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android",
                "maxLength", -1);
        }
        // 过滤输入法表情
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                StringBuffer buffer = new StringBuffer();
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    // 第一个字符为以下时，过滤掉
                    if (c == 55356 || c == 55357 || c == 10060 || c == 9749 || c == 9917 ||
                        c == 10067 || c == 10024 || c == 11088 || c == 9889 || c == 9729 ||
                        c == 11093
                        || c == 9924) {
                        i++;
                    } else {
                        buffer.append(c);
                    }
                }
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(buffer);
                    TextUtils.copySpansFrom((Spanned) source, start, end, null, sp, 0);
                    return sp;
                } else {
                    return buffer;
                }
            }
        };
        // 输入框长度限制
        if (maxLength > 0) {
            setFilters(new InputFilter[] { filter, new InputFilter.LengthFilter(maxLength) });
        } else {
            setFilters(new InputFilter[] { filter });
        }
    }
}
