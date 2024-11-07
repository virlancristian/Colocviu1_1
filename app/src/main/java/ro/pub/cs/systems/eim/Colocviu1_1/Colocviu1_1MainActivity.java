package ro.pub.cs.systems.eim.Colocviu1_1;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Colocviu1_1MainActivity extends AppCompatActivity {
    private int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readClickCount();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_colocviu1_1_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.north_button).setOnClickListener(new CoordinateButtonEventListener("North", this));
        findViewById(R.id.east_button).setOnClickListener(new CoordinateButtonEventListener("East", this));
        findViewById(R.id.south_button).setOnClickListener(new CoordinateButtonEventListener("South", this));
        findViewById(R.id.west_button).setOnClickListener(new CoordinateButtonEventListener("West", this));

        TextView textCountTextView = (TextView) findViewById(R.id.click_count_text_view);
        textCountTextView.setText("Click count: " + this.clickCount);
    }

    @Override
    protected void onResume() {
        super.onResume();
        readClickCount();
    }

    public void click() {
        this.clickCount = clickCount + 1;

        TextView textCountTextView = (TextView) findViewById(R.id.click_count_text_view);
        textCountTextView.setText("Click count: " + this.clickCount);
    }

    private void readClickCount() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "click_counter.txt");

            FileInputStream fos = null;
            try {
                fos = new FileInputStream(file);
                this.clickCount = fos.read();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void saveClickCount() throws Exception{
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "click_counter.txt");

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(this.clickCount);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            saveClickCount();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            saveClickCount();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}