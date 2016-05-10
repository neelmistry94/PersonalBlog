

package edu.umd.bferraro.personalblog;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi.DriveContentsResult;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;


public class DriveBackupActivity extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener {

    private static final String TAG = "drive-quickstart";
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 1;
    private static final int REQUEST_CODE_CREATOR = 2;
    private static final int REQUEST_CODE_RESOLUTION = 3;

    private GoogleApiClient mGoogleApiClient;
    private Bitmap mBitmapToSave;

    /**
     * Create a new file and save it to Drive.
     */
    private void saveFileToDrive() {

        Drive.DriveApi.newDriveContents(mGoogleApiClient)
                .setResultCallback(new ResultCallback<DriveContentsResult>() {

                    @Override
                    public void onResult(DriveContentsResult result) {
                        // If the operation was not successful, we cannot do anything
                        // and must
                        // fail.
                        if (!result.getStatus().isSuccess()) {
                            Log.i(TAG, "Failed to create new contents.");
                            return;
                        }
                        try{
                            PrintWriter writer = new PrintWriter(getFilesDir()+"the-file-name.txt", "UTF-8");
                            writer.println("The first line");
                            writer.println("The second line");
                            writer.close();
                        }
                        catch(Exception e){
                            Log.i("THIS IS THE EXCEPTION",e.toString()+"kj");

                        }

                        // Otherwise, we can write our data to the new contents.
                        Log.i(TAG, "New contents created.");
                        // Get an output stream for the contents.
                        OutputStream outputStream = result.getDriveContents().getOutputStream();

                        try {
                            outputStream.write(getDBBytes());
                            outputStream.close();
                            outputStream = null;

                        } catch (FileNotFoundException e) {
                            Log.w(TAG, "FileNotFoundException: " + e.getMessage());
                        } catch (IOException e1) {
                            Log.w(TAG, "Unable to write file contents." + e1.getMessage());
                        }

                        // Create the initial metadata - MIME type and title.
                        // Note that the user will be able to change the title later.
                        MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                                .setMimeType("text/db").setTitle("database.db").build();

//                        DriveId fileId = DriveId.decodeFromString("database");
//                        DriveFile driveFile = fileId.asDriveFile();
//                        com.google.android.gms.common.api.Status deleteStatus =
//                                driveFile.delete(mGoogleApiClient).await();
//                        if (!deleteStatus.isSuccess()) {
//                            Log.e(TAG, "Unable to delete app data.");
//
//                        }
                        // Create an intent for the file chooser, and start it.
                        IntentSender intentSender = Drive.DriveApi
                                .newCreateFileActivityBuilder()
                                .setInitialMetadata(metadataChangeSet)
                                .setInitialDriveContents(result.getDriveContents())
                                .build(mGoogleApiClient);
                        try {
                            startIntentSenderForResult(
                                    intentSender, REQUEST_CODE_CREATOR, null, 0, 0, 0);
                        } catch (SendIntentException e) {
                            Log.i(TAG, "Failed to launch file chooser.");
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient == null) {
            // Create the API client and bind it to an instance variable.
            // We use this instance as the callback for connection and connection
            // failures.
            // Since no account name is passed, the user is prompted to choose.
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        // Connect the client. Once connected, the camera is launched.
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onPause();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        // Called after a file is saved to Drive.
        if (resultCode == RESULT_OK) {
            finish();
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Called whenever the API client fails to connect.
        Log.i(TAG, "GoogleApiClient connection failed: " + result.toString());
        if (!result.hasResolution()) {
            // show the localized error dialog.
            GoogleApiAvailability.getInstance().getErrorDialog(this, result.getErrorCode(), 0).show();
            return;
        }
        // The failure has a resolution. Resolve it.
        // Called typically when the app is not yet authorized, and an
        // authorization
        // dialog is displayed to the user.
        try {
            result.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
        } catch (SendIntentException e) {
            Log.e(TAG, "Exception while starting resolution activity", e);
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        saveFileToDrive();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "GoogleApiClient connection suspended");
    }
    byte[] getDBBytes() {
        Context ctx = getApplicationContext();
        byte[] out = null;
        try {
            File from = new File(getFilesDir()+"the-file-name.txt");//ctx.getDatabasePath(ctx.getString(R.string.app_name));
            if (from != null)//.getAbsoluteFile().exists())
                out = strm2Bytes(new FileInputStream(from));
        } catch (Exception e) {}
        return out;
    }
    public byte[] strm2Bytes(InputStream is) {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        BufferedInputStream bufIS = null;
        if (is != null) try {
            bufIS = new BufferedInputStream(is);
            int cnt = 0;
            while ((cnt = bufIS.read(buffer)) >= 0) {
                byteBuffer.write(buffer, 0, cnt);
            }
        } catch (Exception e) {}
        finally { try { if (bufIS != null) bufIS.close(); } catch (IOException e) {}}
        return byteBuffer.toByteArray();
    }

}
