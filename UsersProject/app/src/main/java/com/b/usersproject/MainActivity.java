package com.b.usersproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.b.usersproject.model.User;
import com.b.usersproject.service.GetService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userid              = findViewById(R.id.userid);

        Retrofit retrofit   = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetService getService   = retrofit.create(GetService.class);

        Call<List<User>> call   = getService.getUsersList();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if(response.isSuccessful()){

                    List<User> users    = response.body();

                    for (User data : users){
                        String dataid = "\n";
                        dataid += "ID : " + data.getId() + "\n";
                        dataid += "USERNAME : " + data.getUsername() + "\n";
                        dataid += "NAME : " + data.getName() + "\n";
                        dataid += "EMAIL : " + data.getEmail() + "\n";

                        userid.append(dataid);
                    }
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                userid.setText(t.getMessage());
            }
        });
    }
}