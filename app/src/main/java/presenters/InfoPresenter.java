package presenters;

import android.graphics.Bitmap;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import views.InfoView;

@InjectViewState
public class InfoPresenter extends MvpPresenter<InfoView> {
    private String name;
    private String phone;
    private String email;
    private Bitmap bitmap;

    public InfoPresenter(String name, String phone, String email, Bitmap bitmap) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.bitmap = bitmap;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showInfo(name, phone, email, bitmap);
    }
}
