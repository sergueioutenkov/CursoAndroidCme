package com.serguei.cursos.cursoandroidcme.facts_list.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.serguei.cursos.cursoandroidcme.R;
import com.serguei.cursos.cursoandroidcme.facts_list.CatFactsListMvp;
import com.serguei.cursos.cursoandroidcme.facts_list.model.CatFactsInteractor;
import com.serguei.cursos.cursoandroidcme.facts_list.presenter.CatFactsPresenter;

import java.util.List;

public class CatFactsListActivity extends AppCompatActivity implements CatFactsListMvp.View {

    //Vistas
    private RecyclerView recyclerView;
    private ProgressBar loadingBar;

    private CatFactsListMvp.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciamos nuestro recycler view
        recyclerView = (RecyclerView) findViewById(R.id.cat_facts_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(CatFactsListActivity.this));

        //Instanciamos nuestra loading bar
        loadingBar = (ProgressBar) findViewById(R.id.loading_bar);

        //Instanciamos el presenter
        presenter = new CatFactsPresenter(this, new CatFactsInteractor());

        //Le pedimos al presenter obtener los facts
        presenter.getCatFacts(30);
    }

    @Override
    protected void onDestroy() {

        //Debemos avisar al presenter que la activity se esta "destruyendo"
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        recyclerView.setVisibility(View.GONE);
        loadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayCatFacts(List<String> facts) {

        //Cuando obtenemos la respuesta, populamos el adapter
        CatFactsAdapter adapter = new CatFactsAdapter(facts, this);
        recyclerView.setAdapter(adapter);

        //Mostrar el recycler view
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //"Inflamos" el menu
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {

            //Llamamos al presenter
            presenter.getCatFacts(30);
        }

        return true;
    }
}
