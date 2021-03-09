package com.rightcode.unite.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.rightcode.unite.R;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class ImageWrapper {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------


    public static void load(Context context, String imageUrl, ImageView imageView) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void load(Context context, int imageResId, ImageView imageView) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).load(imageResId)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void load(Context context, File file, ImageView imageView) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).load(file)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
    }


    public static void load(Context context, String imageUrl, ImageView imageView, int errorResId) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(errorResId)
                .into(imageView);
    }

    public static void load(Context context, String imageUrl, ImageView imageView, RequestListener listener) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .listener(listener)
                .into(imageView);
    }


    public static void loadCenterCrop(Context context, String imageUrl, ImageView imageView) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .into(imageView);
    }

    public static void loadCenterCrop(Context context, Drawable imageResId, ImageView imageView) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).load(imageResId)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .into(imageView);
    }

    public static void loadCenterCrop(Context context, int imageResId, ImageView imageView) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).load(imageResId)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .into(imageView);
    }

    public static void loadFitCenter(Context context, int imageUrl, ImageView imageView) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter()
                .into(imageView);
    }

    public static void loadFitCenter(Context context, String imageUrl, ImageView imageView) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter()
                .into(imageView);
    }

    public static void loadFitCenter(Context context, String imageUrl, ImageView imageView, int errorResId) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).load(imageUrl)
                .error(errorResId)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter()
                .into(imageView);
    }

    public static void loadDontAnimate(Context context, String imageUrl, ImageView imageView) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).asBitmap().load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    public static void loadDontAnimate(Context context, String imageUrl, ImageView imageView, int errorResId) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }
        Glide.with(context).asBitmap().load(imageUrl)
                .error(errorResId)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).dontAnimate().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    public static void loadCircle(Context context, int imageUrl, ImageView imageView) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }
        Glide.with(context).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .circleCrop()
                .into(imageView);
    }

    public static void load(Context context, String file, ImageView imageView, int width, int height) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).load(file)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(width, height)
                .skipMemoryCache(true)
                .into(imageView);
    }
    public static void load(Context context, File file, ImageView imageView, int width, int height) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).load(file)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(width, height)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void loadCircle(Context context, String imageUrl, ImageView imageView) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }
        Glide.with(context).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .circleCrop()
                .into(imageView);
    }

    public static void loadCircle(Context context, String imageUrl, ImageView imageView, int errorResId) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .circleCrop()
                .error(errorResId)
                .into(imageView);
    }

    public static void loadRound(Context context, int imageUrl, ImageView imageView, int dimen) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }
        Glide.with(context).asBitmap().load(imageUrl).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap bitmap) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
                circularBitmapDrawable.setCornerRadius(context.getResources().getDimensionPixelSize(dimen));
                circularBitmapDrawable.setAntiAlias(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }


    public static void loadRound(Context context, int imageUrl, ImageView imageView, int width, int height) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }
        Glide.with(context).asBitmap().load(imageUrl).override(width, height).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap bitmap) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
                circularBitmapDrawable.setCornerRadius(context.getResources().getDimensionPixelSize(R.dimen.image_corner_radius_8));
                circularBitmapDrawable.setAntiAlias(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void loadRound(Context context, String imageUrl, ImageView imageView) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).asBitmap().load(imageUrl).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap bitmap) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
                circularBitmapDrawable.setCornerRadius(context.getResources().getDimensionPixelSize(R.dimen.image_corner_radius_8));
                circularBitmapDrawable.setAntiAlias(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }


    public static void loadRound(Context context, String imageUrl, ImageView imageView, int dimen) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).asBitmap().load(imageUrl).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap bitmap) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
                circularBitmapDrawable.setCornerRadius(context.getResources().getDimensionPixelSize(dimen));
                circularBitmapDrawable.setAntiAlias(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void loadRound(Context context, String imageUrl, ImageView imageView, int dimen, int errorResId) {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return;
        }

        Glide.with(context).asBitmap().load(imageUrl).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap bitmap) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
                circularBitmapDrawable.setCornerRadius(context.getResources().getDimensionPixelSize(dimen));
                circularBitmapDrawable.setAntiAlias(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static Bitmap getBitmap(Context context, String imageUrl, int width, int height) throws ExecutionException, InterruptedException {
        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing())) {
            return null;
        }

        return Glide.with(context).asBitmap().load(imageUrl).into(width, height).get();
    }

    public static void clear(ImageView imageView) {
        try {
            Glide.with(imageView.getContext()).clear(imageView);
        } catch (Exception e) {
        }
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            if (drawable instanceof RoundedBitmapDrawable) {
                Bitmap bitmap = ((RoundedBitmapDrawable) drawable).getBitmap();
                bitmap.recycle();
            }
            drawable.setCallback(null);
        }

        imageView.setImageDrawable(null);
    }

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
