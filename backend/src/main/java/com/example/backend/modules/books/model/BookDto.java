package com.example.backend.modules.books.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    @NotNull(groups = {Delete.class, Update.class,GetOne.class})
    @Positive(groups = {Delete.class,Update.class,GetOne.class})
    private Long id;
    @NotNull(groups = {Save.class,Update.class})
    @NotBlank(groups = {Save.class,Update.class})
    private String name;
    @NotNull(groups = {Save.class,Update.class})
    @NotBlank(groups = {Save.class,Update.class})
    private String autor;
    @NotNull(groups = {Save.class,Update.class})
    @Positive(groups = {Save.class,Update.class})
    private Long releaseDate;
    private String img;

    public interface Save{}
    public interface Delete{}
    public interface Update{}
    public interface GetOne{}
}
