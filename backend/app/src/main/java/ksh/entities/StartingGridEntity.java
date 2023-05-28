package ksh.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StartingGridEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(columnDefinition = "json")
    private String killerSudoku;

    protected StartingGridEntity() {
    }

    public StartingGridEntity(final String killerSudoku) {
        this.killerSudoku = killerSudoku;
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getKillerSudoku() {
        return this.killerSudoku;
    }

    public void setKillerSudoku(final String killerSudoku) {
        this.killerSudoku = killerSudoku;
    }

}
