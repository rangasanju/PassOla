package com.pass.ola.passola;



        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteStatement;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.webkit.WebSettings;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;
        import org.json.JSONException;
        import org.json.JSONObject;

        import com.loopj.android.http.AsyncHttpClient;
        import com.loopj.android.http.AsyncHttpResponseHandler;
        import com.loopj.android.http.RequestParams;

        import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {


    WebView myWebView;
    Button myButton;
    String badata = "";
    // Progress Dialog Object
    ProgressDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiate Progress Dialog object
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);



        // FOR TESTING THE BA DEVICE STAND ALONE
        myButton = (Button) findViewById(R.id.startBA);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              startBA(v);

            }
        });



    }



/*
    @android.webkit.JavascriptInterface
    public void startBio(String crewid) {



        //Toast.makeText(this, crewid, Toast.LENGTH_SHORT).show();

        Intent getNameScreenIntent = new Intent(this, BioScreen.class);

        final int res = 1;
        getNameScreenIntent.putExtra("callingactivity","MainActivity");
        getNameScreenIntent.putExtra("crewid",crewid);
        startActivityForResult(getNameScreenIntent,res);


    }
*/


    @android.webkit.JavascriptInterface
    public void startBA(View view) {

        Log.d("LOG : " , "INSIDE Start BA");

        //Toast.makeText(this, crewid, Toast.LENGTH_SHORT).show();

        Intent getNameScreenIntent = new Intent(this, BAScreen.class);

        final int res = 2;
        getNameScreenIntent.putExtra("callingactivity","MainActivity");
        getNameScreenIntent.putExtra("crewid","RTM1001");
        startActivityForResult(getNameScreenIntent,res);


    }



    @android.webkit.JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            switch(requestCode)
            {
                case 1:
                    String msg = data.getStringExtra("match");
                    //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                    myWebView.loadUrl("javascript:returnFromBioVeri('" + msg + "')");
                    //myWebView.loadUrl("javascript:returnFromBioVeri('" + msg + "')");
                    break;
                case 2:
                    msg = data.getStringExtra("badata");
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    RequestParams params = new RequestParams();
                    params.put("result",msg);
                    invokeWS(params);
                    //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                    //myWebView.loadUrl("javascript:receiveBAResponse('" + msg + "')");
                    //myWebView.loadUrl("javascript:returnFromBioVeri('" + msg + "')");
                    break;

            }
        }


    }

    public void invokeWS(RequestParams params){
        // Show Progress Dialog
        prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();


        client.post("http://192.168.54.102:8080/cmsws/webapi/messages?result=ahhhndroid",params ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                prgDialog.hide();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }



        });
    }


/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView username = (TextView) findViewById(R.id.textView);

        String nameback = data.getStringExtra("name");

        username.append(" " + nameback);


    } */







}
