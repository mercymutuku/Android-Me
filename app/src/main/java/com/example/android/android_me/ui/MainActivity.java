package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class MainActivity extends AppCompatActivity implements MasterLIstFragment.OnImageClickListener {

    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    private boolean mTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //determine if you're creating a two pane or single pane display
        if (findViewById(R.id.android_me_linear_layout) != null){
            mTwoPane = true;

            //get rid of the Next button
            Button button = (Button) findViewById(R.id.btn_next);
            button.setVisibility(View.GONE);

            //change the grid view to space out the images more on a tablet
            GridView gridView = (GridView) findViewById(R.id.gv_images);
            gridView.setNumColumns(2);

            if (savedInstanceState == null) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                BodyPartFragment headFragment = new BodyPartFragment();
                headFragment.setImageIds(AndroidImageAssets.getHeads());
                fragmentManager.beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .commit();

                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setImageIds(AndroidImageAssets.getBodies());
                fragmentManager.beginTransaction()
                        .add(R.id.body_container, bodyFragment)
                        .commit();

                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setImageIds(AndroidImageAssets.getLegs());
                fragmentManager.beginTransaction()
                        .add(R.id.leg_container, legFragment)
                        .commit();

            }
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onImageSelected(int position) {
        //create a toast that displays the position that was clicked
        Toast.makeText(this, "Position clicked: " + position, Toast.LENGTH_LONG).show();

        int bodyPartNumber = position / 12;

        final int listIndex = position - 12 * bodyPartNumber;

        if (mTwoPane) {
            //handle 2 pane case
            BodyPartFragment newFragment = new BodyPartFragment();

            switch (bodyPartNumber){
                case 0:
                    newFragment.setImageIds(AndroidImageAssets.getHeads());
                    newFragment.setListIndex(listIndex);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, newFragment)
                            .commit();
                    break;

                case 1:
                    newFragment.setImageIds(AndroidImageAssets.getBodies());
                    newFragment.setListIndex(listIndex);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container, newFragment)
                            .commit();
                    break;

                case 2:
                    newFragment.setImageIds(AndroidImageAssets.getLegs());
                    newFragment.setListIndex(listIndex);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container, newFragment)
                            .commit();
                    break;

                default:
                    break;
            }

        }else{
            switch (bodyPartNumber) {
                case 0:
                    headIndex = listIndex;
                    break;

                case 1:
                    bodyIndex = listIndex;
                    break;

                case 2:
                    legIndex = listIndex;
                    break;

                default:
                    break;
            }

            //put this info in a Bundle and attach it to an intent that an AndroidMeActivity
            Bundle bundle = new Bundle();
            bundle.putInt("headIndex", headIndex);
            bundle.putInt("bodyIndex", bodyIndex);
            bundle.putInt("legIndex", legIndex);

            final Intent intent = new Intent(this, AndroidMeActivity.class);
            intent.putExtras(bundle);

            //get a reference to the Next button and launch the intent when this Button is clicked
            Button btnNext = (Button) findViewById(R.id.btn_next);

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });

        }

    }
}
