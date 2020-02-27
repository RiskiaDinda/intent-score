package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();

    public static final String HOME_KEY = "homeTeam";
    public static final String AWAY_KEY = "awayTeam";
    public static final String HOMEIMAGE_KEY = "homeLogo";
    public static final String AWAYIMAGE_KEY = "awayLogo";

    private EditText homeInput;
    private EditText awayInput;
    private ImageView homeImage;
    private ImageView awayImage;
    private Uri imageUri = null;
    private Uri imageUri2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeInput = findViewById(R.id.home_team);
        awayInput = findViewById(R.id.away_team);
        homeImage = findViewById(R.id.home_logo);
        awayImage = findViewById(R.id.away_logo);
        //TODO
        //Fitur Main Activity
        //1. Validasi Input Home Team
        //2. Validasi Input Away Team
        //3. Ganti Logo Home Team
        //4. Ganti Logo Away Team
        //5. Next Button Pindah Ke MatchActivity
    }

    public void handleNext(View view) {
        String homeTeam = homeInput.getText().toString();
        String awayTeam = awayInput.getText().toString();

        Intent i = new Intent(this, MatchActivity.class);

        if (homeTeam.isEmpty()){
            homeInput.setError("Isi Nama HomeTeam dahulu");
        }else if (awayTeam.isEmpty()){
            awayInput.setError("Isi Nama AwayTeam dahulu");
        }else if (imageUri == null){
            Toast.makeText(this, "Pilih gambar dahulu", Toast.LENGTH_SHORT).show();
            handleChangeHomeImage(view);
        }else if (imageUri2 == null){
            Toast.makeText(this, "Pilih gambar dahulu", Toast.LENGTH_SHORT).show();
            handleChangeAwayImage(view);
        }else {
            i.putExtra(HOME_KEY, homeTeam);
            i.putExtra(AWAY_KEY, awayTeam);
            i.putExtra(HOMEIMAGE_KEY, imageUri.toString());
            i.putExtra(AWAYIMAGE_KEY, imageUri2.toString());
            startActivity(i);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            return;
        }

        if (requestCode == 1){
            if (data != null){
                try{
                    imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    homeImage.setImageBitmap(bitmap);
                }catch (IOException e){
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }else if (requestCode == 2){
            if (data != null){
                try{
                    imageUri2 = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri2);
                    awayImage.setImageBitmap(bitmap);
                }catch (IOException e){
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void handleChangeHomeImage(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }

    public void handleChangeAwayImage(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 2);
    }
}
