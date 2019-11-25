package com.example.homework.domain.interactor;

import com.example.homework.domain.model.ShortContact;
import com.example.homework.domain.repository.ContactsRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

public class ContactsInteractorTest {

    private ContactsInteractor contactsInteractor;
    @Mock
    private ContactsRepository contactsRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        contactsInteractor = new ContactsInteractorImpl(contactsRepository);
    }

    @Test
    public void itShouldReturnListWithShortContactName() {
        // Given TestObserver and List<ShortContact> where one ShortContact
        TestObserver<List<ShortContact>> testObserver = TestObserver.create();
        List<ShortContact> list = new ArrayList<>();
        list.add(new ShortContact("228", "Rob", "1488"));
        // contactsRepository should return Single<List<ShortContact>> where "Rob" is a searchText
        when(contactsRepository.getContacts("Rob")).thenReturn(Single.just(list));
        // When call getContacts with "Rob" name
        contactsInteractor.getContacts("Rob").subscribe(testObserver);
        // Then it should return List<ShortContact> and completed without errors
        testObserver
                .assertSubscribed()
                .assertValue(list)
                .assertComplete()
                .assertNoErrors()
                .dispose();
    }

    @Test
    public void itShouldReturnListWithAllShortContacts() {
        // Given TestObserver and List<ShortContact> where all ShortContacts
        TestObserver<List<ShortContact>> testObserver = TestObserver.create();
        List<ShortContact> shortContactList = new ArrayList<>();
        shortContactList.add(new ShortContact("228", "Rob", "1488"));
        shortContactList.add(new ShortContact("322", "Robastick", "1488322"));
        shortContactList.add(new ShortContact("1488", "Noname", "88005553535"));
        // contactsRepository should return with all ShortContacts Single<List<ShortContact>> where "" is a searchText
        when(contactsRepository.getContacts("")).thenReturn(Single.just(shortContactList));
        // When call getContacts with "" name
        contactsInteractor.getContacts("").subscribe(testObserver);
        // Then it should return List<ShortContact> with all ShortContacts and completed without errors
        testObserver
                .assertSubscribed()
                .assertValue(shortContactList)
                .assertComplete()
                .assertNoErrors()
                .dispose();
    }
}