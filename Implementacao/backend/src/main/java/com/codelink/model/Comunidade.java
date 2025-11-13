package com.codelink.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "comunidades")
public class Comunidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToMany
    @JoinTable(name = "comunidade_membros",
        joinColumns = @JoinColumn(name = "comunidade_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private List<Usuario> membros = new ArrayList<>();

    @OneToMany(mappedBy = "comunidade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    // getters, setters, construtores
}