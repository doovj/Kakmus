package com.doovj.kakmus;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    ArrayList<KamusModel> InggrisIndo, IndoInggris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        KamusHelper kamusHelper;
        AppPreference appPreference;
        double progress;
        double maxProgress = 100;

        @Override
        protected void onPreExecute() {

            kamusHelper = new KamusHelper(MainActivity.this);
            appPreference = new AppPreference(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Boolean firstRun = appPreference.getFirstRun();

            if (firstRun) {

                InggrisIndo = preLoadRaw(R.raw.english_indonesia);
                IndoInggris = preLoadRaw(R.raw.indonesia_english);

                kamusHelper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 110.0;
                Double progressDiff = (progressMaxInsert - progress) / (InggrisIndo.size() + IndoInggris.size());

                kamusHelper.insertTransaction(InggrisIndo, true);
                progress += progressDiff;
                publishProgress((int) progress);

                kamusHelper.insertTransaction(IndoInggris, false);
                progress += progressDiff;
                publishProgress((int) progress);

                kamusHelper.close();

                appPreference.setFirstRun(false);

                publishProgress((int) maxProgress);

            } else {
                try {
                    synchronized (this) {
                        this.wait(100);

                        publishProgress(50);

                        this.wait(100);
                        publishProgress((int) maxProgress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<KamusModel> preLoadRaw(int data) {
        ArrayList<KamusModel> kamusModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(data);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                KamusModel kamusModel;

                kamusModel = new KamusModel(splitstr[0], splitstr[1]);
                kamusModels.add(kamusModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kamusModels;
    }
}
