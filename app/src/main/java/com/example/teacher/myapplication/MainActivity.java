package com.example.teacher.myapplication;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import       retrofit2.Response;
import      retrofit2.Retrofit;
import     retrofit2.converter.gson.GsonConverterFactory;
public class MainActivity extends Activity {
    private static String PREDICTOR_URI_JSON = "https://predictor.yandex.net/";
    private static String PREDICTOR_KEY =
            "pdct.1.1.20170516T161226Z.589ed53adb033c59.ea040a17558eb4cfdf3e954fc6da6cccaaee1063";
    EditText editText;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText1);
        textView = (TextView) findViewById(R.id.textView1);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
// Прописываем то, что надо выполнить после изменения текста
                getReport();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int
                    after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {
            }
        });
    }
    void getReport() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PREDICTOR_URI_JSON)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApi service = retrofit.create(RestApi.class);
        Call<Model> call = service.predict(PREDICTOR_KEY,
                editText.getText().toString(), "ru");
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                try {
                    String textWord = response.body().text[0].toString();
                    textView.setText("Предиктор : " + textWord);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
    }
}
class Model {
    public boolean endOfWord;
    public int pos;
    public String[] text;
}
