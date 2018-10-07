package com.example.android.android_me.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class MasterLIstFragment extends Fragment {

    //define an interface OnImageClickListener that a callback in the host activity
    //the callback is a method named onImageSelected(int position) that contains information which
    //position on the grid of images a user clicked
    OnImageClickListener mCallback;

    //OnImageClickListener interface calls a method in the host activity named onImageSelected
    public interface OnImageClickListener{
        void onImageSelected(int position);
    }

    //override onAttach to make sure the host activity has implemented the callback interface
    //if not, it throws an exception
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnImageClickListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement OnImageClickListener");
        }
    }

    public MasterLIstFragment(){}


    //inflates the GridView of all AndroidMe images
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        GridView gridView = (GridView)rootView.findViewById(R.id.gv_images);

        MasterListAdapter adapter = new MasterListAdapter(getContext(), AndroidImageAssets.getAll());

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mCallback.onImageSelected(position);
            }
        });

        return rootView;

    }
}
