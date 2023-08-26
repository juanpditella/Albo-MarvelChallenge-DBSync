package ar.com.tdm.mock.model.entities.serviceB;

import ar.com.tdm.mock.model.entities.SuperHero;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Interaction")
@NoArgsConstructor
public class Interaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interaction_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hero_id")
    private SuperHero hero;

    @ManyToOne
    @JoinColumn(name = "comic_id")
    private Comic comic;

    @ManyToOne
    @JoinColumn(name = "interaction_hero_id")
    private SuperHero interactionHero;
}