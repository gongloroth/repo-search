package com.raywenderlich.reposearch;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by BG on 2017.12.07..
 */

public class DownloadRepoTask extends AsyncTask<String, Void, String> {

    DownloadCompleteListener mDownloadCompleteListener;

    public DownloadRepoTask(DownloadCompleteListener downloadCompleteListener){
        this.mDownloadCompleteListener = downloadCompleteListener;
    }

    @Override
    protected String doInBackground(String... params){
        try {
            return downloadData(params[0]);
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result){
        try{
            mDownloadCompleteListener.downloadComplete(Util.retrieveRepositoriesFromResponse(result));
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private String downloadData(String urlString) throws IOException{
        InputStream is = null;
        try{
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            is = conn.getInputStream();

            return convertToString(is);
        } finally {
            if(is != null){
                is.close();
            }
        }
    }

    private String convertToString(InputStream is) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null){
            total.append(line);
        }
        return new String(total);
    }


}
