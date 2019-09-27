package com.example.sujit007.wwww;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SpeechToText extends AppCompatActivity {

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    DataBaseAdapter dataBaseAdapter;
    TextToSpeech t1;
    int count = 0;
    String replay;
    BackGround backGround = new BackGround();
    String url = "http://coldbeer.website/app/mobileData.php";

    String[] replies = {
            "Can you expIain?",
            "I understand why do you think this happened?",
            "Can you repeat that?",
            "Stop App"
    };

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_speech_to_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        dataBaseAdapter = new DataBaseAdapter(SpeechToText.this);

        // hide the action bar
        //    getSupportActionBar().hide();
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.getDefault());
                }
            }
        });

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        timer = new CountDownTimer(50 * 1 * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }


            @Override
            public void onFinish() {

                // finish();
                //System.exit(0);
            }
        };


        timer.start();


    }

    /**
     * Showing google speech input dialog
     */

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    if (result.get(0).equals(replies[3])) {
                        //   t1.speak(replies[3], TextToSpeech.QUEUE_FLUSH, null);
//                        while (t1.isSpeaking()){
//
//                        }
                        finish();
                        System.exit(0);
                    } else {
                        GetData(result.get(0));
                    }

                    timer.cancel();
                    timer.start();


                }
                break;
            }

        }
    }

    public void GetData(final String s) {
        replay = "";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            replay = jsonObject.getString("replay");
                            if (replay.equals("0")) {

                                replay = "";
                                // replay = replies[count%3];
                                //  count++;
                                // this is a coll app that i have create in this

                            }

                            //    Toast.makeText(getApplicationContext(), response , Toast.LENGTH_SHORT).show();
                            txtSpeechInput.setText(replay);
                            t1.speak(replay, TextToSpeech.QUEUE_FLUSH, null);
                            new BackGround().execute();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            txtSpeechInput.setText("Speak Correctly");
                            t1.speak("Speak Correctly", TextToSpeech.QUEUE_FLUSH, null);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        GetData(s);
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("text", s);
                return params;
            }
        };

        MySingletone.getmInstance(SpeechToText.this).addTorequestQue(stringRequest);

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.mmmmmmmmm, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            startActivity(new Intent(SpeechToText.this, DataEntry.class));
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//

    public class BackGround extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            while (t1.isSpeaking()) {

            }
            promptSpeechInput();


            return null;
        }
    }

}