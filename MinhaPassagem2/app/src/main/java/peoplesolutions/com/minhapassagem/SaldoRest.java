package peoplesolutions.com.minhapassagem;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

import me.anwarshahriar.calligrapher.Calligrapher;

public class SaldoRest extends AppCompatActivity {
    private Button calc, infopUsu;
    private EditText recValue, recQuant, quantiPassagem;
    private TextView resp;
    private TextInputLayout layoutV, layoutP, layoutC;
    private AlertDialog.Builder alertDialog;
    public Toolbar toolbar;
    private Switch ativarPrefs;
    Preferencias preferencias = new Preferencias();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo_rest);
        calc = findViewById(R.id.buttonCalc);
        recQuant = findViewById(R.id.passQuant);
        recValue = findViewById(R.id.passValue);
        infopUsu = findViewById(R.id.infoActivity);
        resp = findViewById(R.id.result);
        quantiPassagem = findViewById(R.id.quantpassagem);
        layoutV = findViewById(R.id.layoutEditV);
        layoutC = findViewById(R.id.layoutEditC);
        layoutP = findViewById(R.id.layoutEditP);
        toolbar = findViewById(R.id.toolbar);
        ativarPrefs = findViewById(R.id.prefsAtiv);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Saldo restante");

        Locale mLocale = new Locale("pt", "BR");
        recValue.addTextChangedListener(new MoneyTextWatcher(recValue, mLocale));
        quantiPassagem.addTextChangedListener(new MoneyTextWatcher(quantiPassagem, mLocale));

        changeFont();

        ativarPrefs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ativarPrefs.isChecked() == true){
                    recuperar();
                }else{
                    recQuant.setText("");
                }

            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verifica se o campo de edittext esta vazio
                String textDgit = recValue.getText().toString();
                String text2Digit = recQuant.getText().toString();
                String text3Digit = quantiPassagem.getText().toString();
                if (textDgit.isEmpty() || text2Digit.isEmpty() || text3Digit.isEmpty()) {
                    //mensagem de erro
                    emptyError();
                    //se nao estiver ele continua o programa
                }else{
                    recValue = findViewById(R.id.passValue);
                    quantiPassagem = findViewById(R.id.quantpassagem);
                    MoneyTextWatcher moneyTextWatcher = new MoneyTextWatcher(recValue);
                    MoneyTextWatcher moneyTextWatcher2 = new MoneyTextWatcher(quantiPassagem);
                    double sald = moneyTextWatcher.parseMaskToDouble(recValue);
                    double val = moneyTextWatcher2.parseMaskToDouble(quantiPassagem);
                    double saldDb = sald/100;
                    double valDb = val/100;
                    String strSald = String.valueOf(saldDb);
                    String strValor = String.valueOf(valDb);
                    String textInpV = strValor;
                    String textInpQ = recQuant.getText().toString();
                    String textInpQu = strSald;
                    Double ValorInpQu = Double.parseDouble(textInpQu);
                    Double ValorInpV = Double.parseDouble(textInpV);
                    Double ValorInpQ = Double.parseDouble(textInpQ);

                    double passagens = ValorInpQu/ValorInpV;
                    double values = passagens/ValorInpQ;
                    int valuesInt = (int) values;
                    int passagensQ = (int) passagens;
                    if (valuesInt <= 3){
                        resp.setText("Você possui "+passagensQ+" passagens. Considere fazer uma recarga em breve! "
                                +valuesInt+" dias restantes.");
                    }else if(valuesInt < 7){
                        resp.setText("Você possui "+passagensQ+" passagens. Suas passagens irão durar menos de uma semana!");

                    }else{
                        resp.setText("Você possui "+passagensQ+" passagens e irão durar "+valuesInt+" dias.");
                    }
                }
            }
        });
        infopUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(SaldoRest.this);
                alertDialog.setTitle("Calcular por saldo restante");
                alertDialog.setMessage(R.string.alertsaldorest);
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });
                alertDialog.create();
                alertDialog.show();
            }
        });
    }

    public void emptyError(){
        if(recQuant.getText().toString().isEmpty()){
            layoutP.setErrorEnabled(true);
            layoutP.setError(getString(R.string.errorWarning));
        }else{
            layoutV.setErrorEnabled(false);
        }
        if(recValue.getText().toString().isEmpty()){
            layoutV.setErrorEnabled(true);
            layoutV.setError(getString(R.string.errorWarning));
        }else{
            layoutP.setErrorEnabled(false);
        }
        if(quantiPassagem.getText().toString().isEmpty()){
            layoutC.setErrorEnabled(true);
            layoutC.setError(getString(R.string.errorWarning));
        }else{
            layoutC.setErrorEnabled(false);
        }
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
                startActivity(new Intent(getApplicationContext(), MenuDirect.class));
        }
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), MenuDirect.class));
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
            quantiPassagem.setText(valorUsu+"0");
            recQuant.setText(quantUsu);
            recValue.setText(saldoUsu+"0");
        } else {
            recValue.setText("Dados não salvos!");
            recQuant.setText("Dados não salvos!");
            quantiPassagem.setText("Dados não salvos!");
        }
    }

    public void changeFont(){
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this,"fonts/Roboto-Light.ttf", true);
    }
}