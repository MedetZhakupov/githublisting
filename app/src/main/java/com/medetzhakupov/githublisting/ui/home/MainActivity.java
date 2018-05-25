package com.medetzhakupov.githublisting.ui.home;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.medetzhakupov.githublisting.R;
import com.medetzhakupov.githublisting.model.User;
import com.medetzhakupov.githublisting.ui.BaseActivity;
import com.medetzhakupov.githublisting.ui.Navigator;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity implements GithubUsersAdapter.UserClickedListener {

    private static final String KEY_RECYCLER_STATE = "key_recycler_state";
    @Inject
    Navigator navigator;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    GithubUsersAdapter adapter;

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.loading_frame)
    View loadingView;
    @BindView(R.id.error_text)
    TextView errorText;

    MainViewModel viewModel;
    Bundle state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        state = savedInstanceState;
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);
        list.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_RECYCLER_STATE, list.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected Disposable[] subscriptions() {
        return new Disposable[]{
                viewModel.loading()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(loading -> {
                    loadingView.setVisibility(loading ? View.VISIBLE : View.GONE);
                    list.setVisibility(loading ? View.GONE : View.VISIBLE);
                    errorText.setVisibility(loading ? View.GONE : errorText.getVisibility());
                }),
                viewModel.users()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(users -> {
                    ((GithubUsersAdapter)list.getAdapter()).setData(users);
                    if (state != null) {
                        list.getLayoutManager().onRestoreInstanceState(state.getParcelable(KEY_RECYCLER_STATE));
                        state = null;
                    }
                }),
                viewModel.error()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(errorRes -> {
                    if (errorRes == -1) {
                        errorText.setText(null);
                        errorText.setVisibility(View.GONE);
                    } else {
                        errorText.setVisibility(View.VISIBLE);
                        list.setVisibility(View.GONE);
                        errorText.setText(errorRes);
                    }
                })
        };
    }

    @Override
    public void onRepoClicked(User user) {
        navigator.navigateToDetails(user.login);
    }
}
