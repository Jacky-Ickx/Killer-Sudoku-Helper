package ksh.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ksh.entities.StartingGridEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StartingGrid {
    @JsonProperty("grid")
    private int[][] grid;
    @JsonProperty("cages")
    private Cage[] cages;

    @JsonCreator
    public StartingGrid(@JsonProperty("grid") final int[][] grid, @JsonProperty("cages") final Cage[] cages) {
        this.grid = grid;
        this.cages = cages;
    }

    public StartingGridEntity toEntity() throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();

        return new StartingGridEntity(objectMapper.writeValueAsString(this));
    }

    public static StartingGrid fromEntity(final StartingGridEntity entity) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();

        String serialized = entity.getKillerSudoku();
        // why the fuck, do i have to do this?
        // desirialization is done with the same framework, that did the serialization...
        // why do i have to do the cleanup manually? THIS IS BULLSHIT
        serialized = serialized.replace("\"{", "{").replace("}\"", "}").replace("\\", "");

        return objectMapper.readValue(serialized, StartingGrid.class);
    }

    @JsonProperty("grid")
    public int[][] getGrid() {
        return this.grid;
    }

    @JsonProperty("grid")
    public void setGrid(final int[][] grid) {
        this.grid = grid;
    }

    @JsonProperty("cages")
    public Cage[] getCages() {
        return this.cages;
    }

    @JsonProperty("cages")
    public void setCages(final Cage[] cages) {
        this.cages = cages;
    }
}