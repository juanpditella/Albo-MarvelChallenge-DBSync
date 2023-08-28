package com.DbSync.model.entities.serviceA;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "CollaboratorHeroRelation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorHeroRelation {
    @Id
    private CollaboratorHeroRelationId id;

    public CollaboratorHeroRelation(Long superHeroID, Long creatorID){
        this.id=new CollaboratorHeroRelationId(superHeroID, creatorID);
    }
}