package peoplesolutions.com.minhapassagem;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdsmdg.harjot.longshadows.LongShadowsFrameLayoutWrapper;
import com.sdsmdg.harjot.longshadows.LongShadowsImageView;

public class SplashScreen extends AppCompatActivity {
    private ImageView imageSplash;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        textView = findViewById(R.id.textView);
        imageSplash = findViewById(R.id.imageSplash);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_alpha);
        imageSplash.startAnimation(animation);
        textView.startAnimation(animation);
        //gerencia a tela para ficar em tela cheia
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //inicia a nova acticivty ao finalizar
                startActivity(new Intent(getApplicationContext(), Release_Notes.class));
                //impede que seja iniciada nvoamente
                finish();
            }
        }, 3000);
    }
}
