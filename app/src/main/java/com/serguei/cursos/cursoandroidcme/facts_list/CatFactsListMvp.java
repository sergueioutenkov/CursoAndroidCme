package com.serguei.cursos.cursoandroidcme.facts_list;

import java.util.List;

/**
 * Created by serguei on 10/10/16.
 */

public interface CatFactsListMvp {

    interface View {

        void showLoading();

        void hideLoading();

        void showError(String errorMessage);

        void displayCatFacts(List<String> facts);

    }

    interface Presenter {

        void getCatFacts(int factsNumber);

        void onDestroy();

        interface GetFactsListCallback {

            void onSuccess(List<String> catFacts);

            void onError(String errorMessage);

        }

    }

    interface Interactor {

        void getCatFacts(int factsNumber, final CatFactsListMvp.Presenter.GetFactsListCallback callback);

    }

}
