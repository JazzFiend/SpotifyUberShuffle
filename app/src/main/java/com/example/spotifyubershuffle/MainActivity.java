package com.example.spotifyubershuffle;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spotifyubershuffle.exceptions.ShuffleException;
import com.example.spotifyubershuffle.httpAdapter.HttpAdapter;
import com.example.spotifyubershuffle.httpAdapter.HttpAdapterVolleyImpl;
import com.example.spotifyubershuffle.spotifyAPIHelper.SpotifyAPIHelper;
import com.example.spotifyubershuffle.spotifyAPIHelper.SpotifyAPIHelperImpl;

//TODO: Need to break private class out to make it more testable.
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void uberShuffle(View view) {
        //TODO: Need to login instead hardcoding token.
        String accessToken = ((EditText)findViewById(R.id.editText_accessToken)).getText().toString();
        String username = ((EditText)findViewById(R.id.editText_username)).getText().toString();
        int playlistSize = Integer.parseInt(((EditText)findViewById(R.id.editText_trackNum)).getText().toString());
        UberShuffleArgs args = new UberShuffleArgs(accessToken, username, playlistSize);
        new AsyncUberShuffle().execute(args);
    }

    private static class AsyncUberShuffle extends AsyncTask<UberShuffleArgs, Void, String> {
        @Override
        protected String doInBackground(UberShuffleArgs... args) {
            try {
                HttpAdapter http = new HttpAdapterVolleyImpl(args[0].getAccessToken());
                SpotifyAPIHelper spotifyAPIHelper = new SpotifyAPIHelperImpl(http);
                SpotifyUberShuffle shuffler = new SpotifyUberShuffle(spotifyAPIHelper);
                shuffler.populateLibrary();
                return shuffler.createShufflePlaylist(args[0].getUsername(), args[0].getPlaylistSize());
            } catch (ShuffleException e) {
                ShuffleException shuffleException = e;
            }
            return "";
        }

        //TODO: Need to handle 400 and 401 codes gracefully
        protected void onPostExecute(String playlistId) {
            Context context = getApplicationContext();
            CharSequence text = String.format("Playlist Successfully Created!\nID: %s", playlistId);
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
