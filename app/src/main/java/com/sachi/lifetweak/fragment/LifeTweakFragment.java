package com.sachi.lifetweak.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;

/**
 * Created by jombay on 28/3/16.
 */
public class LifeTweakFragment extends Fragment {

    View rootView;

    private int position;
    private SmallBang smallBang;
    private ImageView likeImage;
    private boolean liked;

    public static LifeTweakFragment newInstance(int position) {
        LifeTweakFragment lifeTweakFragment = new LifeTweakFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LifeTweakConstants.FRAGMENT_POSITION, position);
        lifeTweakFragment.setArguments(bundle);

        return lifeTweakFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (LifeTweaksStore.getInstance(getActivity()).getIsFavorite(position) == 0) {

        } else {

            Log.d("UnLike :" + position, "Value :" + LifeTweaksStore.getInstance(getActivity()).getIsFavorite(position));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        position = getArguments().getInt(LifeTweakConstants.FRAGMENT_POSITION);
        final LifeTweak lifeTweak = LifeTweaksStore.getInstance(getActivity()).getAllLifeTweaks().get(position);
        rootView = inflater.inflate(R.layout.fragment_layout, container, false);
        smallBang = SmallBang.attach2Window(getActivity());
        TextView txtLifeTweaks = (TextView) rootView.findViewById(R.id.textview);
        txtLifeTweaks.setText(lifeTweak.getLifeHacks());
        TextView txtToday = (TextView) rootView.findViewById(R.id.txtDate);
        txtToday.setText("Today");
        ImageView imageView = (ImageView) rootView.findViewById(R.id.lifetweakImage);
        Picasso.with(getActivity()).load(lifeTweak.getImgUrl()).fit().into(imageView);
        ImageView shareImage = (ImageView) rootView.findViewById(R.id.imgShare);
        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeScreenshot();
            }
        });
        likeImage = (ImageView) rootView.findViewById(R.id.like_button);
        if (LifeTweaksStore.getInstance(getActivity()).getIsFavorite(lifeTweak.getId()) == 1) {
            likeImage.setImageResource(R.drawable.heart_red);
            liked = true;
        } else {
            likeImage.setImageResource(R.drawable.heart);
            liked = false;
        }
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

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/PICTURES/Screenshots/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getActivity().getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();


            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{imageFile.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        Uri uriFile = Uri.fromFile(imageFile);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriFile);
        shareIntent.setType("image/jpg");
        startActivity(Intent.createChooser(shareIntent, "Share via"));

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

            }
        });
    }
}
