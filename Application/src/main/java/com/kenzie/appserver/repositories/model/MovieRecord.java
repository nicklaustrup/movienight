package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "Movie")
public class MovieRecord {

    private String movieId;
    private String title;
    private String description;

    @DynamoDBHashKey(attributeName = "movieId")
    public String getMovieId() {
        return movieId;
    }

    @DynamoDBAttribute(attributeName = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MovieRecord movieRecord = (MovieRecord) o;
        return Objects.equals(movieId, movieRecord.getMovieId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId);
    }
}
