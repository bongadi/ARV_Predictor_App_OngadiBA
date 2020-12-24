package co.ke.ikocare.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import co.ke.ikocare.R;
import co.ke.ikocare.adapters.MyAnalysisAdapter;
import co.ke.ikocare.type.SequenceAnalysis;

public class DrugAnalysis extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;

    public DrugAnalysis() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drug_analysis, container, false);

        viewPager = view.findViewById(R.id.analysis_viewpager);
        tabLayout = view.findViewById(R.id.analysis_tab);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setUpViewPager(ViewPager viewPager) {

        MyAnalysisAdapter adapter = new MyAnalysisAdapter(getChildFragmentManager());
        adapter.addFragment(new MutationAnalysisi(), "Input Mutations");
        adapter.addFragment(new SequenceAnalysis(), "Input Sequence");
        viewPager.setAdapter(adapter);

    }
}