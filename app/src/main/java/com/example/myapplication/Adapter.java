package com.example.myapplication;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.newsHolder> implements Filterable {

    private Context context;
    private ArrayList<Articles> articles;
    private ArrayList<Articles> suggestionArrayList;
    private ArrayList<Articles> display;
    private newsFilter filter;
    private NewsCategoryAdapter.newsCategoryClickListner listner;
    private int selectedPosition = -1;

    public Adapter (Context context, ArrayList<Articles> articles) {
        this.articles=articles;
        this.context=context;
       this.display = (ArrayList<Articles>) articles.clone();
       this.suggestionArrayList = new ArrayList<>();
       filter = new newsFilter();

    }

    @NonNull
    @Override
    public newsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new newsHolder(LayoutInflater.from(context).inflate(R.layout.cell_news,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull newsHolder holder, int position) {

        Articles currentArticle = display.get(position);
        holder.mtvTitle.setText(currentArticle.title);
        holder.mtvDescription.setText(currentArticle.description);
        Glide.with(context).load(currentArticle.urlToImage).placeholder(R.drawable.ic_launcher_background).into(holder.mIvNewsImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition=position;

                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(currentArticle.url));
                context.startActivity(viewIntent);
            }
        });
        if (position == selectedPosition){
            holder.itemView.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.selected,null));
        }else{
            holder.itemView.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.unselected,null));
        }

    }

    @Override
    public int getItemCount() {
        return display != null ? display.size():0;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    class newsHolder extends RecyclerView.ViewHolder{

       private TextView mtvTitle;
       private TextView mtvDescription;
       private ImageView mIvNewsImage;

       public newsHolder(@NonNull View itemView) {
           super(itemView);
          mtvTitle = itemView.findViewById(R.id.tv_title);
          mtvDescription = itemView.findViewById(R.id.tv_description);
          mIvNewsImage = itemView.findViewById(R.id.iv_cell_news);


       }

   }
    public class newsFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            suggestionArrayList.clear();

            if (constraint!= null){
                for (Articles article : articles) {
                    if (article.title.toLowerCase().contains(constraint.toString().toLowerCase())){
                        suggestionArrayList.add(article);
                    }else if (article.title.toLowerCase().contains(constraint.toString().toLowerCase())){
                        suggestionArrayList.add(article);
                    }
                }

            }

            results.values = suggestionArrayList;
            results.count = suggestionArrayList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results != null){
                if (results.count > 0){

                    display = (ArrayList<Articles>) results.values;
                    notifyDataSetChanged();
                }
            }

        }



    }

    public void clearsearch(){
        display.clear();
        display = (ArrayList<Articles>) articles.clone();
        notifyDataSetChanged();


    }
}
