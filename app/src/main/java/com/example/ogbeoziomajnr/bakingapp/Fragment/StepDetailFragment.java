package com.example.ogbeoziomajnr.bakingapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.example.ogbeoziomajnr.bakingapp.Interface.OnFragmentInteractionListener;
import com.example.ogbeoziomajnr.bakingapp.Model.Step;
import com.example.ogbeoziomajnr.bakingapp.R;
import com.example.ogbeoziomajnr.bakingapp.Util.Utility;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the OnFragmentInteractionListener
 * to handle interaction events.
 * Use the {@link StepDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepDetailFragment extends Fragment implements OnPreparedListener {


    private OnFragmentInteractionListener mListener;


    VideoView videoView;
    TextView textViewDescription;
    ImageView imageView;

    Step currentStep;


    protected boolean pausedInOnStop = false;

    private Uri videoUri;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StepDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StepDetailFragment newInstance(String param1, String param2) {
        StepDetailFragment fragment = new StepDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // the fragment should keep its data when rotated
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate the layout and keep the rootciew so that it can be used to locate the others
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);

        try {
            // dont hold useless reference
            if (Utility.isTablet(getActivity())) {
                textViewDescription = (TextView) rootView.findViewById(R.id.textView_description_detail);
                videoView = (VideoView) rootView.findViewById(R.id.playerView);
                imageView = (ImageView) rootView.findViewById(R.id.imageViewBackDrop);
            }

            // get the saved step from bundle if it is there else
            if (savedInstanceState.containsKey("STEP")) {
                currentStep = (Step) savedInstanceState.getParcelable("STEP");

            }
        }
        catch (Exception ex) {
            Timber.e(ex);
        }
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPrepared() {
        videoView.start();
    }





    public  void getDataFromActivity (Step step) {
        textViewDescription.setText(step.getDescription());
        currentStep = step;
        showMedia();
    }

    private void setUpVideoView() {

        videoView.setVideoURI(videoUri);

        videoView.setOnPreparedListener(this);
    }

    private void showMedia() {
        try {
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

                Picasso.with(getContext()).load(R.drawable.media_unavailable)
                        .into(imageView);
            } else {
                videoView.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);

                Picasso.with(getContext()).load(currentStep.getThumbnailURL()).placeholder(R.drawable.loading_image)
                        .error(R.drawable.media_unavailable)
                        .into(imageView);
            }
        }
        catch (Exception ex) {
            Toast.makeText(getContext(), "An error has occured ", Toast.LENGTH_LONG).show();
            Timber.e(ex);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
       // no need to release the exo player the ExoMedia wrapper already does that
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("STEP", currentStep);
    }


}
