package ar.com.tdm.mock.model.entities.serviceB;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Comic")
@AllArgsConstructor
@NoArgsConstructor
public class Comic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comic_id")
    private Long id;

    @Column(name = "comic_title")
    private String title;

}