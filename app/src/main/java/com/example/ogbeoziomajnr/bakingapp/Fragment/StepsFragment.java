package com.example.ogbeoziomajnr.bakingapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ogbeoziomajnr.bakingapp.Activity.StepDetailActivity;
import com.example.ogbeoziomajnr.bakingapp.Adapter.StepsAdapter;
import com.example.ogbeoziomajnr.bakingapp.Activity.IngredientsAndStepsActivity;
import com.example.ogbeoziomajnr.bakingapp.Interface.StepsAdapterOnClickHandler;
import com.example.ogbeoziomajnr.bakingapp.Model.Step;
import com.example.ogbeoziomajnr.bakingapp.R;
import com.example.ogbeoziomajnr.bakingapp.Util.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import timber.log.Timber;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class StepsFragment extends Fragment implements StepsAdapterOnClickHandler {

    LinearLayoutManager layoutManager;
    StepsAdapter stepsAdapter;
    @BindView(R.id.recyclerview_ingredient)
    RecyclerView mRecyclerView;

    TextView textViewDescription;

    List<Step> steps = new ArrayList<>();

    Fragment fragmentStepDetail;

    public StepsFragment() {
        // Required empty public constructor
    }

    StepDetailFragment stepDetailFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepsAdapter = new StepsAdapter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_step);

        IngredientsAndStepsActivity parentActivity = (IngredientsAndStepsActivity) getActivity();
        steps = parentActivity.getSteps();

        layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

        stepsAdapter = new StepsAdapter(this);
        mRecyclerView.setAdapter(stepsAdapter);
        stepsAdapter.loadSteps(steps);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View view1 = view.findViewById(R.id.fake_landscape_marker);

        // if it is  a tablet then it means the tablet view would be available
        // also check for null to void being created twice
        if (Utility.isTablet(getContext()) && stepDetailFragment == null) {
             stepDetailFragment = new StepDetailFragment();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.child_fragment_container, stepDetailFragment).commit();
        }
    }

    @Override
    public void onClick(List<Step> steps, int position) {
        try {
            if (Utility.isTablet(getContext())) {

                // set the text view here because we are sure its there now
                stepDetailFragment.getDataFromActivity(steps.get(position));
            } else {
                Intent intent = new Intent(getActivity(), StepDetailActivity.class);
                intent.putParcelableArrayListExtra("STEPS", (ArrayList<Step>) steps);
                intent.putExtra("POSITION", position);
                startActivity(intent);
            }
        }
        catch (Exception ex) {
            Timber.e(ex);
        }
    }

}
