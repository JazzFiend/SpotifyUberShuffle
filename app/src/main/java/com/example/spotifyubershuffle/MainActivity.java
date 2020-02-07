package com.example.spotifyubershuffle;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//TODO: Need to break private class out to make it more testable.
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void uberShuffle(View view) throws InterruptedException, ExecutionException, TimeoutException {
        //TODO: Need to login instead hardcoding token.
        String accessToken = ((EditText)findViewById(R.id.editText_accessToken)).getText().toString();
        String username = ((EditText)findViewById(R.id.editText_username)).getText().toString();
        int playlistSize = Integer.parseInt(((EditText)findViewById(R.id.editText_trackNum)).getText().toString());
        UberShuffleArgs args = new UberShuffleArgs(accessToken, username, playlistSize);
        AsyncUberShuffle uberShuffle = new AsyncUberShuffle(getApplicationContext(), accessToken);
        uberShuffle.execute(args);
        CharSequence text = String.format("Playlist Successfully Created!\nID: %s", uberShuffle.get(5, TimeUnit.MINUTES));
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toast.show();
    }
}
