package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
  EditText text;
  TextView allWords;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }


  public void startService(View view) throws InterruptedException {

    Intent serviceIntent = new Intent(this, AService.class);
    try {
      startService(serviceIntent);
      Thread.sleep(12000);
    }catch ( Exception e1){
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        this.startForegroundService(serviceIntent);
        Thread.sleep(12000);
      }else {
        this.startService(serviceIntent);
        Thread.sleep(12000);
      }
    }

  }

  // Method to stop the service
  public void stopService(View view) {
    stopService(new Intent(getBaseContext(), AService.class));
  }
}
