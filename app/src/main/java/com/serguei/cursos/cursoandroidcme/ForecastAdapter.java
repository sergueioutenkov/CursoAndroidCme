package com.serguei.cursos.cursoandroidcme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by serguei on 03/10/16.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private final int TODAY = 0;
    private final int OTHER_DAY = 1;
    private List<Forecast> forecasts;
    private Context context;

    public ForecastAdapter(List<Forecast> forecasts, Context context) {
        this.forecasts = forecasts;
        this.context = context;
    }

    //Metodo que se ejecuta cada vez que tiene que crear el ViewHolder, debemos crear el ViewHolder y devolverlo
    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Dependiendo del viewType, instanciamos distintas vistas
        switch (viewType) {
            case TODAY:
                return new ForecastViewHolder(LayoutInflater.from(context).inflate(R.layout.forecast_list_item_today, parent, false));
            case OTHER_DAY:
                return new ForecastViewHolder(LayoutInflater.from(context).inflate(R.layout.forecast_list_item, parent, false));
            default:
                return new ForecastViewHolder(LayoutInflater.from(context).inflate(R.layout.forecast_list_item, parent, false));
        }
    }

    //Devolvemos la cantidad de items de nuestra lista
    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    //Metodo que se ejecuta para unir un objeto de la lista de items con el ViewHolder
    @Override
    public void onBindViewHolder(final ForecastViewHolder holder, int position) {

        //Obtenemos el item de la lista con la posicion
        final Forecast forecast = forecasts.get(position);

        //Bindeamos los datos del objeto con la vista
        holder.imageView.setImageResource(forecast.getImage());
        holder.locationView.setText(forecast.getLocation());
        holder.descriptionView.setText(forecast.getDescription());
        holder.temperatureView.setText(forecast.getTemperature());

        //Cuando se hace click en el item, se muestra un toast
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, forecast.getLocation() + ": " + forecast.getTemperature(), Toast.LENGTH_SHORT).show();
            }
        });

        //A modo de muestra, cuando se haga click en la temperatura, vamos a borrar el item
        holder.temperatureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pasamos la posicion del item en el adapter al metodo
                removeItem(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        //Si es la primer posicion, corresponde al pronostico de hoy
        if (position == 0) {
            return TODAY;
        } else {
            return OTHER_DAY;
        }
    }

    private void removeItem(int position) {
        //Removemos el item de la lista de forecasts
        forecasts.remove(position);

        //Los siguientes metodos son para notificar al adapter que un item fue removido.
        notifyItemRemoved(position);
    }

    //Clase que representa a nuestro elemento(vista) en el RecyclerView
    static class ForecastViewHolder extends RecyclerView.ViewHolder {

        View container;
        ImageView imageView;
        TextView locationView;
        TextView descriptionView;
        TextView temperatureView;

        public ForecastViewHolder(View itemView) {
            super(itemView);

            //Instanciamos las vistas
            container = itemView.findViewById(R.id.forecast_container);
            imageView = (ImageView) itemView.findViewById(R.id.forecast_image);
            locationView = (TextView) itemView.findViewById(R.id.forecast_location);
            descriptionView = (TextView) itemView.findViewById(R.id.forecast_description);
            temperatureView = (TextView) itemView.findViewById(R.id.forecast_temperature);
        }
    }

}
