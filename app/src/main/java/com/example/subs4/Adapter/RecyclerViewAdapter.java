package com.example.subs4.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.subs4.Activity.DetailActivity;
import com.example.subs4.Model.Movie;
import com.example.subs4.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<Movie> mData = new ArrayList<>();


    public void setData(ArrayList<Movie> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int i) {

        holder.bind(mData.get(i));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(v.getContext(), DetailActivity.class);

                //mengirimkan data yang dipilih dengan identitas Extra_Movie
                x.putExtra(DetailActivity.Extra_Movie, mData.get(i));
                v.getContext().startActivity(x);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder   {
        private TextView tv_title, tv_desc;
        private ImageView iv_img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title_movie);
            tv_desc = itemView.findViewById(R.id.tv_desc_movie);
            iv_img = itemView.findViewById(R.id.img_movie);

        }

        void bind(Movie movie) {
            tv_title.setText(movie.getTitle());
            tv_desc.setText(movie.getDesc());
            Picasso.get().load(movie.getImg()).into(iv_img);

        }
    }


}
