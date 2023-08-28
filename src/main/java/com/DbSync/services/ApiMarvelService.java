package com.DbSync.services;

public interface ApiMarvelService {
    public void updateCollaborators(String superHero);
    public void updateInteractedCharacters(String superHeroName);
    public void newSynchronization();
}
