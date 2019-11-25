package com.example.homework.presentation.presenters;

import com.example.homework.domain.interactor.ContactsInteractor;
import com.example.homework.domain.model.ShortContact;
import com.example.homework.presentation.views.ContactsView;
import com.example.homework.request.RequestReadContact;
import com.example.homework.stuff.TrampolineScheduler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactsPresenterTest {

    @Mock
    private ContactsInteractor contactsInteractor;
    @Mock
    private ContactsView contactsView;
    private ContactsPresenter contactsPresenter;
    private List<ShortContact> shortContactList;
    @Mock
    private RequestReadContact requestPermissionFragment;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // Given
        TrampolineScheduler trampolineScheduler = new TrampolineScheduler();
        shortContactList = new ArrayList<>();
        shortContactList.add(new ShortContact("228", "Rob", "1488"));
        shortContactList.add(new ShortContact("322", "Robastick", "1488322"));
        shortContactList.add(new ShortContact("1488", "Noname", "88005553535"));
        // contactsInteractor should return Single List ShortContact
        when(contactsInteractor.getContacts("")).thenReturn(Single.just(shortContactList));
        contactsPresenter = new ContactsPresenter(contactsInteractor, trampolineScheduler);
        // requestPermissionFragment should return true Permission for our request contacts
        when(requestPermissionFragment.getReadContactPermission()).thenReturn(true);
        contactsPresenter.setRequestReadContact(requestPermissionFragment);
        contactsPresenter.attachView(contactsView);
    }

    @Test
    public void shouldGivePermissionToSetContactsWithShowAndHideLoading() {
        // When call getContacts with "" name
        contactsPresenter.getContacts("");
        // Then it should give permission to show loading contacts, set contacts and hide loading
        verify(contactsView).onRequestPermission(requestPermissionFragment);
        verify(contactsView).showLoading();
        verify(contactsView).setContacts(shortContactList);
        verify(contactsView).hideLoading();
    }

}