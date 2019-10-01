package presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.ContactsProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import views.ContactsView;

import static android.content.ContentValues.TAG;

@InjectViewState
public class ContactsPresenter extends MvpPresenter<ContactsView> {
    private ContactsProvider contactsProvider;
    private Disposable disposable;

    public ContactsPresenter(ContactsProvider contactsProvider) {
        this.contactsProvider = contactsProvider;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().onRequestPermission();
    }

    public void getContacts() {
        Log.i(TAG, "getContacts: before disposable");
        disposable = contactsProvider.getContacts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                    for (int i = 0; i <item.size() ; i++) {
                        Log.i(TAG, "getContacts: ViewState().setContacts(): item name: " + item.get(i).getName());
                    }
                    getViewState().setContacts(item);
                });
        Log.i(TAG, "getContacts: after disposable");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
