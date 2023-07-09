package com.smartneasy.hargahponline;
/**
 * Created by smartneasy on 01/06/17.
 */

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ziddandatabaseapiproject.MainActivity;
import com.example.ziddandatabaseapiproject.R;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;
import com.smartneasy.hargahponline.model.Handphone;
import com.smartneasy.hargahponline.server.AsyncInvokeURLTask;

import java.net.URLEncoder;
import java.util.ArrayList;
public class FormHandphone extends ActionBarActivity {
    private EditText textNama, textHarga;
    private Handphone handphone;
    public static final String urlSubmit = "submit_phone.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_handphone);
        initView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        handphone = new Handphone();
        if(getIntent().hasExtra("id")){
            String id = getIntent().getStringExtra("id");
            String nama = getIntent().getStringExtra("nama");
            String harga = getIntent().getStringExtra("harga");
            textNama.setText(nama);
            textHarga.setText(harga);
            handphone.setId(Integer.valueOf(id));
        }else{
            handphone.setId(0);
        }
    }

    private Intent getIntent() {
        return null;
    }

    private void setContentView(int activity_form_handphone) {
    }

    private ActionBar getSupportActionBar() {
        return null;
    }

    private void initView(){
        textNama = (EditText) textNama.findViewById();
        textHarga = (EditText) textHarga.findViewById();
    }
    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_handphone, (ViewGroup) menu);
        return true;
    }

    private LayoutInflater getMenuInflater() {
        return null;
    }

    private void goToMainActivity(){
        Intent in = new Intent(getApplicationContext(),
                MainActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
    }

    private void startActivity(Intent in) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                goToMainActivity();
                break;
            case R.id.option_menu_save:
                if(textHarga.getText().toString().trim().isEmpty() ||
                        textNama.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama dan Harga tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else{
                    sendData();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private Context getApplicationContext() {
        return null;
    }

    public void sendData() {
        AsyncInvokeURLTask task; try {
            String nama = textNama.getText().toString();
            String harga =
                    URLEncoder.encode(textHarga.getText().toString(), "utf-8");
            ArrayList<NameValuePair> nameValuePairs = new
                    ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("nama", nama));
            nameValuePairs.add(new BasicNameValuePair("harga", harga));
            nameValuePairs.add(new

                    BasicNameValuePair("id", String.valueOf(handphone.getId())));
            task = new
                    AsyncInvokeURLTask(nameValuePairs,
                    new AsyncInvokeURLTask.OnPostExecuteListener() {
                        @Override
                        public void onPostExecute(String result) {
// TODO Auto-generated method stub
                            Log.d("TAG", "savedata:" + result);
                            if (result.equals("timeout") ||
                                    result.trim().equalsIgnoreCase("Tidak dapat Terkoneksi ke Data Base")){
                        }else

                        {
                            goToMainActivity();
                        }
                    }
            ;
        });
        task.showdialog = true;
        task.message = "Proses Submit Data Harap Tunggu..";
        task.applicationContext = FormHandphone.this;
        task.mNoteItWebUrl = urlSubmit;
        task.execute();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
