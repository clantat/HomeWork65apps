package com.example.homework.core;

import androidx.fragment.app.Fragment;

import com.example.homework.presentation.fragment.FragmentContactInfo;
import com.example.homework.presentation.fragment.FragmentContacts;
import com.example.homework.presentation.fragment.MapFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class    Screens {
    public static final class ContactsScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            return FragmentContacts.getNewInstance();
        }
    }

    public static final class ContactInfoScreen extends SupportAppScreen {
        private final String id;

        public ContactInfoScreen(String id) {
            this.id = id;
        }

        @Override
        public Fragment getFragment() {
            return FragmentContactInfo.newInstance(id);
        }
    }

    public static final class MapScreen extends SupportAppScreen {
        private final String id;
        private final String name;
        public MapScreen(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public Fragment getFragment() {
            return MapFragment.newInstance(id, name);
        }
    }
}
