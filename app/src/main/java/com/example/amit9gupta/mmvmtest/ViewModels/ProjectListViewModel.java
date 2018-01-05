package com.example.amit9gupta.mmvmtest.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.amit9gupta.mmvmtest.service.model.Project;
import com.example.amit9gupta.mmvmtest.service.repository.ProjectRepository;

import java.util.List;

/**
 * Created by amit9.gupta on 1/1/18.
 */

public class ProjectListViewModel extends AndroidViewModel {
    private final LiveData<List<Project>> projectListObservable;

    public ProjectListViewModel(Application application) {
        super(application);

        // If any transformation is needed, this can be simply done by Transformations class ...
        projectListObservable = ProjectRepository.getInstance().getProjectList("Google");
    }

    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<List<Project>> getProjectListObservable() {
        return projectListObservable;
    }
}