package com.medetzhakupov.githublisting.ui.home;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.medetzhakupov.githublisting.R;
import com.medetzhakupov.githublisting.model.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Medet Zhakupov.
 */
public class GithubUsersAdapter extends RecyclerView.Adapter<GithubUsersAdapter.UserViewHolder> {

    private final UserClickedListener listener;
    private final List<User> data = new ArrayList<>();

    @Inject
    GithubUsersAdapter(UserClickedListener listener) {
        this.listener = listener;
        setHasStableIds(true);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new UserViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).id;
    }

    void setData(List<User> repos) {
        if (repos != null) {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new RepoDiffCallback(data, repos));
            data.clear();
            data.addAll(repos);
            diffResult.dispatchUpdatesTo(this);
        } else {
            data.clear();
            notifyDataSetChanged();
        }
    }

    static final class UserViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.stuff) TextView stuff;

        private User user;

        UserViewHolder(View itemView, UserClickedListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (user != null) {
                    listener.onRepoClicked(user);
                }
            });
        }

        void bind(User user) {
            this.user = user;
            Glide.with(itemView)
                    .load(user.avatarUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageView);
            name.setText(user.login);
            stuff.setText(user.siteAdim ? "Stuff": "");
        }
    }

    public interface UserClickedListener {

        void onRepoClicked(User user);
    }
}
