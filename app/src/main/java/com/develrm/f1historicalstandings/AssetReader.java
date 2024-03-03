package com.develrm.f1historicalstandings;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

public class AssetReader {

    public static String readXmlFile(Context context, String filename) {

        AssetManager assetManager = context.getAssets();

        try {

            InputStream inputStream = assetManager.open(filename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}