package com.taskmanager.dao;

import java.util.List;

// Interface genérica com Generics - T é o tipo da entidade
public interface GenericDAO<T> {

    void inserir(T objeto);

    void atualizar(T objeto);

    void deletar(int id);

    T buscarPorId(int id);

    List<T> listarTodos();
}