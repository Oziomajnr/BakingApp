package com.example.ogbeoziomajnr.bakingapp.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.example.ogbeoziomajnr.bakingapp.Fragment.StepDetailFragment;
import com.example.ogbeoziomajnr.bakingapp.Interface.OnFragmentInteractionListener;
import com.example.ogbeoziomajnr.bakingapp.Model.Step;
import com.example.ogbeoziomajnr.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class StepDetailActivity extends AppCompatActivity implements OnPreparedListener, OnFragmentInteractionListener {
    List<Step> steps;

    int currentPosition;
    Step currentStep;

    /*
    Note : there is no need to release this video player as the library already does releasing the resouce for you
     */
    @BindView(R.id.playerView)
    VideoView videoView;
    @BindView(R.id.textView_description_detail)
    @Nullable
    TextView textViewDescription;
    @BindView(R.id.imageViewBackDrop)
    ImageView imageView;

    protected boolean pausedInOnStop = false;

    private Uri videoUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        ButterKnife.bind(this);
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            try {
                Intent i = getIntent();
                steps = (ArrayList<Step>) i.getExtras().get("STEPS");
                currentPosition = i.getExtras().getInt("POSITION");
            } catch (Exception ex) {
                Toast.makeText(this, "An error has occured while loading data", Toast.LENGTH_LONG).show();
                Timber.e(ex);
            }
        }
        if (steps != null) {
                currentStep = steps.get(currentPosition);
            showMedia();
        }
    }

    private void setUpVideoView() {

        videoView.setVideoURI(videoUri);

        videoView.setOnPreparedListener(this);
    }

    @Override
    public void onPrepared() {
        videoView.start();
    }

    private void showMedia() {
        // the text view might be null because it is not in all the matching views for this layout
        if (textViewDescription != null) {
            textViewDescription.setText(currentStep.getDescription());
        }

        if (!TextUtils.isEmpty(currentStep.getVideoURL())) {
            videoView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            videoUri = Uri.parse(currentStep.getVideoURL());
            setUpVideoView();
        } else if (TextUtils.isEmpty(currentStep.getThumbnailURL())) {
            videoView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);

            Picasso.with(this).load(R.drawable.media_unavailable)
                    .into(imageView);
        } else {
            videoView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);

            Picasso.with(this).load(currentStep.getThumbnailURL()).placeholder(R.drawable.loading_image)
                    .error(R.drawable.media_unavailable)
                    .into(imageView);
        }
    }

    /**
     * Load the previous step from the list of steps, this method is triggered
     * when the Prev floating action button is clicked
     * * @param view
     */
    public void goToPrevStep(View view) {
        if (steps != null) {
            try {
                steps.get(currentPosition - 1);
                currentStep = steps.get(currentPosition - 1);
                showMedia();
                currentPosition--;
            } catch (IndexOutOfBoundsException ex) {
                Toast.makeText(this, "No More Steps", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Load the next step from the list of steps, this methos is triggered
     * when the Next floating action button is clicked
     *
     * @param view
     */
    public void goToNextStep(View view) {
        if (steps != null) {
            try {
                steps.get(currentPosition + 1);
                currentStep = steps.get(currentPosition + 1);
                showMedia();
                currentPosition++;
            } catch (IndexOutOfBoundsException ex) {
                Toast.makeText(this, "No More Steps", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (videoView.isPlaying()) {
            pausedInOnStop = true;
            videoView.pause();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // No need to release ExoPlayer here the ExoMedia wrapper already does that for us
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
