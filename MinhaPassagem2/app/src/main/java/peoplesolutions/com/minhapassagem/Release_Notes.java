package peoplesolutions.com.minhapassagem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Release_Notes extends AppCompatActivity {

    private Button prox;
    private AlertDialog.Builder alertDialog;
    private ProgressBar progressBar;
    private TextView mudanca1, mudanca2, mudanca3, mudanca4, mudanca5, mudanca6;
    private TextView proxM1, proxM2, proxM3;
    private TextView bf1, bf2, bf3, bf4;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference databaseReferenceMudanca = databaseReference.child("ReleaseNotes").child("Mudanca");
    private DatabaseReference databaseReferenceProxM = databaseReference.child("ReleaseNotes").child("ProxMudanca");
    private DatabaseReference databaseReferenceBf = databaseReference.child("ReleaseNotes").child("BugFix");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release__notes);
        prox = findViewById(R.id.buttonPross);
        progressBar = findViewById(R.id.progressBar);
        changeFont();
        databaseReferenceMudanca.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mudanca1 = findViewById(R.id.mudanca1);
                mudanca2 = findViewById(R.id.mudanca2);
                mudanca3 = findViewById(R.id.mudanca3);
                mudanca4 = findViewById(R.id.mudanca4);
                mudanca5 = findViewById(R.id.mudanca5);
                mudanca6 = findViewById(R.id.mudanca6);
                mudanca1.setText(dataSnapshot.child("001").toString().replace("DataSnapshot { key = 001, value = ", "")
                        .replace("}", ""));
                mudanca2.setText(dataSnapshot.child("002").toString().replace("DataSnapshot { key = 002, value = ", "")
                        .replace("}", ""));
                mudanca3.setText(dataSnapshot.child("003").toString().replace("DataSnapshot { key = 003, value = ", "")
                        .replace("}", ""));
                mudanca4.setText(dataSnapshot.child("004").toString().replace("DataSnapshot { key = 004, value = ", "")
                        .replace("}", ""));
                mudanca5.setText(dataSnapshot.child("005").toString().replace("DataSnapshot { key = 005, value = ", "")
                        .replace("}", ""));
                mudanca6.setText(dataSnapshot.child("006").toString().replace("DataSnapshot { key = 006, value = ", "")
                        .replace("}", ""));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        databaseReferenceProxM.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                proxM1 = findViewById(R.id.proxM1);
                proxM2 = findViewById(R.id.proxM2);
                proxM3 = findViewById(R.id.proxM3);
                proxM1.setText(dataSnapshot.child("001").toString().replace("DataSnapshot { key = 001, value = ", "")
                        .replace("}", ""));
                proxM2.setText(dataSnapshot.child("002").toString().replace("DataSnapshot { key = 002, value = ", "")
                        .replace("}", ""));
                proxM3.setText(dataSnapshot.child("003").toString().replace("DataSnapshot { key = 003, value = ", "")
                        .replace("}", ""));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReferenceBf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bf1 = findViewById(R.id.bf1);
                bf2 = findViewById(R.id.bf2);
                bf3 = findViewById(R.id.bf3);
                bf4 = findViewById(R.id.bf4);
                bf1.setText(dataSnapshot.child("001").toString().replace("DataSnapshot { key = 001, value = ", "")
                        .replace("}", ""));
                bf2.setText(dataSnapshot.child("002").toString().replace("DataSnapshot { key = 002, value = ", "")
                        .replace("}", ""));
                bf3.setText(dataSnapshot.child("003").toString().replace("DataSnapshot { key = 003, value = ", "")
                        .replace("}", ""));
                bf4.setText(dataSnapshot.child("004").toString().replace("DataSnapshot { key = 004, value = ", "")
                        .replace("}", ""));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        prox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
    public void changeFont(){
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this,"fonts/Roboto-Light.ttf", true);
    }
}
