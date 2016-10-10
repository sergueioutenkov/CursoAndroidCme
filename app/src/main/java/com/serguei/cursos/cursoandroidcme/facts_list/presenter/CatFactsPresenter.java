package com.serguei.cursos.cursoandroidcme.facts_list.presenter;

import com.serguei.cursos.cursoandroidcme.facts_list.CatFactsListMvp;
import com.serguei.cursos.cursoandroidcme.facts_list.model.CatFactsInteractor;

import java.util.List;

/**
 * Created by serguei on 10/10/16.
 */

public class CatFactsPresenter implements CatFactsListMvp.Presenter {

    private CatFactsListMvp.View view;
    private CatFactsListMvp.Interactor interactor;

    public CatFactsPresenter(CatFactsListMvp.View view) {
        this.view = view;
        this.interactor = new CatFactsInteractor();
    }

    @Override
    public void getCatFacts(int factsNumber) {

        if (view != null) {
            view.showLoading();
        }

        interactor.getCatFacts(factsNumber, new GetFactsListCallback() {
            @Override
            public void onSuccess(List<String> catFacts) {
                if (view != null) {
                    view.hideLoading();
                    view.displayCatFacts(catFacts);
                }
            }

            @Override
            public void onError(String errorMessage) {
                if (view != null) {
                    view.hideLoading();
                    view.showError(errorMessage);
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
