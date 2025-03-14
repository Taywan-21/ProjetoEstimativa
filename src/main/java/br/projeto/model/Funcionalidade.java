/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class Funcionalidade {

    private int id;
    private Categoria categoria;
    private Perfil perfil;
    private String nome;
    private boolean foiSelecionada;
    private Map<Plataforma, Double> valoresPlataformas = new HashMap<>();

    public Funcionalidade(int id, Categoria categoria, Perfil perfil, String nome) {
        this.id = id;
        this.categoria = categoria;
        this.perfil = perfil;
        this.nome = nome;
    }

    public Funcionalidade(int id, Categoria categoria, String nome) {
        this.id = id;
        this.categoria = categoria;
        this.nome = nome;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public int getId() {
        return id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public String getNome() {
        return nome;
    }

    public boolean isFoiSelecionada() {
        return foiSelecionada;
    }

    public Double getValorPorPlataforma(Plataforma plataforma) {
        return valoresPlataformas.get(plataforma);
    }

    public void addValorPlataforma(Plataforma plataforma, Double valor) {
        valoresPlataformas.put(plataforma, valor);
    }

    public void setFoiSelecionada(boolean foiSelecionada) {
        this.foiSelecionada = foiSelecionada;
    }
}
