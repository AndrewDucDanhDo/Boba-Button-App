package andrewdddo.gmail.com.bobabutton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import andrewdddo.gmail.com.bobabutton.R;
import andrewdddo.gmail.com.bobabutton.ShopActivity;
import andrewdddo.gmail.com.bobabutton.SimpleAnimationListener;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvPoints;
    private int totalPoints = 0;
    private int currentPoints = 0;
    private int clickPoints = 0;
    private TextView tvcps;
    private int clicks = 0;
    private int cps = 0;
    private int totalAutoClicks = 1;
    private BobaCounter bobaCounter = new BobaCounter();
    private Typeface otf;
    private Random random;

    private int upperboundY = 500;
    private int lowerboundY = 200;
    private int upperboundX = 320;
    private int lowerboundX = 160;

    RelativeLayout homeActivityLayout;
    AnimationDrawable animationDrawable;
    Animation rotateAnimation;
    ImageView whiteBacklightImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        tvPoints = findViewById(R.id.tvPoints);
        tvPoints.setTypeface(otf);
        otf = Typeface.createFromAsset(getAssets(), "fonts/bianca.otf");
        random = new Random();

        tvcps = findViewById(R.id.tvcps);

        homeActivityLayout = (RelativeLayout) findViewById(R.id.homeActivityLayout);
        animationDrawable = (AnimationDrawable) homeActivityLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();

        whiteBacklightImageView = (ImageView) findViewById(R.id.imgWhiteBacklight);

        rotateAnimation();


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgBoba) {
            Animation a = AnimationUtils.loadAnimation(this, R.anim.boba_animation);
            a.setAnimationListener(new SimpleAnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    bobaClick();
                }
            });
            v.startAnimation(a);
        } else if (v.getId() == R.id.btnShop) {
            ImageButton button = (ImageButton) findViewById(R.id.btnShop);
            Animation a = AnimationUtils.loadAnimation(this, R.anim.shop_animation);
            button.startAnimation(a);
            openShopActivity();
        } else if (v.getId() == R.id.btnSignOut) {
            FirebaseAuth.getInstance().signOut();
            Intent intToLogin = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intToLogin);
        }
    }

    private void bobaClick() {
        totalPoints++;
        clicks++;
        tvPoints.setText(Integer.toString(totalPoints));
        showToast(R.string.clicked);
    }

    private void showToast(int stringID) {
        final Toast toast = new Toast(this);
        toast.setGravity(Gravity.BOTTOM|Gravity.LEFT, random.nextInt(upperboundX-lowerboundX)+lowerboundX, random.nextInt(upperboundY-lowerboundY)+lowerboundY);
        toast.setDuration(Toast.LENGTH_SHORT);
        TextView textView = new TextView(this);
        textView.setText(stringID);
        textView.setTextSize(40f);
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(otf);
        toast.setView(textView);
        CountDownTimer toastCountDown;

        /*toastCountDown = new CountDownTimer(500,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                toast.show();
            }

            @Override
            public void onFinish() {
                toast.cancel();
            }
        };*/
        toast.show();
        //toastCountDown.start();
    }

    private void update() {
        totalPoints += cps + totalAutoClicks;
        clickPoints = totalPoints - currentPoints;
        cps = clicks + clickPoints;

        tvcps.setText(Integer.toString(cps));
        tvPoints.setText(Integer.toString(totalPoints));

        currentPoints = totalPoints;
        clicks = 0;
        clickPoints = 0;
        cps = 0;
    }

    public class BobaCounter {
        private Timer timer;

        public BobaCounter(){
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask(){
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            update();
                        }
                    });
                }
            },1000,1000);
        }
    }

    public void openShopActivity() {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }

    private void rotateAnimation() {
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        whiteBacklightImageView.startAnimation(rotateAnimation);
    }
}
