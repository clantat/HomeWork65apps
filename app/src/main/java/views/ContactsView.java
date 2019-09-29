package views;

import com.arellomobile.mvp.MvpView;
import com.example.homework.Contact;

import java.util.ArrayList;

public interface ContactsView extends MvpView {

    void setContacts(ArrayList<Contact> arrayList);

}
