package com.example.ashu.podlogin;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class LoginActivity extends AppCompatActivity {

    TextView login,signUp;
    EditText id,pwd;

    public String idStr,pwdStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=findViewById(R.id.login_tv);
        signUp=findViewById(R.id.singup_tv);

        id=findViewById(R.id.login_et);
        pwd=findViewById(R.id.pwd_et);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loginClicked();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });

    }

    public void loginClicked(){
        idStr=id.getText().toString();
        pwdStr=pwd.getText().toString();

        if(idStr!=null&&pwdStr!=null){

            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute("login");
        }
        else
        {
            Toast.makeText(this,"Enter Correct Details",Toast.LENGTH_SHORT).show();
        }


    }

    public class BackgroundTask extends AsyncTask<String, Void, String> {

        Context ctx;
        String json_string = "", result = "", method, data = "";

        BackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            method = params[0];

            if (method.equals("login")) {
                String loginUrl = "http://139.59.93.232/xyzSchool/login.php";


                try {
                    URL url = new URL(loginUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    data = URLEncoder.encode("idNo", "UTF-8") + "=" + URLEncoder.encode("" + idStr,"UTF-8") + "&" +
                            URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode("" + pwdStr, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    Log.i("Query" + " ", "...query.." + data);

                    InputStream is = httpURLConnection.getInputStream();
                    Scanner scanner = new Scanner(is);
                    scanner.useDelimiter("\\A");
                    boolean hasInput = scanner.hasNext();
                    if (hasInput)
                        json_string = scanner.next();
                    else
                        json_string = "";

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return json_string;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            if (method.equals("login") && result != null) {

                String access = JsonParser.getJsonData("login", result);
                if (access.equals("Allowed")) {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                } else
                    Toast.makeText(getApplicationContext(), "Enter Correct Details", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
