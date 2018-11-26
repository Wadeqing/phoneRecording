package com.sinocall.phonerecordera.ui.fragment;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.sinocall.phonerecordera.R;


/**


 */
public class LoadingViewPagerFragment extends FragmentBase {

    private Activity activity;
    private ImageView imageView;
    private int image;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    @Override
    public void initView() {
        setContentView(R.layout.loading_viewpager);
        imageView = (ImageView) contentView.findViewById(R.id.imageview_loading);
        imageView.setImageDrawable(ContextCompat.getDrawable(activity, image));
        imageView.setOnClickListener(this);
    }


}
