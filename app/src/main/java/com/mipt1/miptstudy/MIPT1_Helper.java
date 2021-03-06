package com.mipt1.miptstudy;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


class MIPT1_Helper {

    interface TaskFoundCallback {
        void problem_found_callback(String res);
    }

    int sem = 1;

    private TaskFoundCallback callback;

    void registerCallback(TaskFoundCallback callback) {
        this.callback = callback;
    }

    String[] topics = {"Механика", "Термодинамика и молекулярная физика", "Электричество и магнетизм", "Оптика", "Атомная и ядерная физика"};

    String search_problem_in_Kor(String problem) {
        String res = "";
        try {
            new NetworkRequests().execute("search", problem);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private class NetworkRequests extends AsyncTask<String, Void, String> {

        String make_search_request(String problem, int sem) {
            String URL = (new StringBuilder()).append("http://mipt1.ru/1_2_3_4_5_kor.php?sem=")
                    .append(sem).append("&zad=").append(problem).toString();
            Document doc;
            Log.d("my_logs", URL);
            try {
                doc = Jsoup.connect(URL).get();
            } catch(IOException e) {
                e.printStackTrace();
                return "nothing";
            }
            return doc.text();
        }

        @Override
        protected String doInBackground(String... params) {
            if(params[0].equals("search")) {
                String s = make_search_request(params[1], sem);
                Log.d("my_logs", s);
                int tmp = s.indexOf("Задача");
                return s.substring(tmp, s.indexOf("!", tmp));
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            callback.problem_found_callback(s);
        }
    }
}
