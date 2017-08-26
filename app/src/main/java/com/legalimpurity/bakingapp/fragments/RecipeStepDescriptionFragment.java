package com.legalimpurity.bakingapp.fragments;

import android.app.Activity;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.legalimpurity.bakingapp.R;
import com.legalimpurity.bakingapp.Utils.UrlUtils;
import com.legalimpurity.bakingapp.objects.Step;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepDescriptionFragment extends Fragment{


    public static RecipeStepDescriptionFragment newInstance(Step stepObj, boolean videoToStartOnInitialize) {
        RecipeStepDescriptionFragment fragment = new RecipeStepDescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_STEP_OBJ, stepObj);
        args.putBoolean(ARG_VIDEO_TO_BE_LOADED, videoToStartOnInitialize);
        fragment.setArguments(args);
        return fragment;
    }



    public static final String ARG_STEP_OBJ = "ARG_STEP_OBJ";
    public static final String ARG_VIDEO_TO_BE_LOADED = "ARG_VIDEO_TO_BE_LOADED";

    private Step step;
    private boolean videoPlayed = false;
    private boolean onCreateView = false;
    private SimpleExoPlayer mExoPlayer;

    @BindView(R.id.recipePlayerView)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.recipeImageView)
    ImageView imageView;

    @BindView(R.id.recipe_title)
    TextView recipe_title;

    @BindView(R.id.recipe_detail)
    TextView recipe_detail;

    @BindView(R.id.content_window)
    FrameLayout content_window;

    public RecipeStepDescriptionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_STEP_OBJ)) {
            step = getArguments().getParcelable(ARG_STEP_OBJ);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible && !videoPlayed && onCreateView && !TextUtils.isEmpty(step.getVideoURL())) {
            initializePlayer(getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);
        ButterKnife.bind(this,rootView);
        if (step != null) {
            recipe_detail.setText(step.getDescription());
            recipe_title.setText(step.getShortDescription());
        }

        if(!TextUtils.isEmpty(step.getVideoURL()))
        {

            if (getArguments().containsKey(ARG_VIDEO_TO_BE_LOADED)) {
                if(getArguments().getBoolean(ARG_VIDEO_TO_BE_LOADED))
                    initializePlayer(getActivity());
            }
        }
        else if(!TextUtils.isEmpty(step.getThumbnailURL()))
        {
            Picasso.with(getActivity())
                    .load(step.getThumbnailURL())
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });
            mPlayerView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);

        }
        else
        {
            content_window.setVisibility(View.GONE);
        }

        onCreateView = true;
        return rootView;
    }


    private void initializePlayer(FragmentActivity act) {
        if (mExoPlayer == null) {

            Uri.Builder b = Uri.parse(step.getVideoURL()).buildUpon();
            Uri mediaUri = b.build();

            mPlayerView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(act, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(new ExoPlayer.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity() {

                }
            });

            String userAgent = Util.getUserAgent(act, "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    act, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
            videoPlayed = true;
        }
    }

    private void releasePlayer() {
        if(mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
}
