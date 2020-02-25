package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import static id.putraprima.skorbola.MainActivity.AWAYIMAGE_KEY;
import static id.putraprima.skorbola.MainActivity.AWAY_KEY;
import static id.putraprima.skorbola.MainActivity.HOMEIMAGE_KEY;
import static id.putraprima.skorbola.MainActivity.HOME_KEY;

public class MatchActivity extends AppCompatActivity {

    public static final String STATUS_KEY = "status";
    
    private TextView homeScoreView;
    private TextView awayScoreView;
    private TextView hometxt;
    private TextView awaytxt;
    private ImageView homeLogo;
    private ImageView awayLogo;

    public int homeScore = 0;
    public int awayScore = 0;
    private Uri uri, uri2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        hometxt = findViewById(R.id.txt_home);
        awaytxt = findViewById(R.id.txt_away);
        homeScoreView = findViewById(R.id.score_home);
        awayScoreView = findViewById(R.id.score_away);
        homeLogo = findViewById(R.id.home_logo);
        awayLogo = findViewById(R.id.away_logo);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            hometxt.setText(extras.getString(HOME_KEY));
            awaytxt.setText(extras.getString(AWAY_KEY));
            uri = Uri.parse(extras.getString(HOMEIMAGE_KEY));
            uri2 = Uri.parse(extras.getString(AWAYIMAGE_KEY));
            Bitmap bitmap = null;
            Bitmap bitmap2 = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri2);
            }catch (IOException e){
                e.printStackTrace();
            }
            homeLogo.setImageBitmap(bitmap);
            awayLogo.setImageBitmap(bitmap2);

        }

        //TODO
        //1.Menampilkan detail match sesuai data dari main activity
        //2.Tombol add score menambahkan satu angka dari angka 0, setiap kali di tekan
        //3.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang ke ResultActivity, jika seri di kirim text "Draw"
    }

    public void handleCek(View view) {
        String status = null;
        if (homeScore == awayScore ){
            status = "Name of Winning Draw";
        }else if (homeScore > awayScore){
            status = "Name of Winning "+hometxt.getText().toString();
        }else if (homeScore < awayScore){
            status = "Name of Winning "+awaytxt.getText().toString();
        }
        Intent i = new Intent(this, ResultActivity.class);
        i.putExtra(STATUS_KEY, status);
        startActivity(i);
    }

    public void handleAwayScore(View view) {
        awayScore++;
        awayScoreView.setText(String.valueOf(awayScore));
    }

    public void handleHomeScore(View view) {
        homeScore++;
        homeScoreView.setText(String.valueOf(homeScore));
    }


}
