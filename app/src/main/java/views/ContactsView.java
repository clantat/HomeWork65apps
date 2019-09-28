package views;

import android.content.ContentProvider;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.MvpView;
import com.example.homework.Contact;
import com.example.homework.ContactsProvider;

import java.util.ArrayList;

public interface ContactsView extends MvpView {

    void setContacts(ContactsProvider contactsProvider);

}
