package com.serguei.cursos.cursoandroidcme.facts_list.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.serguei.cursos.cursoandroidcme.facts_list.CatFactsListMvp;
import com.serguei.cursos.cursoandroidcme.service.CatFactsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by serguei on 10/10/16.
 */

public class CatFactsInteractor implements CatFactsListMvp.Interactor {

    @Override
    public void getCatFacts(int factsNumber, final CatFactsListMvp.Presenter.GetFactsListCallback callback) {

        //Instanciamos el parseador Gson
        Gson gson = new GsonBuilder().create();

        //Creamos la instancia de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CatFactsApi.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //Instanciamos la interface gracias a Retrofit
        CatFactsApi catFactsAPI = retrofit.create(CatFactsApi.class);

        //Instanciamos la llamada (Call) a la API, pasando como parametro
        // la cantidad de facts sobre los gatos que queremos obtener
        Call<CatFactsResponse> call = catFactsAPI.getCatFacts(factsNumber);

        //Ejecutamos la llamada en un thread distinto al main (por eso el "enqueue" en vez de "execute")
        call.enqueue(new Callback<CatFactsResponse>() {
            @Override
            public void onResponse(Call<CatFactsResponse> call, Response<CatFactsResponse> response) {

                //Llamamos al presenter a traves del callback
                callback.onSuccess(response.body().facts);
            }

            @Override
            public void onFailure(Call<CatFactsResponse> call, Throwable t) {

                //Si fallo, le avisamos al presenter a traves del callback
                callback.onError(t.getLocalizedMessage());
            }
        });

    }

}
