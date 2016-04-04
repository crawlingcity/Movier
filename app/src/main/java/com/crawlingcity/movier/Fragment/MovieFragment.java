package com.crawlingcity.movier.Fragment;

/**
 * Created by crawl on 03/04/2016.
 * ${PACKAGE_NAME}
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.crawlingcity.movier.API.MovieAPI;
import com.crawlingcity.movier.Adapter.MovierListViewAdapter;
import com.crawlingcity.movier.R;
import com.crawlingcity.movier.Tmdb.Movie;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@FragmentWithArgs
public class MovieFragment extends Fragment {

    @Bind(R.id.gridViewMain)
    GridView lv;

    @Arg
    String endpoint;
    @Arg
    String language;

    public MovieFragment(){
    }

//    public static MovieFragment newInstance(String endpoint,String language) {
//        MovieFragment argsFrags = new MovieFragment();
//        Bundle args = new Bundle();
//        args.putString("endpoint", endpoint);
//        args.putString("language", language);
//        argsFrags.setArguments(args);
//        return argsFrags;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //listview
        //View view = inflater.inflate(R.layout.listview_main, container, false);
        //gridview
        View view = inflater.inflate(R.layout.gridview_main, container, false);
        ButterKnife.bind(this, view);


//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieAPI.BASE_URL)
//                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final MovieAPI movieAPI = retrofit.create(MovieAPI.class);
        final Call<Movie> resquestMovie = movieAPI.movies(endpoint,"a46db43a6932dfee6242200ff6b6949e",language);

        resquestMovie.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                if(!response.isSuccessful()) {
                    //Movie catalog = response.body();
                    //Log.i(TAG, "Erro: " + catalog.getResults());
                    //Log.i(TAG, "Erro: " + call.request());

                }
                else {
                    //sucesso
                    Movie movie = response.body();

                    //listview
                    //MovierListViewAdapter adapt = new MovierListViewAdapter(getActivity(),R.layout.listview_main, movie.getResults());
                    //gridview
                    MovierListViewAdapter adapt = new MovierListViewAdapter(getActivity(),R.layout.gridview_main, movie.getResults());
                    lv.setAdapter(adapt);


                }
            }



            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.i("TAG", t.getMessage());
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}

