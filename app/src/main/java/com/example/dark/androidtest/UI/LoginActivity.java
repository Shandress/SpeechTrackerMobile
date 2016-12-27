package com.example.dark.androidtest.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dark.androidtest.GlobalClass;
import com.example.dark.androidtest.R;
import com.example.dark.androidtest.utility;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.dark.androidtest.R.id.loginField;
import static com.example.dark.androidtest.R.id.passField;
import static com.example.dark.androidtest.utility.convertStreamToString;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Connect(View view) {
        EditText loginField = (EditText)this.findViewById(R.id.loginField);
        EditText passField = (EditText)this.findViewById(R.id.passField);
        String login = loginField.getText().toString();
        String pass = passField.getText().toString();
        LoginTask lt = new LoginTask();
        try {
            String response = lt.execute(login, pass).get();
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show();

        } catch (InterruptedException e) {
            Toast.makeText(this, "Cancelled.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ExecutionException e) {
            Toast.makeText(this, "Something happened.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    private class LoginTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.1.3:4567/login");
            String login = params[0];
            int passHash = utility.hashPassword(params[1]);
            List<NameValuePair> nameValuePairs = new ArrayList<>(2);
            nameValuePairs.add(new BasicNameValuePair("login", login));
            nameValuePairs.add(new BasicNameValuePair("mobile", "true"));
            nameValuePairs.add(
                    new BasicNameValuePair("pass", Integer.toString(passHash)));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                if(response != null) {
                    String line = "";
                    InputStream inputstream = response.getEntity().getContent();
                    line = convertStreamToString(inputstream);
                    GlobalClass c = (GlobalClass)getApplicationContext();
                    c.currentUser.userFromXML(line);
                    return "OK. data updated.";
                } else {
                    return "Unable to complete your request";//Toast.makeText(this, "Unable to complete your request", Toast.LENGTH_LONG).show();
                }
            } catch (ClientProtocolException e) {
                return "Caught ClientProtocolException";//Toast.makeText(this, "Caught ClientProtocolException", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
               return "Caught IOException";// Toast.makeText(this, "Caught IOException", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                return "Caught Exception";//  Toast.makeText(this, "Caught Exception", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("OK. data updated.")) {
                startActivity(new Intent(LoginActivity.this, userInfoActivity.class));
            }
        }
    }
}
