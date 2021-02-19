package peoplesolutions.com.minhapassagem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.anwarshahriar.calligrapher.Calligrapher;

public class SavedData extends AppCompatActivity {

    private TextView unitPass, quantPass, cartSald, contSald;
    private ImageView refreshBtn;
    public Toolbar toolBar;
    Preferencias preferencias = new Preferencias();
    ContCalendar contCalendar = new ContCalendar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_data);
        unitPass = findViewById(R.id.unitPass);
        quantPass = findViewById(R.id.quantPass);
        cartSald = findViewById(R.id.cartSald);
        contSald = findViewById(R.id.contSald);
        toolBar = findViewById(R.id.toolbar);
        refreshBtn = findViewById(R.id.refreshBtn);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Dados do Usuário");

        recuperar();
        changeFont();

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                startActivity(new Intent(getApplicationContext(), MenuDirect.class));
        }
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), Preferencias.class));
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
            case R.id.bug_reporter:
                startActivity(new Intent(getApplicationContext(), BugReport.class));
        }
        switch (item.getItemId()){
            case R.id.preferencias:
                startActivity(new Intent(getApplicationContext(), Preferencias.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void recuperar(){
        SharedPreferences sharedPreferences = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSV, 0);
        SharedPreferences sharedPreferencesS = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSQ, 0);
        SharedPreferences sharedPreferencesT = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSVAL, 0);
        if(sharedPreferences.contains("valor") || sharedPreferencesS.contains("quant") || sharedPreferencesT.contains("saldo")){
            String valorUsu = sharedPreferences.getString("valor", "no data");
            String quantUsu = sharedPreferencesS.getString("quant", "no data");
            String saldoUsu = sharedPreferencesT.getString("saldo", "no data");
            unitPass.setText("R$ "+valorUsu);
            quantPass.setText(quantUsu+" /Dia");
            cartSald.setText("R$ "+saldoUsu);
        } else {
            unitPass.setText("Dados não salvos!");
            quantPass.setText("Dados não salvos!");
            cartSald.setText("Dados não salvos!");
        }

    }

    public void changeFont(){
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this,"fonts/Roboto-Light.ttf", true);
    }

    public void refresh(){
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
                saldoUsu.equals("no data") || diaUsu.equals("no data")){
            Toast.makeText(getApplicationContext(), "Nenhum dado para ser carregado!", Toast.LENGTH_LONG).show();
        } else{
            double resposta = preferencias.diaReturn(diaUsuInt, saldoUsuDb, quantUsuDb, valorUsuDb);
            String s = String.valueOf(resposta);
            contSald.setText("R$ "+ s);
        }
    }
}
