package com.app.whatsapp.ending.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Welcome {
    private Generation generation;
    private long id;
    private List<Generation> move_learn_methods;
    private String name;
    private long order;
    private List<Generation> pokedexes;
    private List<Generation> regions;
    
    private String testError;
}
