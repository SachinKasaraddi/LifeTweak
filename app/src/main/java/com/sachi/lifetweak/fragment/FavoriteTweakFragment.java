package com.sachi.lifetweak.fragment;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sachi.lifetweak.R;
import com.sachi.lifetweak.db.LifeTweaksStore;
import com.sachi.lifetweak.model.LifeTweak;
import com.sachi.lifetweak.util.LifeTweakConstants;
import com.squareup.picasso.Picasso;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;

/**
 * Created by jombay on 6/4/16.
 */
public class FavoriteTweakFragment extends Fragment {

    View rootView;
    Activity activity;
    private int position;
    private ImageView likeImage;
    private boolean liked;
    private SmallBang smallBang;

    public static FavoriteTweakFragment newInstance(int position) {
        FavoriteTweakFragment favoriteTweakFragment = new FavoriteTweakFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LifeTweakConstants.FRAGMENT_POSITION, position);
        favoriteTweakFragment.setArguments(bundle);

        return favoriteTweakFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        position = getArguments().getInt(LifeTweakConstants.FRAGMENT_POSITION);
        final LifeTweak lifeTweak = LifeTweaksStore.getInstance(getActivity()).getAllFavorites().get(position);
        rootView = inflater.inflate(R.layout.fragment_layout, container, false);
        smallBang = SmallBang.attach2Window(getActivity());
        TextView txtLifeTweaks = (TextView) rootView.findViewById(R.id.textview);
        txtLifeTweaks.setText(lifeTweak.getLifeHacks());
        TextView txtToday = (TextView) rootView.findViewById(R.id.txtDate);
        txtToday.setText("Today");
        ImageView imageView = (ImageView) rootView.findViewById(R.id.lifetweakImage);
        Picasso.with(getActivity()).load(lifeTweak.getImgUrl()).fit().into(imageView);
        likeImage = (ImageView) rootView.findViewById(R.id.like_button);
        if (LifeTweaksStore.getInstance(getActivity()).getIsFavorite(lifeTweak.getId()) == 1) {
            likeImage.setImageResource(R.drawable.heart_red);
            liked = true;
        } else {
            likeImage.setImageResource(R.drawable.heart);
            liked = false;
        }
        ImageView shareImage = (ImageView) rootView.findViewById(R.id.imgShare);
        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!liked) {
                    like(view);
                    LifeTweaksStore.getInstance(getActivity()).isFavorite(lifeTweak.getId(), 1);
                } else {
                    unLike(view);
                    LifeTweaksStore.getInstance(getActivity()).isFavorite(lifeTweak.getId(), 0);

                }
            }
        });
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto-Light.ttf");
        txtLifeTweaks.setTypeface(face);
        txtLifeTweaks.setTypeface(face);

        return rootView;

    }

    public void like(View view) {
        likeImage.setImageResource(R.drawable.heart_red);
        liked = true;
        smallBang.bang(view);
        smallBang.bang(view, 40, new SmallBangListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public void unLike(View view) {
        likeImage.setImageResource(R.drawable.heart);
        liked = false;
        smallBang.bang(view);
        smallBang.bang(view, 40, new SmallBangListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {
                try {
                    ((NotifyListner) activity).onFavoriteTweakUnliked();
                } catch (ClassCastException c) {

                }

            }
        });
    }

    public interface NotifyListner {

        public void onFavoriteTweakUnliked();
    }

}
