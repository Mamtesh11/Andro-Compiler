package com.example.coed.androcompile;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    String ServerURL = "http://192.168.43.123/AndroCompile/compileAndRun.php" ;
    Button save, compile;
    String code, inputs, language, fileName;
    EditText codeText, inputText, fileNameText;
    TextView compileRes;
    private Spinner spinner1;
    static String  phpData;
    private String filepath = "AndroFile";
    File myExternalFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //save = (Button) findViewById(R.id.save);
        compile = (Button) findViewById(R.id.compileNrun);

        codeText = (EditText) findViewById(R.id.editor);
        inputText = (EditText) findViewById(R.id.inputEdit);
        //fileNameText = (EditText) findViewById(R.id.filename);

        compileRes = (TextView) findViewById(R.id.output_edit);

        spinner1 = (Spinner) findViewById(R.id.spinner1);


        compile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = codeText.getText().toString();
                inputs = inputText.getText().toString();
                language = spinner1.getSelectedItem().toString();
                compileAndRun(code, inputs, language);

            }
        });



    }





    public void compileAndRun(final String code, final String inputs, final String language){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String CodeHolder = code ;
                String InputHolder = inputs;
                String LanguageHolder = language;


                List<NameValuePair> nameValuePairs;
                nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("code", CodeHolder));
                nameValuePairs.add(new BasicNameValuePair("inputs", InputHolder));
                nameValuePairs.add(new BasicNameValuePair("language", LanguageHolder));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();

                    InputStream is = httpEntity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            is, "iso-8859-1"), 8); // From here you can extract the data that you get from your php file..

                    StringBuilder sb = new StringBuilder();

                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }

                    is.close();
                    phpData="";
                    phpData = sb.toString(); // Here you are converting again the string into json object. So that you can get values with the help of keys that you send


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Inserted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                compileRes.setText(phpData);


            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(code, inputs,language);
    }




}

