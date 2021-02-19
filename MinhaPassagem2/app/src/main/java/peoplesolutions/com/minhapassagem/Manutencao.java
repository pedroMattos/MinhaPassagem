package peoplesolutions.com.minhapassagem;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Manutencao extends AppCompatActivity {

    private TextView maintence;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manutencao);
        toolbar = findViewById(R.id.toolbar);
        maintence = findViewById(R.id.textMaintence);
        setSupportActionBar(toolbar);

        changeFont();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Em Manutenção");
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
    public void changeFont(){
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this,"fonts/Roboto-Light.ttf", true);
    }
}
