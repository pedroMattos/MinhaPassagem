package peoplesolutions.com.minhapassagem;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MenuDirect extends AppCompatActivity{
    private Button calcRest, calcSab, saldRest, semRest;
    public Toolbar toolBar;
    private TextView contSald;
    Preferencias preferencias = new Preferencias();
    SavedData savedData = new SavedData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_direct);
        calcRest = findViewById(R.id.CalcdUtl);
        calcSab = findViewById(R.id.SatdUtl);
        saldRest = findViewById(R.id.CalcsRest);
        semRest = findViewById(R.id.CalcdRest);
        toolBar = findViewById(R.id.toolbar);
        contSald = findViewById(R.id.cartSald);
        setSupportActionBar(toolBar);

        changeFont();
//        savedData.refresh();
        recuperar();

        calcRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSV, 0);
                SharedPreferences sharedPreferencesS = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSQ, 0);
                SharedPreferences sharedPreferencesT = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSVAL, 0);
                SharedPreferences sharedPreferencesD = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_DIA, 0);
                String valorUsu = sharedPreferences.getString("valor", "no data");
                String quantUsu = sharedPreferencesS.getString("quant", "no data");
                String saldoUsu = sharedPreferencesT.getString("saldo", "no data");
                String diaUsu = sharedPreferencesD.getString("dia", "no data");
                if ( valorUsu.equals("no data") || quantUsu.equals("no data") ||
                saldoUsu.equals("no data") || diaUsu.equals("no data")){
                    Toast.makeText(getApplicationContext(), getString(R.string.please), Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(getApplicationContext(), SecondActivity.class));
                }
            }
        });
        calcSab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSV, 0);
                SharedPreferences sharedPreferencesS = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSQ, 0);
                SharedPreferences sharedPreferencesT = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSVAL, 0);
                SharedPreferences sharedPreferencesD = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_DIA, 0);
                String valorUsu = sharedPreferences.getString("valor", "no data");
                String quantUsu = sharedPreferencesS.getString("quant", "no data");
                String saldoUsu = sharedPreferencesT.getString("saldo", "no data");
                String diaUsu = sharedPreferencesD.getString("dia", "no data");
                if ( valorUsu.equals("no data") || quantUsu.equals("no data") ||
                        saldoUsu.equals("no data") || diaUsu.equals("no data")){
                    Toast.makeText(getApplicationContext(), getString(R.string.please), Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(getApplicationContext(), SabadoDiaUtil.class));
                }
            }
        });
        saldRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSV, 0);
                SharedPreferences sharedPreferencesS = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSQ, 0);
                SharedPreferences sharedPreferencesT = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSVAL, 0);
                SharedPreferences sharedPreferencesD = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_DIA, 0);
                String valorUsu = sharedPreferences.getString("valor", "no data");
                String quantUsu = sharedPreferencesS.getString("quant", "no data");
                String saldoUsu = sharedPreferencesT.getString("saldo", "no data");
                String diaUsu = sharedPreferencesD.getString("dia", "no data");
                if ( valorUsu.equals("no data") || quantUsu.equals("no data") ||
                        saldoUsu.equals("no data") || diaUsu.equals("no data")){
                    Toast.makeText(getApplicationContext(), getString(R.string.please), Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(getApplicationContext(), SaldoRest.class));
                }
            }
        });
        semRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //startActivity(new Intent(getApplicationContext(), Manutencao.class));
               startActivity(new Intent(getApplicationContext(), Personalized.class));
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
                Toast.makeText(getApplicationContext(), "Menu", Toast.LENGTH_SHORT).show();
        }
        switch (item.getItemId()){
            case R.id.about:
                startActivity(new Intent(getApplicationContext(), InfoApplication.class));
        }
        switch (item.getItemId()){
            case R.id.savedData:
                startActivity(new Intent(getApplicationContext(), SavedData.class));
        }
        switch (item.getItemId()){
            case R.id.preferencias:
                startActivity(new Intent(getApplicationContext(), Preferencias.class));
        }
        switch (item.getItemId()){
            case R.id.bug_reporter:
                startActivity(new Intent(getApplicationContext(), BugReport.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeFont(){
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this,"fonts/Roboto-Light.ttf", true);
    }

    public void recuperar() {
        SharedPreferences sharedPreferences = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSV, 0);
        SharedPreferences sharedPreferencesS = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSQ, 0);
        SharedPreferences sharedPreferencesT = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSVAL, 0);
        SharedPreferences sharedPreferencesD = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_DIA, 0);
        String valorUsu = sharedPreferences.getString("valor", "no data");
        String quantUsu = sharedPreferencesS.getString("quant", "no data");
        String saldoUsu = sharedPreferencesT.getString("saldo", "no data");
        String diaUsu = sharedPreferencesD.getString("dia", "no data");
        Double valorUsuDb = Double.parseDouble(valorUsu);
        Double quantUsuDb = Double.parseDouble(quantUsu);
        Double saldoUsuDb = Double.parseDouble(saldoUsu);
        Integer diaUsuInt = Integer.parseInt(diaUsu);
        if (valorUsu.equals("no data") || quantUsu.equals("no data") ||
                saldoUsu.equals("no data") || diaUsu.equals("no data")) {
            Toast.makeText(getApplicationContext(), "Nenhum dado para ser carregado!", Toast.LENGTH_LONG).show();
        } else {
            double resposta = preferencias.diaReturn(diaUsuInt, saldoUsuDb, quantUsuDb, valorUsuDb);
            String s = String.valueOf(resposta);
            contSald.setText("R$ " + s);
        }
    }
}
