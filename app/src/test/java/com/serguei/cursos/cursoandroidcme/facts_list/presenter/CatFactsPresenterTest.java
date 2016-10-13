package com.serguei.cursos.cursoandroidcme.facts_list.presenter;

import com.serguei.cursos.cursoandroidcme.facts_list.CatFactsListMvp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by serguei on 12/10/16.
 */
public class CatFactsPresenterTest {

    @Mock
    CatFactsListMvp.View view;
    @Mock
    CatFactsListMvp.Interactor interactor;

    CatFactsListMvp.Presenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new CatFactsPresenter(view, interactor);
    }

    @Test
    public void validApiCallShowsListOfFacts() {

        //Mockeamos nuestra lista de facts
        final List<String> fakeFactsList = new ArrayList<>();
        fakeFactsList.add("Test Fact 1");
        fakeFactsList.add("Test Fact 2");

        //Cuando se ejecute el interactor, invocamos el metodo "onSuccess()" del callback pasado argumento 1 (porque es el 2do y empieza desde el 0)
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((CatFactsListMvp.Presenter.GetFactsListCallback) invocation.getArguments()[1]).onSuccess(fakeFactsList);
                return null;
            }
        }).when(interactor).getCatFacts(anyInt(), any(CatFactsListMvp.Presenter.GetFactsListCallback.class));

        //Llamamos el metodo del presenter
        presenter.getCatFacts(1000);

        //Verificamos que se muestre el loading, se oculte y se muestren los facts
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).displayCatFacts(fakeFactsList);

        //Tambien verificamos que nunca se llame el metodo "showError()" de la vista.
        verify(view, never()).showError(anyString());

    }

    @Test
    public void invalidApiCallShowsError() {

        final String errorMessage = "Error";

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((CatFactsListMvp.Presenter.GetFactsListCallback) invocation.getArguments()[1]).onError(errorMessage);
                return null;
            }
        }).when(interactor).getCatFacts(anyInt(), any(CatFactsListMvp.Presenter.GetFactsListCallback.class));

        presenter.getCatFacts(200);
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showError(errorMessage);
        verify(view, never()).displayCatFacts(anyListOf(String.class));

    }

    @Test
    public void onDestroyClearsView() {

        presenter.onDestroy();
        verify(view, never()).showLoading();

    }


}