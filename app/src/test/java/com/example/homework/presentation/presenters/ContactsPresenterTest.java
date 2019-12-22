package com.example.homework.presentation.presenters;

import com.example.homework.data.provider.ContactsProvider;
import com.example.homework.data.repository.ContactsRepositoryImpl;
import com.example.homework.domain.interactor.ContactsInteractor;
import com.example.homework.domain.interactor.ContactsInteractorImpl;
import com.example.homework.domain.model.ShortContact;
import com.example.homework.domain.repository.ContactsRepository;
import com.example.homework.presentation.views.ContactsView;
import com.example.homework.request.RequestReadContact;
import com.example.homework.stuff.TrampolineScheduler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactsPresenterTest {


    private ContactsInteractor contactsInteractor;
    private ContactsRepository contactsRepository;
    @Mock
    private ContactsProvider contactsProvider;
    @Mock
    private ContactsView contactsView;
    private ContactsPresenter contactsPresenter;
    private List<ShortContact> shortContactList;
    @Mock
    private RequestReadContact requestPermissionFragment;
    private TrampolineScheduler trampolineScheduler;

    @Before
    public void setUp() throws Exception {
        // Given
        contactsRepository = new ContactsRepositoryImpl(contactsProvider);
        contactsInteractor = new ContactsInteractorImpl(contactsRepository);
        trampolineScheduler = new TrampolineScheduler();
        shortContactList = new ArrayList<>();
        shortContactList.add(new ShortContact("228", "Rob", "1488"));
        shortContactList.add(new ShortContact("322", "Robastick", "1488322"));
        shortContactList.add(new ShortContact("1488", "Noname", "88005553535"));


    }

    @Test
    public void shouldGivePermissionToSetContactsWithShowAndHideLoading() {
        // requestPermissionFragment should return true Permission for our request contacts
        when(requestPermissionFragment.getReadContactPermission()).thenReturn(true);
        // contactsProvider should return Single List ShortContact\
        when(contactsProvider.getContacts("")).thenReturn(Single.just(shortContactList));
        contactsPresenter = new ContactsPresenter(contactsInteractor, trampolineScheduler);
        contactsPresenter.setRequestReadContact(requestPermissionFragment);
        contactsPresenter.attachView(contactsView);
        // When call getContacts with "" name
        contactsPresenter.getContacts("");
        // Then it should give permission to show loading contacts, set contacts and hide loading
        verify(contactsView).onRequestPermission(requestPermissionFragment);
        verify(contactsView).showLoading();
        verify(contactsView).setContacts(shortContactList);
        verify(contactsView).hideLoading();
    }

    @Test
    public void shouldShowOnErrorWhenFalseReadPermission() {
        // requestPermissionFragment should return false Permission for our request contacts
        when(requestPermissionFragment.getReadContactPermission()).thenReturn(false);
        contactsPresenter = new ContactsPresenter(contactsInteractor, trampolineScheduler);
        contactsPresenter.setRequestReadContact(requestPermissionFragment);
        contactsPresenter.attachView(contactsView);
        // When call getContacts with "" name
        contactsPresenter.getContacts("");
        // Then it should show error
        verify(contactsView).onError("Please, give read permission");
    }

}