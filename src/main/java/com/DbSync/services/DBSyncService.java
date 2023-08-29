package com.DbSync.services;

import com.DbSync.exceptions.HeroNotFoundException;

public interface DBSyncService {
    public void updateCollaborators(String superHero) throws HeroNotFoundException;
    public void updateInteractedCharacters(String superHeroName) throws HeroNotFoundException;
    public void newSynchronization();
}
