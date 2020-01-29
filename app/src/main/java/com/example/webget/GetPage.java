package com.example.webget;

import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetPage extends AsyncTask<String, Void, String> {
    String webResponse;
    TextView webText;

    public GetPage(TextView webText) {
        this.webText = webText;
    }


    @Override
    protected String doInBackground(String... strings) {
        URL url;
        HttpsURLConnection urlConnection = null;

        try {
            url = new URL(strings[0]);
            urlConnection = (HttpsURLConnection) url.openConnection();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                webResponse = readStream(urlConnection.getInputStream());
                return webResponse;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {

                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(String result) {
       result=result.replaceAll("(?s)<!--.*?-->","");
       result=result.replaceAll("<script[^>]*>.*?</script>","");
       result=result.replaceAll("<[^>]*>","");

        webText.setText(result);
        super.onPostExecute(result);





        /*result = result.replaceAll("<(.*?)\\>"," ");
        result = result.replaceAll("<(.*?)\\\n"," ");


        result = result.replaceFirst("(.*?)\\>", " ");
        result = result.replaceAll("&nbsp;"," ");
        result = result.replaceAll("&amp;"," ");
        result = result.replaceAll("&amp;"," ");
        result = result.replaceAll("&amp;"," ");

        result = result.replaceAll("&amp;"," ");

        webText.setText(result);
        webText.setText(result.replaceAll("(?s)<script[^>]>.*?</script>", ""));

        //webText.setText(result.replaceAll("\\<[^>]*>"," "));
        super.onPostExecute(result);*/
    }




}
