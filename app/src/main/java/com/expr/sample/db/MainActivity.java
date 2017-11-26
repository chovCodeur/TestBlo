package com.expr.sample.db;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Randula
 */

public class MainActivity extends Activity {
/*
    public static final String IMAGE_ID = "IMG_ID";
    private final String TAG = "MainActivity";
    public static final int PICK_IMAGE = 1;
    private DatabaseHelper databaseHelper;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        databaseHelper = new DatabaseHelper(this);
        imageView = (ImageView)findViewById(R.id.imageView);

    }

    public void testAjout(View v){
        Log.e("miPa","DEBUT INSERTION");
        //Drawable dbDrawable = getResources().getDrawable(R.drawable.voiture_small);
        //databaseHelper.insetImage(dbDrawable, IMAGE_ID);
        Log.e("miPa","FIN INSERTION");

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            Log.e("MiPa request", String.valueOf(requestCode));
            Log.e("MiPa request", String.valueOf(PICK_IMAGE));

        }
    }

    public void testLire(View v){
        Log.e("miPa","DEBUT LECTURE");

        new LoadImageFromDatabaseTask().execute(0);
        Log.e("miPa","DEBUT LECTURE");

    }

    private class LoadImageFromDatabaseTask extends AsyncTask<Integer, Integer, ImageHelper> {

        private final ProgressDialog LoadImageProgressDialog =  new ProgressDialog(MainActivity.this);

        protected void onPreExecute() {
            this.LoadImageProgressDialog.setMessage("Loading Image from Db...");
            this.LoadImageProgressDialog.show();
        }

        @Override
        protected ImageHelper doInBackground(Integer... integers) {
            Log.e("Loa : doInBackground", "");
            return databaseHelper.getImage(IMAGE_ID);
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(ImageHelper imageHelper) {
            Log.e("LoadI :  - ImageID ", imageHelper.getImageId());
            if (this.LoadImageProgressDialog.isShowing()) {
                this.LoadImageProgressDialog.dismiss();
            }
            setUpImage(imageHelper.getImageByteArray());
        }

    }


    private void setUpImage(byte[] bytes) {
        Log.d(TAG, "Decoding bytes");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView.setImageBitmap(bitmap);
    }

*/

    // Les constantes
    final static int SELECT_PICTURE = 1;

    // Les éléments d'affichage
    ImageView imageVue;

    //Les variables
    public Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Transforme la resource en bitmap
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sqlite_image);

        //Initialise l'imageview on lui met une action
        imageVue = (ImageView) findViewById(R.id.imageVue);
        imageVue.setImageBitmap(bitmap);
        imageVue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btGalleryClick(v);
            }
        });
    }
    /*
        *Méthode pour ouvrir une galerie d'image
     */
    public void btGalleryClick(View v){

        //creation et ouverture de la boite de dialogue
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selectionnez une image"), SELECT_PICTURE);
    }

    /*
        *Retour du resultat de la galerie
     */
    protected void onActivityResult(int request, int resultCode, Intent data) {
        super.onActivityResult(request, resultCode, data);

        if(resultCode == RESULT_OK) {
            switch (request) {
                case SELECT_PICTURE :
                    String path = getRealPathFromUri(data.getData());
                    Log.e("Choix d'image", "uri"+path);

                    //Transforme l'image en jpg
                    bitmap = BitmapFactory.decodeFile(path);

                    imageVue.setImageBitmap(bitmap);

                    break;

            }
        }
    }

    /*
    *Methode pour récuperer l'Uri de l'image
     */
    private String getRealPathFromUri(Uri contentUri) {
        String result;

        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);

        if(cursor == null){
            result = contentUri.getPath();
        }else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

}



