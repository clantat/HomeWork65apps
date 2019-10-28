package com.example.homework;

import androidx.fragment.app.Fragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {
    public static final class ContactsScreen extends SupportAppScreen {
        private final int number;

        public ContactsScreen(int number) {
            this.number = number;
        }

        @Override
        public Fragment getFragment() {
            return FragmentContacts.getNewInstance(number);
        }
    }

    public static final class ContactInfoScreen extends SupportAppScreen {
        private final int number;
        private final String id;

        public ContactInfoScreen(int number, String id) {
            this.number = number;
            this.id = id;
        }

        @Override
        public Fragment getFragment() {
            return FragmentContactInfo.newInstance(id, number);
        }
    }
}
