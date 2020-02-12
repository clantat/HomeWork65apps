package com.example.homework.presentation.presenters;

import com.example.homework.core.Screens;
import com.example.homework.data.provider.ContactsProvider;
import com.example.homework.data.repository.ContactsRepositoryImpl;
import com.example.homework.domain.interactor.InfoInteractor;
import com.example.homework.domain.interactor.InfoInteractorImpl;
import com.example.homework.domain.model.Contact;
import com.example.homework.domain.repository.ContactsRepository;
import com.example.homework.presentation.views.InfoView;
import com.example.homework.request.RequestReadContact;
import com.example.homework.stuff.TrampolineScheduler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import ru.terrakok.cicerone.Router;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InfoPresenterTest {

    private InfoPresenter infoPresenter;
    private InfoInteractor infoInteractor;
    private Contact contact;

    @Mock
    private Router router;
    @Mock
    private ContactsProvider contactsProvider;
    @Mock
    private InfoView infoView;
    @Mock
    private RequestReadContact readContactRequest;
    private TrampolineScheduler trampolineScheduler;

    @Before
    public void setUp() {
        // Given
        ContactsRepository contactsRepository = new ContactsRepositoryImpl(contactsProvider);
        infoInteractor = new InfoInteractorImpl(contactsRepository);
        trampolineScheduler = new TrampolineScheduler();
        contact = new Contact("228", "Rob", "1488", "hello@gmail.ru", null);
    }


    @Test
    public void getContactWhenRequestReadContactTrue() {
        // readContactRequest should return true Permission for our request contacts
        when(readContactRequest.getReadContactPermission()).thenReturn(true);
        // contactsProvider should return Single<Contact>
        when(contactsProvider.getInfoContact("228")).thenReturn(Single.just(contact));
        infoPresenter = new InfoPresenter(infoInteractor, "228", trampolineScheduler, router);
        infoPresenter.setRequestReadContact(readContactRequest);
        infoPresenter.attachView(infoView);
        // When call infoPresenter init()
        infoPresenter.init();
        // Then it should give permission to show contact info
        verify(infoView).onRequestPermission(readContactRequest);
        verify(infoView).showInfo(contact);
    }

    @Test
    public void getErrorWhenRequestReadContactFalse() {
        // readContactRequest should return true Permission for our request contacts
        when(readContactRequest.getReadContactPermission()).thenReturn(false);
        infoPresenter = new InfoPresenter(infoInteractor, "228", trampolineScheduler, router);
        infoPresenter.setRequestReadContact(readContactRequest);
        infoPresenter.attachView(infoView);
        // When call infoPresenter init()
        infoPresenter.init();
        // Then it should return Error
        verify(infoView).onError("Please, give read permission");
    }

    @Test(expected = NullPointerException.class)
    public void getContactWithWrongIdWhenRequestReadContactTrue() {
        // readContactRequest should return true Permission for our request contacts
        when(readContactRequest.getReadContactPermission()).thenReturn(true);
        // contactsProvider should return NullPointerException if id = 222
        when(contactsProvider.getInfoContact("222")).thenThrow(NullPointerException.class);
        infoPresenter = new InfoPresenter(infoInteractor, "222", trampolineScheduler, router);
        infoPresenter.setRequestReadContact(readContactRequest);
        infoPresenter.attachView(infoView);
        // When call infoPresenter init()
        infoPresenter.init();
        // Then it should return NullPointerException
    }


    @Test
    public void clickMapButtonForNavigateToMapScreen() {
        ArgumentCaptor<Screens.MapScreen> argumentCaptor = ArgumentCaptor.forClass(Screens.MapScreen.class);
        infoPresenter = new InfoPresenter(infoInteractor, "228", trampolineScheduler, router);
        infoPresenter.attachView(infoView);
        // When click on the MapButton
        infoPresenter.clickMapButton();
        // Then router should navigate to MapScreen
        verify(router).navigateTo(argumentCaptor.capture());
    }
}