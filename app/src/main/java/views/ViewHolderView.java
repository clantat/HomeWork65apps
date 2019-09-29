package views;

import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;

public interface ViewHolderView extends MvpView {
    void setName(String name);
    void setPhone(String phone);
    void setImage(Bitmap bitmap);
}
