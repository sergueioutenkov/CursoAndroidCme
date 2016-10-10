package com.serguei.cursos.cursoandroidcme.service;

import com.serguei.cursos.cursoandroidcme.facts_list.model.CatFactsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatFactsApi {

    //URL base de nuestra API
    String ENDPOINT = "http://catfacts-api.appspot.com/api/";

    /**
     * Método que obtiene la lista de curiosidades de los gatos
     * Ejemplo URL: http://catfacts-api.appspot.com/api/facts?number=5
     *
     * @param number
     */
    @GET("facts")
    Call<CatFactsResponse> getCatFacts(@Query("number") int number);
}
