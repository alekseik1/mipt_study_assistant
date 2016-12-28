package com.mipt1.miptstudy;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
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
public class MainActivityFragment extends Fragment implements MIPT1_Helper.TaskFoundCallback {

    Spinner topic_spinner;
    EditText editText_problem_number;
    Button btn_do_search;
    TextView tv_search_results;
    private ProgressDialog pd;
    MIPT1_Helper helper = new MIPT1_Helper();
    int course = 0;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        helper.registerCallback(this);
        topic_spinner = (Spinner) getView().findViewById(R.id.spinner_select_topic_physics);
        topic_spinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, MIPT1_Helper.topics));
        if(savedInstanceState != null) {
            topic_spinner.setSelection(course - 1);
        }
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
        editText_problem_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    editText_problem_number.setText("");
                }
            }
        });
        btn_do_search = (Button) getView().findViewById(R.id.button_search_problem);
        tv_search_results = (TextView) getView().findViewById(R.id.textView_search_problem_result);
        btn_do_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (editText_problem_number.length() != 0) {
                        pd = ProgressDialog.show(getContext(), "Поиск", "Ищем задачу в Корявове, пожалуйста, будьте терпеливы!");
                        helper.search_problem_in_Kor(editText_problem_number.getText().toString());
                    }
                } catch(Exception e ) {
                    e.printStackTrace();
                    pd.cancel();
                }
            }
        });

    }

    @Override
    public void problem_found_callback(String result) {
        if(pd.isShowing()) {
            pd.cancel();
        }
        tv_search_results.setText(result);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
