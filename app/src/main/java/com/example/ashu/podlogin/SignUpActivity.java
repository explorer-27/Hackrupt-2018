package com.example.ashu.podlogin;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
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

public class SignUpActivity extends AppCompatActivity {
    TextView signup ;
    EditText id,name,pwd,mobile,email;
    Spinner spinner;

     String idStr,nameStr,pwdStr,mobileStr,emailStr,categoryStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        spinner=findViewById(R.id.categorySpinner);

        id=findViewById(R.id.id_et_signup);
        name=findViewById(R.id.name_et_signup);
        pwd=findViewById(R.id.pwd_et_signup);
        mobile=findViewById(R.id.mobileNo_et_signup);
        email=findViewById(R.id.email_et_signup);






        signup=findViewById(R.id.signup_tv_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpClicked();
            }
        });
    }

    public void signUpClicked(){
        idStr=id.getText().toString();
        pwdStr=pwd.getText().toString();
        emailStr=email.getText().toString();
        mobileStr=mobile.getText().toString();
        nameStr=name.getText().toString();

        if(spinner.getSelectedItemPosition()==0)
         categoryStr="Student";
        else if (spinner.getSelectedItemPosition()==1)
            categoryStr="Staff";
        else if (spinner.getSelectedItemPosition()==2)
            categoryStr="Parent";

        String method="register";
        RegisterTask backgroundTask=new RegisterTask(this);
        backgroundTask.execute(method,idStr,pwdStr,nameStr,emailStr,mobileStr,categoryStr);



    }

    public class RegisterTask extends AsyncTask<String,Void,String> {

        Context ctx;
        RegisterTask(Context ctx){
            this.ctx=ctx;
        }

        @Override
        protected String doInBackground(String... strings) {


            String regUrl="http://139.59.93.232/xyzSchool/register.php",method=strings[0];
            if(method.equals("register")){

                String id,name,pwd,email,mobile,category;
                id=strings[1];pwd=strings[2];name=strings[3];email=strings[4];mobile=strings[5];category=strings[6];

                try {
                    URL url=new URL(regUrl);
                    Log.i("Insert Data" +" ","...."+url);
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                    String data= URLEncoder.encode("idNo","UTF-8")+"="+URLEncoder.encode(""+id,"UTF-8")+"&"+
                            URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                            URLEncoder.encode("pwd","UTF-8")+"="+URLEncoder.encode(pwd,"UTF-8")+"&"+
                            URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8")+"&"+
                            URLEncoder.encode("category","UTF-8")+"="+URLEncoder.encode(category,"UTF-8")+"&"+
                            URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");

                    Log.i("Insert Data" +" ","...Data..."+data);

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream is=httpURLConnection.getInputStream();
                    is.close();



                } catch (MalformedURLException e) {
                    Log.i("Exception Called",""+e);
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            return "Inserted";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            Intent i=new Intent(SignUpActivity.this,MainActivity.class);
            startActivity(i);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
