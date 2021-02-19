package peoplesolutions.com.minhapassagem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import me.anwarshahriar.calligrapher.Calligrapher;
import peoplesolutions.com.minhapassagem.R;
import peoplesolutions.com.minhapassagem.SecondActivity;

public class MainActivity extends Activity {
    private Button mudarActivity;
    private TextView textType;
    Preferencias preferencias = new Preferencias();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Letter letra = new Letter();
        Letter textview = new Letter();
        textview.setLetterSpacings(-0.05);
        letra.setLetterSpacings(0.5);
        textType = findViewById(R.id.textName);
        mudarActivity = findViewById(R.id.changeActivity);
        mudarActivity.setLetterSpacing((float) letra.getLetterSpacings());


        String mensagem = getIntent().getStringExtra("mensagem");
        textType.setText(mensagem);

        changeFont();

        textType.setLetterSpacing((float) textview.getLetterSpacings());
        mudarActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MenuDirect.class));
            }
        });
    }

    public void changeFont(){
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this,"fonts/Roboto-Light.ttf", true);
    }
}