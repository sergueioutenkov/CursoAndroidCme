package com.serguei.cursos.cursoandroidcme.facts_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.serguei.cursos.cursoandroidcme.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CatFactDetailActivity extends AppCompatActivity {

    public static final String CAT_FACT_IMAGE_URL = "catImageUrl";
    public static final String CAT_FACT_TEXT = "catFactText";
    CircleImageView image;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_fact_detail);

        //Vistas
        image = (CircleImageView) findViewById(R.id.cat_fact_image);
        text = (TextView) findViewById(R.id.cat_fact_text);

        //Obtenemos los datos del intent con el cuál fue invocada la activity
        Intent intent = getIntent();
        String catImageUrl = intent.getStringExtra(CAT_FACT_IMAGE_URL);
        String factText = intent.getStringExtra(CAT_FACT_TEXT);

        //Seteamos la imagen al image View con Picasso
        Picasso.with(this).load(catImageUrl).placeholder(R.mipmap.ic_launcher).into(image);

        //Seteamos el texto
        text.setText(factText);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Manualmente realizar la transicion hacia atras de la imagen
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
