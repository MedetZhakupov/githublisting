package com.medetzhakupov.githublisting.ui.detail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.medetzhakupov.githublisting.R;
import com.medetzhakupov.githublisting.ui.BaseActivity;
import com.medetzhakupov.githublisting.ui.home.GithubUsersAdapter;
import com.medetzhakupov.githublisting.ui.home.MainViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class DetailActivity extends BaseActivity {

    public static final String USERNAME = "username";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    DetailViewModel viewModel;

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.blog)
    TextView blog;
    @BindView(R.id.loading_frame)
    View loadingView;
    @BindView(R.id.error_text)
    TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel.class);
        String username = getIntent().getStringExtra(USERNAME);
        viewModel.setUsername(username);
        getSupportActionBar().setTitle(username);
    }

    @OnClick(R.id.blog)
    public void onBlogClick(TextView textView) {
        String url = textView.getText().toString();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    protected Disposable[] subscriptions() {
        return new Disposable[]{
                viewModel.loading()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(loading -> {
                    loadingView.setVisibility(loading ? View.VISIBLE : View.GONE);
                    errorText.setVisibility(loading ? View.GONE : errorText.getVisibility());
                }),
                viewModel.userDetail()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(usersDetail -> {
                    Glide.with(this)
                            .load(usersDetail.avatarUrl)
                            .apply(RequestOptions.circleCropTransform())
                            .into(image);
                    name.setText(usersDetail.name);
                    if (usersDetail.siteAdmin) {
                        username.setText(String.format("%s \n %s", usersDetail.login, "Stuff"));
                    } else {
                        username.setText(usersDetail.login);
                    }
                    location.setText(usersDetail.location);
                    blog.setText(usersDetail.blog);
                }),
                viewModel.error()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(errorRes -> {
                    if (errorRes == -1) {
                        errorText.setText(null);
                        errorText.setVisibility(View.GONE);
                    } else {
                        errorText.setVisibility(View.VISIBLE);
                        errorText.setText(errorRes);
                    }
                })
        };
    }
}
