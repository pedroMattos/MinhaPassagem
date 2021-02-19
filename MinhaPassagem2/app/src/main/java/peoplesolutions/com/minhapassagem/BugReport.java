package peoplesolutions.com.minhapassagem;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.Random;
import me.anwarshahriar.calligrapher.Calligrapher;
public class BugReport extends AppCompatActivity {

    private Button sendImg, btnSend;
    private ImageView infoAplication;
    private TextInputLayout layoutEditBug, layoutEditTitle;
    private EditText msgBug, titleBug;
    private Toolbar toolbar;
    private Uri selectedImage;
    private AlertDialog.Builder alertDialog;
    final private static int REQUEST_CODE = 1;
    final private static int PERMISSION_REQUEST = 2;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference dataBaseSendBug = databaseReference.child("BugReport");
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();
    ContCalendar contCalendar = new ContCalendar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug_report);
        sendImg = findViewById(R.id.btnSendImage);
        btnSend = findViewById(R.id.btnSend);
        toolbar = findViewById(R.id.toolbar);
        layoutEditBug = findViewById(R.id.layoutEditBug);
        layoutEditTitle = findViewById(R.id.layoutEditTitle);
        titleBug = findViewById(R.id.titleBug);
        msgBug = findViewById(R.id.msgBug);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Reportar Bug");

        userPermission();
        changeFont();

        sendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recuperImg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(recuperImg, REQUEST_CODE);
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarPDatabase(dataBaseSendBug, msgBug, titleBug);
            }
        });

    }

    private void uploadImage(int numR) {
        titleBug = findViewById(R.id.titleBug);
        if ( selectedImage != null ) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Enviando...");
            progressDialog.show();
            StorageReference ref = storageReference.child("Titulo "+titleBug.getText().toString()+" Id: "+numR);
            ref.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Erro ao Enviar a imagem!"+ e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage(""+ (int) progress+"%");
                        }
                    });

        }

    }

    public void enviarPDatabase(DatabaseReference dataBaseSendBug, EditText msgBug, EditText titleBug) {
        try {
            String mensagem = msgBug.getText().toString();
            String titulo = titleBug.getText().toString();
            String versao = getString(R.string.appVersion);
            Random random = new Random();
            int numRandom = random.nextInt(9999);
            if (msgBug.getText().toString().isEmpty() || titleBug.getText().toString().isEmpty()) {
                emptyError();
            } else {
                dataBaseSendBug.child(String.valueOf(numRandom)).setValue("Título: " + titulo + " Conteúdo: " + mensagem
                        + " Versão do app: " + versao + " Data: " + contCalendar.datDia + "/" + contCalendar.month);
                uploadImage(numRandom);
                Toast.makeText(getApplicationContext(), "Obrigado pela colaboração! Bug enviado!", Toast.LENGTH_LONG).show();
                msgBug.setText("");
                titleBug.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emptyError() {
        if (msgBug.getText().toString().isEmpty()) {
            layoutEditBug.setErrorEnabled(true);
            layoutEditBug.setError(getString(R.string.obrigatoryFiled));
        } else {
            layoutEditBug.setErrorEnabled(false);
        }
        if (titleBug.getText().toString().isEmpty()) {
            layoutEditTitle.setErrorEnabled(true);
            layoutEditTitle.setError(getString(R.string.obrigatoryFiled));
        } else {
            layoutEditTitle.setErrorEnabled(false);
        }
    }

    @Override
    protected void onStart() {
        alertDialog = new AlertDialog.Builder(BugReport.this);
        alertDialog.setTitle(R.string.bugReport);
        alertDialog.setMessage(R.string.textBugReport);
        alertDialog.setCancelable(true);
        alertDialog.create();
        alertDialog.show();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), MenuDirect.class));
        }
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
                Toast.makeText(getApplicationContext(), "Bugs", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        infoAplication = findViewById(R.id.infoAplication);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
            infoAplication.setImageBitmap(thumbnail);
            Toast.makeText(getApplicationContext(), "Imagem adicionada com sucesso!", Toast.LENGTH_LONG).show();
        }
    }

    public void userPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else { }
            return;
        }
    }
    public void changeFont(){
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this,"fonts/Roboto-Light.ttf", true);
    }
}