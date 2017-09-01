package com.example.ogbeoziomajnr.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ogbeoziomajnr.bakingapp.Interface.StepsAdapterOnClickHandler;
import com.example.ogbeoziomajnr.bakingapp.Model.Step;
import com.example.ogbeoziomajnr.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SQ-OGBE PC on 05/07/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {

    private final StepsAdapterOnClickHandler mClickHandler;
    Context context;
    List<Step> steps;




    public StepsAdapter(StepsAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutForItem = R.layout.step_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutForItem, parent, false);
        StepViewHolder viewHolder = new StepViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (steps == null) {
            return 0;
        }
        return steps.size();
    }

    public void loadSteps(List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewStepNumber;
        TextView textViewShortDescription;

        public StepViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewStepNumber = (TextView) itemView.findViewById(R.id.textview_stepNumberr);
            textViewShortDescription = (TextView) itemView.findViewById(R.id.textview_step_shortDescription);
        }

        void bind(int position) {
            if (steps != null) {
                if (position != 0) {
                    textViewStepNumber.setText(String.valueOf(position));
                }
                textViewShortDescription.setText(steps.get(position).getShortDescription());
            }
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(steps,adapterPosition);
        }
    }
}
