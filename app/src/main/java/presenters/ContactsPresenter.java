package presenters;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

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

import static android.content.ContentValues.TAG;

@InjectViewState
public class ContactsPresenter extends MvpPresenter<ContactsView> {
    private ContactsProvider contactsProvider;

    public void getContacts() {
        Log.i(TAG, "getContacts: Presenter");
        getViewState().setContacts(contactsProvider);
    }

    @Override
    public void onDestroy() {
        contactsProvider.unsubscribe();
        super.onDestroy();
    }
}
