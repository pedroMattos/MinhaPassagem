package peoplesolutions.com.minhapassagem;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
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

public class SecondActivity extends AppCompatActivity {
    private Button buttonCalcu, infopUsu;
    private TextView textResult, valDia, valPassage, func;
    private EditText inpC, inpQ;
    private TextInputLayout layoutV, layoutP;
    private AlertDialog.Builder alertDialog;
    public Toolbar toolbar;
    private Switch ativarPrefs;
    Preferencias preferencias = new Preferencias();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textResult = findViewById(R.id.result);
        inpC = findViewById(R.id.passValue);
        inpQ = findViewById(R.id.passQuant);
        buttonCalcu = findViewById(R.id.buttonCalc);
        infopUsu = findViewById(R.id.infoActivity);
        layoutP = findViewById(R.id.layoutEditP);
        layoutV = findViewById(R.id.layoutEditV);
        valDia = findViewById(R.id.valDia);
        ativarPrefs = findViewById(R.id.prefsAtiv);
        valPassage = findViewById(R.id.valPasse);
        func = findViewById(R.id.func);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Locale mLocale = new Locale("pt", "BR");
        inpC.addTextChangedListener(new MoneyTextWatcher(inpC, mLocale));

        changeFont();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Dias Úteis");

        SharedPreferences sharedPreferences = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSV, 0);
        SharedPreferences sharedPreferencesS = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSQ, 0);
        SharedPreferences sharedPreferencesT = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_PASSVAL, 0);
        SharedPreferences sharedPreferencesD = getSharedPreferences(preferencias.PREFERENCIAS_DO_USUARIO_DIA, 0);
        final String valorUsu = sharedPreferences.getString("valor", "no data");
        String quantUsu = sharedPreferencesS.getString("quant", "no data");
        String saldoUsu = sharedPreferencesT.getString("saldo", "no data");
        String diaUsu = sharedPreferencesD.getString("dia", "no data");
        final Double valorUsuDb = Double.parseDouble(valorUsu);
        final Double quantUsuDb = Double.parseDouble(quantUsu);
        final Double saldoUsuDb = Double.parseDouble(saldoUsu);
        final Integer diaUsuInt = Integer.parseInt(diaUsu);
        double resposta = preferencias.diaReturn(diaUsuInt, saldoUsuDb, quantUsuDb, valorUsuDb);

        ativarPrefs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ativarPrefs.isChecked() == true){
                    recuperar();
                }else{
                    inpQ.setText("");
                }
            }
        });

        buttonCalcu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verifica se o campo de edittext esta vazio
                String textDgit = inpC.getText().toString();
                String text2Digit = inpQ.getText().toString();
                if (textDgit.isEmpty() || text2Digit.isEmpty()){
                    //mensagem de erro
                    emptyError();
                    //se nao estiver ele continua o programa
                }else{
                    ContCalendar calendario = new ContCalendar();
                    FormatDec formatar = new FormatDec();
                    int reDia = calendario.rDia;
                    int fSemana = calendario.wWeekend;
                    int restDia = reDia - fSemana;
                    inpC = findViewById(R.id.passValue);
                    MoneyTextWatcher moneyTextWatcher = new MoneyTextWatcher(inpC);
                    double val = moneyTextWatcher.parseMaskToDouble(inpC);
                    double valorDb = val/100;
                    String strValor = String.valueOf(valorDb);
                    String textInpV = strValor;
                    String textInpQ = inpQ.getText().toString();
                    Double ValorInpV = Double.parseDouble(textInpV);
                    Double ValorInpQ = Double.parseDouble(textInpQ);
                    if(calendario.weDia == 6 || calendario.weDia == 7){
                        restDia = restDia - 2;
                        double resposta = preferencias.diaReturn(diaUsuInt, saldoUsuDb, quantUsuDb, valorUsuDb);
                        double resultads = restDia * (ValorInpQ * ValorInpV);
                        final double novoValue = resultads - resposta;
                        String valueForm = formatar.FormatDecimal(novoValue);
                        if ( novoValue <= 0 ) {
                            textResult.setText("Você possui passagens suficientes até o fim do mês!");
                        } else {
                            textResult.setText("Precisa recarregar "+valueForm + " Reais");
                        }

                    }else{
                        double resposta = preferencias.diaReturn(diaUsuInt, saldoUsuDb, quantUsuDb, valorUsuDb);
                        double resultads = restDia * (ValorInpQ * ValorInpV);
                        final double novoValue = resultads - resposta;
                        String valueForm = formatar.FormatDecimal(novoValue);
                        if ( novoValue <= 0 ) {
                            textResult.setText("Você possui passagens suficientes até o fim do mês!");
                        } else {
                            textResult.setText("Precisa recarregar "+valueForm + " Reais");
                        }
                    }
                }
            }
        });


        infopUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(SecondActivity.this);
                alertDialog.setTitle("Calcular por dias úteis");
                alertDialog.setMessage(R.string.diasuteis);
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.create();
                alertDialog.show();
            }
        });

    }
    public void emptyError(){
        if(inpC.getText().toString().isEmpty()){
            layoutV.setErrorEnabled(true);
            layoutV.setError(getString(R.string.errorWarning));
        }else{
            layoutV.setErrorEnabled(false);
        }
        if(inpQ.getText().toString().isEmpty()){
            layoutP.setErrorEnabled(true);
            layoutP.setError(getString(R.string.errorWarning));
        }else{
            layoutP.setErrorEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

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
            inpC.setText(valorUsu+"0");
            inpQ.setText(quantUsu);
        } else {
            inpC.setText("Dados não salvos!");
            inpQ.setText("Dados não salvos!");
        }
    }
    public void changeFont(){
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this,"fonts/Roboto-Light.ttf", true);
    }
}