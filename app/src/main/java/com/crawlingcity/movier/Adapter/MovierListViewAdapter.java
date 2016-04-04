package com.crawlingcity.movier.Adapter;

/**
 * Created by crawl on 03/04/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crawlingcity.movier.R;
import com.crawlingcity.movier.Tmdb.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by crawl on 02/04/2016.
 * ${PACKAGE_NAME}
 */
public class MovierListViewAdapter extends ArrayAdapter<Result> {

    private Context context;
    private List<Result> profileList;

    public MovierListViewAdapter(Context context,int resource, List<Result> object){
        super(context,resource,object);
        this.context = context;
        this.profileList = object;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.listview_item, parent, false);

            Result filme = profileList.get(position);


            TextView listViewItem = (TextView) view.findViewById(R.id.title);
            listViewItem.setText(filme.getTitle());
            //Toast.makeText(getContext(), filme.getReleaseDate(), Toast.LENGTH_LONG).show();
            //sinopse
//        TextView txtOverview = (TextView) view.findViewById(R.id.overview);
//        txtOverview.setText(filme.getOverview());


            ImageView img = (ImageView) view.findViewById(R.id.thumbnail);
            String base_img_url = "http://image.tmdb.org/t/p/w500";
            Picasso.with(getContext()).load(base_img_url + filme.getPosterPath()).into(img);

        return view;

    }
}

