package com.sinocall.phonerecordera.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sinocall.phonerecordera.R;


public class PopupWindowUtil {
    public static PopupWindow popupWindow;

    public static void showPopupWindow(final Context context, View.OnClickListener onClickListener, final View parent) {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        View view = LinearLayout.inflate(context,
                R.layout.call_popupwindow, null);
        popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.Widget_AppCompat_PopupWindow);
        popupWindow.setClippingEnabled(false);
//        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        Button buttonOne = (Button) view.findViewById(R.id.button_one);
        Button buttonTwo = (Button) view.findViewById(R.id.button_two);
        Button buttonThree = (Button) view.findViewById(R.id.button_three);
        Button buttonFour = (Button) view.findViewById(R.id.button_four);
        Button buttonFive = (Button) view.findViewById(R.id.button_five);
        Button buttonSix = (Button) view.findViewById(R.id.button_six);
        Button buttonSeven = (Button) view.findViewById(R.id.button_seven);
        Button buttonEight = (Button) view.findViewById(R.id.button_eight);
        Button buttonNine = (Button) view.findViewById(R.id.button_nine);
        Button buttonZero = (Button) view.findViewById(R.id.button_zero);
        Button buttonStars = (Button) view.findViewById(R.id.button_stars);
        Button buttonPound = (Button) view.findViewById(R.id.button_pound);
        final TextView PhoneNum = (TextView) view.findViewById(R.id.textview_phone_num);
        ImageView close = (ImageView) view.findViewById(R.id.imageview_close_popupwindow);

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder phoneNum = new StringBuilder(PhoneNum.getText().toString());
                phoneNum.append("1");
                PhoneNum.setText(phoneNum);
            }
        });
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder phoneNum = new StringBuilder(PhoneNum.getText().toString());
                phoneNum.append("2");
                PhoneNum.setText(phoneNum);
            }
        });
        buttonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder phoneNum = new StringBuilder(PhoneNum.getText().toString());
                phoneNum.append("3");
                PhoneNum.setText(phoneNum);
            }
        });
        buttonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder phoneNum = new StringBuilder(PhoneNum.getText().toString());
                phoneNum.append("4");
                PhoneNum.setText(phoneNum);
            }
        });
        buttonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder phoneNum = new StringBuilder(PhoneNum.getText().toString());
                phoneNum.append("5");
                PhoneNum.setText(phoneNum);
            }
        });
        buttonSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder phoneNum = new StringBuilder(PhoneNum.getText().toString());
                phoneNum.append("6");
                PhoneNum.setText(phoneNum);
            }
        });
        buttonSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder phoneNum = new StringBuilder(PhoneNum.getText().toString());
                phoneNum.append("7");
                PhoneNum.setText(phoneNum);
            }
        });
        buttonEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder phoneNum = new StringBuilder(PhoneNum.getText().toString());
                phoneNum.append("8");
                PhoneNum.setText(phoneNum);
            }
        });
        buttonNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder phoneNum = new StringBuilder(PhoneNum.getText().toString());
                phoneNum.append("9");
                PhoneNum.setText(phoneNum);
            }
        });
        buttonZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder phoneNum = new StringBuilder(PhoneNum.getText().toString());
                phoneNum.append("0");
                PhoneNum.setText(phoneNum);
            }
        });
        buttonStars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder phoneNum = new StringBuilder(PhoneNum.getText().toString());
                phoneNum.append("*");
                PhoneNum.setText(phoneNum);
            }
        });
        buttonPound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder phoneNum = new StringBuilder(PhoneNum.getText().toString());
                phoneNum.append("#");
                PhoneNum.setText(phoneNum);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder phoneNum = new StringBuilder(PhoneNum.getText().toString());
                if (phoneNum.length() >= 1) {
                    String delete = phoneNum.delete(phoneNum.length() - 1, phoneNum.length()).toString();
                    PhoneNum.setText(delete);
                } else if (phoneNum.length() == 0) {
                    PhoneNum.setHint(context.getString(R.string.popupwindow_hint));
                    dismissPopupWindow();
                }
            }
        });
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        int windowHeigh = AppUtil.getWindowHeigh(context);
        popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, location[0], (windowHeigh - UIUtils.dip2px(422)));

    }

    public static void dismissPopupWindow() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

}
