package com.org.demo2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author: created by qinsen
 * date: 2021/1/18 15
 * email: qinshen@Gangtise.onaliyun.com
 **/
public class GKeyWizardAdapter extends BaseAdapter {

    private Context mContent;
    private List<Object> codelist ;
    private List<Object> abbrlist ;
    private LayoutInflater mLayoutInflater;

    public GKeyWizardAdapter(Context context,List<Object> list1,List<Object> list2) {
        this.mContent = context;
        this.codelist = list1;
        this.abbrlist = list2;

        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return codelist.size();
    }

    @Override
    public Object getItem(int position) {
        return codelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GKeyWizardAdapter.ViewHolder holder = null;

        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.layout_gkwizard,null);

            holder = new GKeyWizardAdapter.ViewHolder();
            holder.tv_code = convertView.findViewById(R.id.tv_code);
            holder.tv_name = convertView.findViewById(R.id.tv_name);

            convertView.setTag(holder);
        }else {
            holder = (GKeyWizardAdapter.ViewHolder) convertView.getTag();
        }

        //给控件赋值
        Object code = codelist.get(position);
        Object abbr = abbrlist.get(position);
        holder.tv_code.setText(code.toString());
        holder.tv_name.setText(abbr.toString());
        return convertView;
    }

    static class ViewHolder{
        private TextView tv_code,tv_name;
    }
}
