package com.example.user.noterealmdatabase;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by User on 08/03/2017.
 */

public class MyBookViewHolder extends RecyclerView.ViewHolder {

    public TextView tvBookId,tvBookTitle;
    public ImageView imgEdit,imgDelete;
    public MyBookViewHolder(View itemView) {
        super(itemView);
        tvBookId = (TextView) itemView.findViewById(R.id.tvBookId);
        tvBookTitle = (TextView) itemView.findViewById(R.id.tvBookTitle);
        imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
        imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
    }

}
