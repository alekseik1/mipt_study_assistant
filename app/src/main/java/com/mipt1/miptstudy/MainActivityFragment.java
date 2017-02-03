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
        // Callback-магия
        helper.registerCallback(this);

        topic_spinner = (Spinner) getView().findViewById(R.id.spinner_select_topic_physics);
        editText_problem_number = (EditText) getView().findViewById(R.id.editText_problem);
        btn_do_search = (Button) getView().findViewById(R.id.button_search_problem);
        tv_search_results = (TextView) getView().findViewById(R.id.textView_search_problem_result);

        topic_spinner.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, helper.topics));
        // Поворот экрана - вернуть выбор спиннера
        if(savedInstanceState != null) {
            topic_spinner.setSelection(helper.sem - 1);
        }

        topic_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                helper.sem = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Чистим при фокусе
        editText_problem_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    editText_problem_number.setText("");
                }
            }
        });

        btn_do_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (editText_problem_number.length() != 0) {
                        if (!editText_problem_number.getText().toString().matches("[0-9]+\\.[0-9]+")) {
                            throw new IllegalArgumentException();
                        }
                        pd = ProgressDialog.show(getContext(), "Поиск", "Ищем задачу в Корявове, пожалуйста, будьте терпеливы!");
                        helper.search_problem_in_Kor(editText_problem_number.getText().toString());
                    }
                } catch(IllegalArgumentException e) {
                    Log.d("my_log", "Неправильный ввод");
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
