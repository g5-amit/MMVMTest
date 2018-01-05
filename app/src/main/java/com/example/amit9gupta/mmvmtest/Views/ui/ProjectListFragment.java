package com.example.amit9gupta.mmvmtest.Views.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amit9gupta.mmvmtest.R;
import com.example.amit9gupta.mmvmtest.ViewModels.ProjectListViewModel;
import com.example.amit9gupta.mmvmtest.Views.adapters.ProjectAdapter;
import com.example.amit9gupta.mmvmtest.Views.callback.ProjectClickCallback;
import com.example.amit9gupta.mmvmtest.databinding.FragmentProjectListBinding;
import com.example.amit9gupta.mmvmtest.service.model.Project;

import java.util.List;

/**
 * Created by amit9.gupta on 1/1/18.
 */

public class ProjectListFragment extends LifecycleFragment {
    public static final String TAG = "ProjectListFragment";
    private ProjectAdapter projectAdapter;
    private FragmentProjectListBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false);

        projectAdapter = new ProjectAdapter(projectClickCallback);
        binding.projectList.setAdapter(projectAdapter);
        binding.setIsLoading(true);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // Use ViewModelProviders to get last viewmodel instance while device is rotated
        // and fragment or activity recreated to maintian the current state of viewmodel
        final ProjectListViewModel viewModel =
                ViewModelProviders.of(this).get(ProjectListViewModel.class);

        observeViewModel(viewModel);
    }

    private void observeViewModel(ProjectListViewModel viewModel) {
        // Update the list when the data changes
        /*
        * Use Livedata observer for observing data change since LiveData is activity/fragment lifecycle
        * aware component.
        *
        **/
        viewModel.getProjectListObservable().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> projects) {
                if (projects != null) {
                    binding.setIsLoading(false);
                    projectAdapter.setProjectList(projects);
                }
            }
        });
    }

    private final ProjectClickCallback projectClickCallback = new ProjectClickCallback() {
        @Override
        public void onClick(Project project) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((TestActivity) getActivity()).show(project);
            }
        }
    };
}
