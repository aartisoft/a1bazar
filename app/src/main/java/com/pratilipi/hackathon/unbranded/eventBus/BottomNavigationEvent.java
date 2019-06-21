package com.pratilipi.hackathon.unbranded.eventBus;


public class BottomNavigationEvent {

    private final boolean show;

    public BottomNavigationEvent(boolean show) {
        this.show = show;
    }

    public boolean showMenu() {
        return show;
    }
}
