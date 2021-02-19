package peoplesolutions.com.minhapassagem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.SupportActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Preferencias extends AppCompatActivity {

    private EditText passV, passQ, passval;
    private Button saveButton, visuPrefs;
    private ImageView erase;
    public Toolbar toolBar;
    private TextInputLayout layoutV, layoutP, layoutC;
    public static final String PREFERENCIAS_DO_USUARIO_PASSV = "";
    public static final String PREFERENCIAS_DO_USUARIO_PASSQ = "";
    public static final String PREFERENCIAS_DO_USUARIO_PASSVAL = "";
    public static final String PREFERENCIAS_DO_USUARIO_DIA = "";
    ContCalendar calendar = new ContCalendar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        passV = findViewById(R.id.passValue);
        passQ = findViewById(R.id.passQuant);
        passval = findViewById(R.id.valPass);
        erase = findViewById(R.id.erase);
        layoutV = findViewById(R.id.layoutEditV);
        layoutC = findViewById(R.id.layoutEditC);
        layoutP = findViewById(R.id.layoutEditP);
        saveButton = findViewById(R.id.botaoSalvar);
        visuPrefs = findViewById(R.id.visuPrefs);
        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        Locale mLocale = new Locale("pt", "BR");
        passV.addTextChangedListener(new MoneyTextWatcher(passV, mLocale));
        passval.addTextChangedListener(new MoneyTextWatcher(passval, mLocale));

        changeFont();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passValS = passval.getText().toString();
                String passVS = passV.getText().toString();
                String passQS = passQ.getText().toString();
                if ( passValS.isEmpty() || passVS.isEmpty()
                || passQS.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Preencha os Campos", Toast.LENGTH_SHORT).show();
                } else {
                    userPrefs();
                }
            }
        });

        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearUserPrefs();
            }
        });


        visuPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SavedData.class));
            }
        });
    }

    public double diaReturn(int diaRest, double sald, double quant, double pass) {
        int aux;
        double saldoFinal = 0;
        if (diaRest < calendar.datDia) {
            aux = calendar.datDia - diaRest;
            double valorPassagemDia = quant * pass;
            double valorPassagemSub = aux * valorPassagemDia;
            saldoFinal = sald - valorPassagemSub;
        } else {

            saldoFinal = sald;

        }
        return saldoFinal;
    }

    public void userPrefs() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIAS_DO_USUARIO_PASSV, 0);
        SharedPreferences sharedPreferencesS = getSharedPreferences(PREFERENCIAS_DO_USUARIO_PASSQ, 0);
        SharedPreferences sharedPreferencesT = getSharedPreferences(PREFERENCIAS_DO_USUARIO_PASSVAL, 0);
        SharedPreferences sharedPreferencesD = getSharedPreferences(PREFERENCIAS_DO_USUARIO_DIA, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor editorS = sharedPreferencesS.edit();
        SharedPreferences.Editor editorT = sharedPreferencesT.edit();
        SharedPreferences.Editor editorD = sharedPreferencesD.edit();
        passV = findViewById(R.id.passValue);
        passval = findViewById(R.id.valPass);
        MoneyTextWatcher moneyTextWatcher = new MoneyTextWatcher(passV);
        MoneyTextWatcher moneyTextWatcher2 = new MoneyTextWatcher(passval);
        double val = moneyTextWatcher.parseMaskToDouble(passV);
        double sald = moneyTextWatcher2.parseMaskToDouble(passval);
        double valorDb = val/100;
        double saldDb = sald/100;
        String strValor = String.valueOf(valorDb);
        String strSald = String.valueOf(saldDb);
        editor.putString("valor", strValor);
        editorS.putString("quant", passQ.getText().toString());
        editorT.putString("saldo", strSald);
        editorD.putString("dia", String.valueOf(calendar.datDia));
        editor.commit();
        editorS.commit();
        editorT.commit();
        editorD.commit();
        Toast.makeText(getApplicationContext(), "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();
    }

    public void clearUserPrefs() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIAS_DO_USUARIO_PASSV, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(getApplicationContext(), "Dado Apagado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(getApplicationContext(), MenuDirect.class));
        }
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(getApplicationContext(), InfoApplication.class));
        }
        switch (item.getItemId()) {
            case R.id.savedData:
                startActivity(new Intent(getApplicationContext(), SavedData.class));
        }
        switch (item.getItemId()) {
            case R.id.bug_reporter:
                startActivity(new Intent(getApplicationContext(), BugReport.class));
        }
        switch (item.getItemId()) {
            case R.id.preferencias:
                startActivity(new Intent(getApplicationContext(), ForTest.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeFont() {
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "fonts/Roboto-Light.ttf", true);
    }

    public void emptyError() {
        if (passQ.getText().toString().isEmpty()) {
            layoutP.setErrorEnabled(true);
            layoutP.setError(getString(R.string.errorWarning));
        } else {
            layoutP.setErrorEnabled(false);
        }
        if (passval.getText().toString().isEmpty()) {
            layoutV.setErrorEnabled(true);
            layoutV.setError(getString(R.string.errorWarning));
        } else {
            layoutV.setErrorEnabled(false);
        }
        if (passV.getText().toString().isEmpty()) {
            layoutC.setErrorEnabled(true);
            layoutC.setError(getString(R.string.errorWarning));
        } else {
            layoutC.setErrorEnabled(false);
        }
    }
}
