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
import com.example.amit9gupta.mmvmtest.ViewModels.ProjectViewModel;
import com.example.amit9gupta.mmvmtest.databinding.FragmentProjectDetailsBinding;
import com.example.amit9gupta.mmvmtest.service.model.Project;

/**
 * Created by amit9.gupta on 1/1/18.
 */

public class ProjectFragment extends LifecycleFragment {
    private static final String KEY_PROJECT_ID = "project_id";
    private FragmentProjectDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_details, container, false);

        // Create and set the adapter for the RecyclerView.
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProjectViewModel.Factory factory = new ProjectViewModel.Factory(
                getActivity().getApplication(), getArguments().getString(KEY_PROJECT_ID));

        final ProjectViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(ProjectViewModel.class);

        binding.setProjectViewModel(viewModel);
        binding.setIsLoading(true);

        observeViewModel(viewModel);
    }

    private void observeViewModel(final ProjectViewModel viewModel) {

        /**
         * Adds the given observer to the observers list within the lifespan of the given
         * owner. The events are dispatched on the main thread. If LiveData already has data
         * set, it will be delivered to the observer.
         * <p>
         * The observer will only receive events if the activity/fragment is in {@link Lifecycle.State#STARTED}
         * or {@link Lifecycle.State#RESUMED} state (active).
         * <p>
         * If the owner moves to the {@link Lifecycle.State#DESTROYED} state, the observer will
         * automatically be removed.
         * <p>
         * When data changes while the {@code owner} is not active, it will not receive any updates.
         * If it becomes active again, it will receive the last available data automatically.
         * <p>
         * LiveData keeps a strong reference to the observer and the owner as long as the
         * given LifecycleOwner is not destroyed. When it is destroyed, LiveData removes references to
         * the observer &amp; the owner.
         * <p>
         * If the given owner is already in {@link Lifecycle.State#DESTROYED} state, LiveData
         * ignores the call.
         * <p>
         * If the given owner, observer tuple is already in the list, the call is ignored.
         * If the observer is already in the list with another owner, LiveData throws an
         * {@link IllegalArgumentException}.
         *
         * @param owner    The LifecycleOwner which controls the observer
         * @param observer The observer that will receive the events
         */

        // Observe project data
        viewModel.getObservableProject().observe(this, new Observer<Project>() {
            @Override
            public void onChanged(@Nullable Project project) {
                if (project != null) {
                    binding.setIsLoading(false);
                    viewModel.setProject(project);
                }
            }
        });
    }

    /** Creates project fragment for specific project ID */
    public static ProjectFragment forProject(String projectID) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();

        args.putString(KEY_PROJECT_ID, projectID);
        fragment.setArguments(args);

        return fragment;
    }
}
