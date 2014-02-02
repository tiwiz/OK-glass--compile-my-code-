package it.droidcon.helloglass;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.glass.app.Card;
import com.google.android.glass.timeline.TimelineManager;

/**
 * Based on Google Glass Sample - Compass and StopWatch
 * @author IsabelM
 * 
 */
public class MainActivity extends Activity {

	private TextToSpeech textToSpeech;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                /* do nothing*/
            }
        });

        setContentView(R.layout.card_text);
        addCard();
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onDestroy() {
        closeApp(false);
    	super.onDestroy();
    }

    private void closeApp(boolean calledFromMenu){
        textToSpeech.shutdown();
        textToSpeech = null;

        if(calledFromMenu)
            finish();
    }

    private void speak(){
        textToSpeech.speak(getString(R.string.read_aloud),TextToSpeech.QUEUE_FLUSH,null);
    }

    private void addCard(){
        TimelineManager timelineManager = TimelineManager.from(this);

        Card mCard = new Card(this);
        mCard.setText("Hello DroidCon");
        mCard.setFootnote("I'm a footer!");

        timelineManager.insert(mCard);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.read_aloud:
                speak();
                return true;
            case R.id.stop:
                closeApp(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
