package edu.umd.bferraro.personalblog;

        import java.io.IOException;
        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.media.AudioManager;
        import android.media.AudioManager.OnAudioFocusChangeListener;
        import android.media.MediaPlayer;
        import android.media.MediaRecorder;
        import android.os.Bundle;
        import android.os.Environment;
        import android.util.Log;
        import android.widget.CompoundButton;
        import android.widget.CompoundButton.OnCheckedChangeListener;
        import android.widget.ToggleButton;


/**
 * Created by Terminator on 5/5/16.
 */
public class AudioRecord extends Activity {


    private static final String TAG = "AudioRecord";
    private static String mFileName;
    private static int i = 0;

    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private AudioManager mAudioManager;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.audio_layout);

        final ToggleButton mRecordButton = (ToggleButton) findViewById(R.id.record_button);
        final ToggleButton mPlayButton = (ToggleButton) findViewById(R.id.play_button);


        // Set up record Button
        mRecordButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                // Set enabled state
                mPlayButton.setEnabled(!isChecked);

                // Start/stop recording
                onRecordPressed(isChecked);

            }
        });

        // Set up play Button
        mPlayButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                // Set enabled state
                mRecordButton.setEnabled(!isChecked);

                // Start/stop playback
                onPlayPressed(isChecked);
            }
        });

        // Get AudioManager
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Request audio focus
        mAudioManager.requestAudioFocus(afChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

    }

    // Toggle recording
    private void onRecordPressed(boolean shouldStartRecording) {

        if (shouldStartRecording) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    // Start recording with MediaRecorder
    private void startRecording() {
        i++;
        mFileName = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/audiorecordtest" + i + ".3gp";

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

     //   Log.e(TAG, mFileName);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "Couldn't prepare and start MediaRecorder");
        }

        mRecorder.start();
    }

    // Stop recording. Release resources
    private void stopRecording() {

        if (null != mRecorder) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }

        Intent i = new Intent();
        i.putExtra("audioFile", getAudioFile());
        setResult(RESULT_OK, i);
    }

    // Toggle playback
    private void onPlayPressed(boolean shouldStartPlaying) {

        if (shouldStartPlaying) {
            startPlaying();
        } else {
            stopPlaying();
        }

    }

    // Playback audio using MediaPlayer
    private void startPlaying() {

        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(TAG, "Couldn't prepare and start MediaPlayer");
        }

    }

    // Stop playback. Release resources
    private void stopPlaying() {
        if (null != mPlayer) {
            if (mPlayer.isPlaying())
                mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }

        Log.e(TAG, "Stop playing?");

        AlertDialog alertDialog = new AlertDialog.Builder(AudioRecord.this).create();
        alertDialog.setTitle("Are you okay with this audio?");

        alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();

    }

    // Listen for Audio Focus changes
    OnAudioFocusChangeListener afChangeListener = new OnAudioFocusChangeListener() {

        @Override
        public void onAudioFocusChange(int focusChange) {

            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                mAudioManager.abandonAudioFocus(afChangeListener);

                // Stop playback, if necessary
                if (null != mPlayer && mPlayer.isPlaying())
                    stopPlaying();
            }

        }

    };

    // Release recording and playback resources, if necessary
    @Override
    public void onPause() {
        super.onPause();

        if (null != mRecorder) {
            mRecorder.release();
            mRecorder = null;
        }

        if (null != mPlayer) {
            mPlayer.release();
            mPlayer = null;
        }

    }


    // GETS AUDIO FILE PATH
    public String getAudioFile() {

        return mFileName;
    }

}

