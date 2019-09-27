package presenters;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.Contact;
import com.example.homework.ContactsProvider;
import com.example.homework.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import views.ContactsView;

@InjectViewState
public class ContactsPresenter extends MvpPresenter<ContactsView> {
    private ContactsProvider contactsProvider;
    public void getContacts() {
        getViewState().setContacts(contactsProvider.getContacts());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        contactsProvider.unsubscribe();
    }

    //TODO вынести ContentResolver отдельно

}
