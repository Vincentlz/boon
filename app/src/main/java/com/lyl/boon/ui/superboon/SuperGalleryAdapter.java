package com.lyl.boon.ui.superboon;

import android.content.Context;
import android.widget.ImageView;

import com.lyl.boon.R;
import com.lyl.boon.net.entity.SuperImageEntity;
import com.lyl.boon.ui.base.apdter.MyBaseAdapter;
import com.lyl.boon.utils.ImgUtils;
import com.lyl.boon.utils.NetStatusUtil;

import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class SuperGalleryAdapter extends MyBaseAdapter<SuperImageEntity.ListBean> {

    private Context context;

    public SuperGalleryAdapter(Context context, List<SuperImageEntity.ListBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int position, SuperImageEntity.ListBean item) {
        super.onBind(holder, viewType, position, item);
        if (NetStatusUtil.isWifi(mContext)) {
            ImgUtils.load(context, //
                    item.getQhimg_url(), //
                    (ImageView) holder.getView(R.id.item_image), //
                    item.getPic_width(), //
                    item.getPic_height());
        } else {
            ImgUtils.load(context, //
                    item.getQhimg_thumb_url(), //
                    (ImageView) holder.getView(R.id.item_image), //
                    item.getQhimg_thumb_width(),//
                    item.getQhimg_thumb_height());
        }
    }

}
