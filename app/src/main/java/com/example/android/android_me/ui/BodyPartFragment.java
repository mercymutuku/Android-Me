package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.R;

import java.util.ArrayList;
import java.util.List;

public class BodyPartFragment extends Fragment {

    private static final String TAG = "BodyPartFragment";
    private List<Integer> mImageIds;
    private int mListIndex;

    private static final String IMAGE_ID_LIST = "image_ids";
    private static final String LAST_INDEX = "last_index";

    public BodyPartFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null){
            mImageIds = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
            mListIndex = savedInstanceState.getInt(LAST_INDEX);
        }

        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);

        final ImageView imageView = (ImageView)rootView.findViewById(R.id.iv_body_part);

        //In the BodyPartFragment class, set a click listener on the image view and on a click increment the list index and set the image resource
        //If you reach the end of a list of images, set the list index back to 0 (the first item in the list)
        if (mImageIds != null){
            imageView.setImageResource(mImageIds.get(mListIndex));

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mListIndex < mImageIds.size()-1){
                        mListIndex++;
                    } else {
                        mListIndex = 0;
                    }

                    imageView.setImageResource(mImageIds.get(mListIndex));
                }
            });

        } else {
            Log.d(TAG, "list has a null list of image id's");
        }

        return rootView;
    }

    public void setImageIds(List<Integer> mImageIds){this.mImageIds = mImageIds;}

    public void setListIndex(int mListIndex){ this.mListIndex = mListIndex;}

    //Override onSaveInstanceState and save the current state of the fragment
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList(IMAGE_ID_LIST, (ArrayList<Integer>) mImageIds);
        outState.putInt(LAST_INDEX, mListIndex);
    }
}
