package com.serguei.cursos.cursoandroidcme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    RecyclerView forecastsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciamos el RecyclerView
        forecastsRecyclerView = (RecyclerView) findViewById(R.id.forecast_list);

        //Instanciamos nuestro adapter
        ForecastAdapter forecastAdapter = new ForecastAdapter(Forecast.getFakeForecasts(), MainActivity.this);

        //Tenemos que setear un LayoutManager para decirle al RecyclerView de que manera queremos que muestre los items.
        //En este caso queremos una lista vertical
        forecastsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //Seteamos nuestro adaptador al recycler view, a partir de ahora ya estan los items renderizados en la lista.
        forecastsRecyclerView.setAdapter(forecastAdapter);
    }
}
