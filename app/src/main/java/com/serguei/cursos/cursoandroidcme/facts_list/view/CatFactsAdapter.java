package com.serguei.cursos.cursoandroidcme.facts_list.view;// Created by Serguei Outenkov on 8/28/16.

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.serguei.cursos.cursoandroidcme.R;
import com.serguei.cursos.cursoandroidcme.facts_detail.CatFactDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class CatFactsAdapter extends RecyclerView.Adapter {
    List<String> catFacts;
    List<String> catImagesURLs = new ArrayList<>();
    Context context;

    public CatFactsAdapter(List<String> catFacts, Context context) {
        this.catFacts = catFacts;
        this.context = context;

        //Por cada fact, generamos una URL
        for (String fact : catFacts) {

            //Generamos un ID random en un rango
            int min = 300;
            int max = 500;
            Random r = new Random();
            int width = r.nextInt(max - min + 1) + min;
            int height = r.nextInt(max - min + 1) + min;

            //Url de la imagen
            String imageURL = "https://placekitten.com/" + width + "/" + height;
            catImagesURLs.add(imageURL);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CatFactViewHolder(LayoutInflater.from(context).inflate(R.layout.cat_fact_list_item, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //Obtenemos el fact y la URL de la imagen
        final String catFact = catFacts.get(position);
        final String catImageURL = catImagesURLs.get(position);

        final CatFactViewHolder viewHolder = (CatFactViewHolder) holder;

        //Seteamos la imagen al image View con Picasso
        Picasso.with(context).load(catImageURL).placeholder(R.mipmap.ic_launcher).into(viewHolder.catFactImageView);

        //Seteamos texto
        viewHolder.catFactTextView.setText(catFact);

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el intent
                Intent intent = new Intent(context, CatFactDetailActivity.class);

                //Seteamos los parametros q queremos pasar
                intent.putExtra(CatFactDetailActivity.CAT_FACT_IMAGE_URL, catImageURL);
                intent.putExtra(CatFactDetailActivity.CAT_FACT_TEXT, catFact);

                //Preparacion de la animacion
                // Obtenemos el nombre de la transicion
                String transitionName = context.getString(R.string.image_transition);

                //Creamos la animacion
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((CatFactsListActivity) context, viewHolder.catFactImageView, transitionName);

                //Realizamos la transicion
                ActivityCompat.startActivity((CatFactsListActivity) context, intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return catFacts.size();
    }

    static class CatFactViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        CircleImageView catFactImageView;
        TextView catFactTextView;

        public CatFactViewHolder(View itemView) {
            super(itemView);

            container = (RelativeLayout) itemView.findViewById(R.id.container);
            catFactImageView = (CircleImageView) itemView.findViewById(R.id.cat_fact_image);
            catFactTextView = (TextView) itemView.findViewById(R.id.cat_fact_text);
        }
    }
}
