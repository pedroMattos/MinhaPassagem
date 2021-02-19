package peoplesolutions.com.minhapassagem;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Locale;

public class ForTest extends AppCompatActivity {
    Preferencias preferencias = new Preferencias();
    ContCalendar contCalendar = new ContCalendar();

    private Button botaoTeste;
    private EditText editTexto;
    private TextView textoTeste;

    private static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "Simplified Coding";
    private static final String CHANNEL_DESC = "Simplified Coding Notifications";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_test);
        botaoTeste = findViewById(R.id.buttonTest);
        textoTeste = findViewById(R.id.textTest);
        editTexto = findViewById(R.id.editTest);


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
        final double resposta = preferencias.diaReturn(diaUsuInt, saldoUsuDb, quantUsuDb, valorUsuDb);
        String s = String.valueOf(resposta);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        botaoTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( resposta <= 0 ){
                    displayNotification();
                }
            }
        });
    }

    public void displayNotification(){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setContentTitle("Alerta!")
                        .setContentText("Você precisa recarregar seu cartão urgentemente!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(this);
        mNotificationMgr.notify(1, mBuilder.build());

    }
}

