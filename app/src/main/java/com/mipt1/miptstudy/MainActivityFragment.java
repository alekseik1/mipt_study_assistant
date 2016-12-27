package com.mipt1.miptstudy;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    String problem_page;
    Spinner topic_spinner;
    EditText editText_problem_number;
    Button btn_do_search;
    TextView tv_search_results;
    private ProgressDialog pd;
    int course = 0;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        new NetworkRequests().execute("14.23");
        topic_spinner = (Spinner) getView().findViewById(R.id.spinner_select_topic_physics);
        String[] data = {"Механика", "Термодинамика и молекулярная физика", "Электричество и магнетизм", "Оптика", "Атомная и ядерная физика"};
        topic_spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, data));
        topic_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        editText_problem_number = (EditText) getView().findViewById(R.id.editText_problem);
        editText_problem_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_problem_number.setText("");
            }
        });
        btn_do_search = (Button) getView().findViewById(R.id.button_search_problem);
        tv_search_results = (TextView) getView().findViewById(R.id.textView_search_problem_result);
        btn_do_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (editText_problem_number.length() != 0) {
                        pd = ProgressDialog.show(getContext(), "Ищем задачу в Корявове, пожалуйста, будьте терпеливы!", "");
                        new NetworkRequests().execute(editText_problem_number.getText().toString()).get();
                        tv_search_results.setText(problem_page);
                        pd.cancel();
                    }
                } catch(Exception e ) {
                    e.printStackTrace();
                    pd.cancel();
                }
            }
        });
    }

    private class NetworkRequests extends AsyncTask<String, Void, String> {

        public String make_search_request(String problem) {
            String URL = (new StringBuilder()).append("https://mipt1.ru/1_2_3_4_5_kor.php?sem=1&zad=").append(problem).toString();
            Document doc = null;
            try {
                //Log.d("my_log", URL);
                doc = Jsoup.connect(URL).get();
                //Log.d("my_log", doc.text());
            } catch(IOException e) {
                e.printStackTrace();
                return "nothing";
            }
            return doc.text();
        }

        @Override
        protected String doInBackground(String... params) {
            return make_search_request(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            int tmp = s.indexOf("Задача");
            problem_page = s.substring(tmp, s.indexOf("!", tmp));
            //Log.d("my_log", problem_page);
            //Log.d("my_logs", s);
        }
    }
}
