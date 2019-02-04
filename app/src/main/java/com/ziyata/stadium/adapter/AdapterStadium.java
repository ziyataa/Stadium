package com.ziyata.stadium.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ziyata.stadium.DetailActivity;
import com.ziyata.stadium.R;
import com.ziyata.stadium.model.Constant;
import com.ziyata.stadium.model.StadiumData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterStadium extends RecyclerView.Adapter<AdapterStadium.ViewHolder> {


    private Bundle bundle;
    private Context context;
    private final List<StadiumData> teamDataList;

    public AdapterStadium(Context context, List<StadiumData> teamDataList) {
        this.context = context;
        this.teamDataList = teamDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final StadiumData dataResponse = teamDataList.get(i);
        viewHolder.txtNamaStadium.setText(dataResponse.getStrStadium());
        Glide.with(context).load(dataResponse.getStrStadiumThumb()).into(viewHolder.imgAvatar);
        RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image);
        Glide.with(context).load(dataResponse.getStrStadiumThumb()).apply(options).into(viewHolder.imgAvatar);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putString(Constant.id, dataResponse.getIdTeam());
                context.startActivity(new Intent(context, DetailActivity.class).putExtras(bundle));
            }
        });



    }

    @Override
    public int getItemCount() {
        return teamDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgAvatar)
        ImageView imgAvatar;
        @BindView(R.id.txtNamaStadium)
        TextView txtNamaStadium;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
