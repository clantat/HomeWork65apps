package presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.homework.ContactsProvider;

import views.ContactsView;

import static android.content.ContentValues.TAG;

@InjectViewState
public class ContactsPresenter extends MvpPresenter<ContactsView> {
    private ContactsProvider contactsProvider;

    public ContactsPresenter(ContactsProvider contactsProvider) {
        this.contactsProvider = contactsProvider;
    }

    public void getContacts() {
        getViewState().setContacts(contactsProvider.getContacts());
    }

    @Override
    public void onDestroy() {
        contactsProvider.unsubscribe();
        contactsProvider = null;
        super.onDestroy();
    }
}
