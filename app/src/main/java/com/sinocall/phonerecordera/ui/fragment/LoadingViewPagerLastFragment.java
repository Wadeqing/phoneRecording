package com.sinocall.phonerecordera.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sinocall.phonerecordera.R;
import com.sinocall.phonerecordera.ui.activity.MainActivity;
import com.sinocall.phonerecordera.util.Constants;
import com.sinocall.phonerecordera.util.SPUtils;


/**
 */
public class LoadingViewPagerLastFragment extends FragmentBase {

    private Activity activity;
    private ImageView imageView;
    private Button button;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void initView() {
        setContentView(R.layout.fragment_loading_last_viewpager);
        button = (Button) contentView.findViewById(R.id.button_loading_feel);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_loading_feel:
                Intent intent = new Intent();
                intent.setClass(activity, MainActivity.class);
                SPUtils.put(getActivity(), Constants.FIRST_USING, false);
                startActivity(intent);
                activity.finish();
                break;
            default:
                break;
        }
    }
}
