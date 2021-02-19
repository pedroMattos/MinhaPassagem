package peoplesolutions.com.minhapassagem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Personalized extends AppCompatActivity {
    private Button calc, drop, ter, seg;
    private AppCompatEditText recValue, recQuant;
    private TextView resp;
    private AlertDialog.Builder alertDialog;
    private HorizontalScrollView scrollContent;
    private TextInputLayout layoutV, layoutP;
    public Toolbar toolbar;
    private Switch ativarPrefs;
    Preferencias preferencias = new Preferencias();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalized);
        calc = findViewById(R.id.buttonCalc);
        recValue = findViewById(R.id.passValue);
        recQuant = findViewById(R.id.passQuant);
        resp = findViewById(R.id.result);
        layoutP = findViewById(R.id.layoutEditP);
        layoutV = findViewById(R.id.layoutEditV);
        seg = findViewById(R.id.seg);
        ter = findViewById(R.id.ter);
        toolbar = findViewById(R.id.toolbar);
        drop = findViewById(R.id.dropDown);
        scrollContent = findViewById(R.id.scrollContent);
        ativarPrefs = findViewById(R.id.prefsAtiv);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Personalizado");

        changeFont();
        caseButtons(seg);

        ativarPrefs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ativarPrefs.isChecked() == true){
                    recuperar();
                }else{
                }
            }
        });
        drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollContent = findViewById(R.id.scrollContent);
                scrollContent.setVisibility(View.VISIBLE);
            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textdigit = recValue.getText().toString();
                String textDigit2 = recQuant.getText().toString();
                if (textdigit.isEmpty() || textDigit2.isEmpty()){
                    emptyError();
                }else{
                    ContCalendar calendario = new ContCalendar();
                    FormatDec formatar = new FormatDec();
                    String textInpV = recValue.getText().toString();
                    String textInpQ = recQuant.getText().toString();
                    Double ValorInpV = Double.parseDouble(textInpV);
                    Double ValorInpQ = Double.parseDouble(textInpQ);

                    int reDia = calendario.rDia/5;
                    Integer restD = calendario.rDia%5;
                    double result = ValorInpQ * ValorInpV;
                    double valueFinal = reDia * result;
                    if(restD > 0){
                        double resultf = result * reDia;
                        double mDia = ValorInpQ/5;
                        double restDia = ValorInpQ%5;
                        if( restDia == 0 ){
                            String valueForm = formatar.FormatDecimal(valueFinal);
                            resp.setText("Precisa regarregar: "+valueForm+" Reais.");
                        }else if (restDia > 0){
                            double passpD = restDia + mDia;
                            double passvD = passpD * ValorInpV;
                            double valFalt = passvD * restD;
                            double vFinal = valFalt + resultf;
                            String valueForm = formatar.FormatDecimal(vFinal);
                            resp.setText("Precisa regarrar: "+valueForm+" Reais.");
                        }else{
                            double passvDia = mDia * restD;
                            double valFalt = passvDia * resultf;
                            double vfinal =  valFalt + resultf;
                            String valueForm = formatar.FormatDecimal(vfinal);
                            resp.setText("Precisa regarregar: "+valueForm+" Reais.");
                        }

                    }
                }
            }
        });

        /*infopUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(Personalized.this);
                alertDialog.setTitle("Personalizar");
                alertDialog.setMessage(R.string.alert_personalized);
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });
             alertDialog.create();
             alertDialog.show();

            }
        });*/

    }
    public void emptyError(){
        if(recValue.getText().toString().isEmpty()){
            layoutV.setErrorEnabled(true);
            layoutV.setError("Preencha este campo");
        }else{
            layoutV.setErrorEnabled(false);
        }
        if(recQuant.getText().toString().isEmpty()){
            layoutP.setErrorEnabled(true);
            layoutP.setError("Preencha este campo");
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
            recValue.setText(valorUsu);
        } else {
            recValue.setText("Dados não salvos!");
        }

    }
    public void changeFont(){
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this,"fonts/Roboto-Light.ttf", true);
    }

    /*public String addValue(String value) {
        Integer recs = Integer.parseInt(value);
        String testando = null;
        if (recs < 6) {
            int teste = recs + 1;
            recs = teste;
            testando = String.valueOf(recs);
        } else {

        }
        return testando;
    }

    public String remvValue(String value){
        Integer recs = Integer.parseInt(value);
        String testando = null;
        if ( recs > 0 ){
            int teste = recs - 1;
            recs = teste;
            testando = String.valueOf(recs);
        } else if (recs.equals("")){
            recs = 0;
            testando = String.valueOf(recs);
            Toast.makeText(getApplicationContext(), "Mínimo possível!", Toast.LENGTH_SHORT).show();
        }
        return testando;
    }*/

    public void caseButtons(Button item){
        switch (item.getId()){
            case R.id.seg:
                seg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Segunda", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.ter:
                Toast.makeText(getApplicationContext(), "Terça", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}