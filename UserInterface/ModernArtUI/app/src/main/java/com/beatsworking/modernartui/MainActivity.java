package com.beatsworking.modernartui;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity
        implements MoreInfoDialogFragment.MoreInfoDialogListener, SeekBar.OnSeekBarChangeListener {

    // Tag for logging
    static private final String TAG = "modernartui";
    static private final String MOMA_URL = "http://www.moma.org";

    static private final int GET_TEXT_REQUEST_CODE =1;
    static private final int MAX_SEEK = 100;

    private int ll1OriginalColor;
    private int ll2OriginalColor;
    private int ll3OriginalColor;
    private int ll4OriginalColor;
    private int ll5OriginalColor;

    private static LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout ll3;
    private LinearLayout ll4;
    private LinearLayout ll5;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);


        // Get the layouts we will change the colours of
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        ll5 = (LinearLayout) findViewById(R.id.ll5);

        // Get all the original colors for reference
        ColorDrawable ll1ColorDrawable = (ColorDrawable) ll1.getBackground();
        ll1OriginalColor = ll1ColorDrawable.getColor();
        ColorDrawable ll2ColorDrawable = (ColorDrawable) ll2.getBackground();
        ll2OriginalColor = ll2ColorDrawable.getColor();
        ColorDrawable ll3ColorDrawable = (ColorDrawable) ll3.getBackground();
        ll3OriginalColor = ll3ColorDrawable.getColor();
        ColorDrawable ll4ColorDrawable = (ColorDrawable) ll4.getBackground();
        ll4OriginalColor = ll4ColorDrawable.getColor();
        ColorDrawable ll5ColorDrawable = (ColorDrawable) ll5.getBackground();
        ll5OriginalColor = ll5ColorDrawable.getColor();

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
        if (id == R.id.action_moreinfo) {
            DialogFragment newFragment = new MoreInfoDialogFragment();
            newFragment.show(getFragmentManager(),"MoreInfo");
        }

        return super.onOptionsItemSelected(item);
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        startUrlIntent(MOMA_URL);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        Toast.makeText(MainActivity.this, "Negative Click", Toast.LENGTH_LONG);
    }

    @Override
    public void onDialogNeutralClick(DialogFragment dialog) {
        startUrlIntent(MOMA_URL);
    }
    // Launches the MOMA website by starting a browse implicit intent
    private void startUrlIntent(String url) {

        Log.i(TAG, "Entered startUrlIntent");

        // Create a base intent for viewing a URL
        // (HINT:  second parameter uses Uri.parse())
        Uri addressUri = Uri.parse(url);
        Intent baseIntent = new Intent(Intent.ACTION_VIEW, addressUri);

        // Create a chooser intent, for choosing which Activity will carry out the baseIntent
        Intent chooserIntent = Intent.createChooser(baseIntent, "Select an app");
        Log.i(TAG,"Chooser Intent Action:" + chooserIntent.getAction());

        // Start the chooser Activity, using the chooser intent
        startActivity(chooserIntent);
        //startActivityForResult(chooserIntent, GET_TEXT_REQUEST_CODE);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seek) {
        // TODO Implement onSeekStartTracking

    }

    @Override
    public void onStopTrackingTouch(SeekBar seek){
        // TODO Implement onStopTrackingTouch
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // TODO Implement onProgressChanged
        ll1.setBackgroundColor(transitionColor(ll1OriginalColor, ll2OriginalColor, progress));
        ll2.setBackgroundColor(transitionColor(ll2OriginalColor, ll3OriginalColor, progress));
        ll3.setBackgroundColor(transitionColor(ll3OriginalColor, ll4OriginalColor, progress));
        ll4.setBackgroundColor(transitionColor(ll4OriginalColor, ll5OriginalColor, progress));
        ll5.setBackgroundColor(transitionColor(ll5OriginalColor, ll1OriginalColor, progress));
    }


    private int transitionColor(int originalColor, int destinationColor, int progress) {

        //((TextView)findViewById(R.id.progressReadout)).setText(Integer.toString(progress));

        if (progress==0)
            return originalColor;
        if (progress==100)
            return destinationColor;

        int transitionColor = 0xff000000;
        // Get the original color in red/green/blue
        int originalRed = (originalColor >> 16) & 0xff;
        int originalGreen = (originalColor >> 8) & 0xff;
        int originalBlue = (originalColor ) & 0xff;

        // Get the destination colour in red/green/blue
        // Get the original color in red/green/blue
        int destinationRed = (destinationColor >> 16) & 0xff;
        int destinationGreen = (destinationColor >> 8) & 0xff;
        int destinationBlue = (destinationColor ) & 0xff;

        double redDelta = originalRed - destinationRed;
        double blueDelta = originalBlue - destinationBlue;
        double greenDelta = originalGreen - destinationGreen;

        double redInter = redDelta/MAX_SEEK;
        redInter = redInter * progress;
        double transitionRed = originalRed - redInter;

        double greenInter = greenDelta/MAX_SEEK;
        greenInter = greenInter * progress;
        double transitionGreen = originalGreen - greenInter;

        double blueInter = blueDelta/MAX_SEEK;
        blueInter = blueInter * progress;
        double transitionBlue = originalBlue - blueInter;

        return 0xFF000000 | ( (int)transitionRed << 16 ) | ( (int)transitionGreen << 8 ) | ( (int)transitionBlue );
    }

}



