package views;

import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;

public interface InfoView extends MvpView {
    void showInfo(String name, String phone, String email, Bitmap bitmap);
}
