package me.paxana.alerta.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.paxana.alerta.R;
import me.paxana.alerta.model.ItemSlideMenu;

/**
 * Created by paxie on 1/13/16.
 */
public class SlidingMenuAdapter extends BaseAdapter {

    private Context mContext;
    private List<ItemSlideMenu> lstItem;

    public SlidingMenuAdapter(Context context, List<ItemSlideMenu> lstItem) {
        mContext = context;
        this.lstItem = lstItem;
    }

    @Override
    public int getCount() {
        return lstItem.size();
    }

    @Override
    public Object getItem(int position) {
        return lstItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_sliding_menu, null);
        ImageView img = (ImageView)v.findViewById(R.id.item_img);
        TextView tv = (TextView)v.findViewById(R.id.item_title);

        ItemSlideMenu item = lstItem.get(position);
        img.setImageResource(item.getImgId());
        tv.setText(item.getTitle());
        return v;
    }
}