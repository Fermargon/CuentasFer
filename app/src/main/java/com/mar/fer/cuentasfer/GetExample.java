package com.mar.fer.cuentasfer;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class GetExample {

    OkHttpClient client = new OkHttpClient();

     @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTYzMywiaWRlbiI6IjQ4NzI2OTIwMTUwNjA3NDYzNCIsIm1kIjp7InVzZXJuYW1lIjoiZmVybmFuZG9tZyIsImtleVZlcnNpb24iOjMsImRpc2NyaW1pbmF0b3IiOiIwMzA3In0sInRzIjoxNTM2NzU2ODA0NzgxfQ.kQebf9uIS-z6px8_F4WibNMICAgXu0hTBD5FC6JkR5k")
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}