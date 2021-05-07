package com.animation_study.activity;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

import com.animation_study.R;

/**
 * Created by css on 2017/10/20.
 */

public class AutoTextActivity extends BaseActiytiy {
    AutoCompleteTextView mAuto;
    MultiAutoCompleteTextView mMauto;
    String strs[] = {"小猪猪", "小可爱", "小毛毛", "小馒头", "小猪猪", "小猪猪"};

    @Override
    public Integer getLayout() {
        return R.layout.activity_auto_text;
    }

    @Override
    public void findView() {
        mAuto = (AutoCompleteTextView) findViewById(R.id.atv);
        mMauto = (MultiAutoCompleteTextView) findViewById(R.id.matv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, strs);
        mAuto.setAdapter(adapter);
        mMauto.setAdapter(adapter);
        mMauto.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}
