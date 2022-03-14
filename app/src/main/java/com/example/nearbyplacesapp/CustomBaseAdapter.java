package com.example.nearbyplacesapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    ArrayList<JSONObject> apiDataList;
    LayoutInflater inflater;
    Drawable imgSource;
    String fact;
    String picRepo;
    String apiRepo;
    String imgLink1;

    CustomBaseAdapter(Context ctx, ArrayList<JSONObject> dataList){
        this.context = ctx;
        this.apiDataList = dataList;
        inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return apiDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.list_content, null);
        TextView txtFact = (TextView) convertView.findViewById(R.id.fact);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.imgIcon);
        TextView txtLink1 = (TextView) convertView.findViewById(R.id.link1);
        TextView txtLink2 = (TextView) convertView.findViewById(R.id.link2);
        try {
            translateJSonObj(apiDataList.get(position));
            txtFact.setText(this.fact);
            txtLink1.setText(this.picRepo);
            txtLink2.setText(this.apiRepo);
            //imgView.setImageDrawable(this.imgSource);
            new DownloadImageFromInternet((ImageView) convertView.findViewById(R.id.imgIcon)).execute(this.imgLink1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }
    public void translateJSonObj(JSONObject obj) throws JSONException {
        this.fact = obj.getString("facts");
        String imgLink = obj.getString("url");
        this.imgLink1 = imgLink;
        this.picRepo = obj.getString("pics_repo");
        this.apiRepo = obj.getString("api_repo");
        //this.imgSource = LoadImageFromWebOperations(imgLink);
    }
   /* public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "axolotl pic");
            return d;
        } catch (Exception e) {
            return null;
        }
    }*/
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage=BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

}
