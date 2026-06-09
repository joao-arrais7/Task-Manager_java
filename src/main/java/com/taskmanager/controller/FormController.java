package com.taskmanager.controller;

import com.taskmanager.dao.TarefaDAO;
import com.taskmanager.model.Tarefa;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FormController {

    @FXML private Label    labelTituloPagina;
    @FXML private TextField campoTitulo;
    @FXML private TextArea  campoDescricao;
    @FXML private CheckBox  checkConcluida;
    @FXML private Label     erroTitulo;

    private Stage    stage;
    private TarefaDAO tarefaDAO;
    private Tarefa   tarefaEditando;
    private Runnable onSalvar;

    // Setters injetados pelo MainController
    public void setStage(Stage stage)          { this.stage = stage; }
    public void setTarefaDAO(TarefaDAO dao)    { this.tarefaDAO = dao; }
    public void setOnSalvar(Runnable onSalvar) { this.onSalvar = onSalvar; }

    //  Preenche o form quando for edição
    public void preencherForm(Tarefa tarefa) {
        this.tarefaEditando = tarefa;
        labelTituloPagina.setText("Editar Tarefa");
        campoTitulo.setText(tarefa.getTitulo());
        campoDescricao.setText(tarefa.getDescricao());
        checkConcluida.setSelected(tarefa.isConcluida());
    }

    // Botão Salvar
    @FXML
    private void salvar() {
        if (!validar()) return;

        String titulo    = campoTitulo.getText().trim();
        String descricao = campoDescricao.getText().trim();
        boolean concluida = checkConcluida.isSelected();

        if (tarefaEditando == null) {
            // Nova tarefa
            Tarefa nova = new Tarefa(titulo, descricao, concluida);
            tarefaDAO.inserir(nova);
        } else {
            // Editar tarefa existente
            tarefaEditando.setTitulo(titulo);
            tarefaEditando.setDescricao(descricao);
            tarefaEditando.setConcluida(concluida);
            tarefaDAO.atualizar(tarefaEditando);
        }

        if (onSalvar != null) onSalvar.run();
        stage.close();
    }

    //  Botão Cancelar
    @FXML
    private void cancelar() {
        stage.close();
    }

    //  Validação do formulário
    private boolean validar() {
        if (campoTitulo.getText().trim().isEmpty()) {
            erroTitulo.setText("⚠️ O título é obrigatório!");
            erroTitulo.setVisible(true);
            campoTitulo.requestFocus();
            return false;
        }
        if (campoTitulo.getText().trim().length() > 100) {
            erroTitulo.setText("⚠️ Título deve ter no máximo 100 caracteres!");
            erroTitulo.setVisible(true);
            return false;
        }
        erroTitulo.setVisible(false);
        return true;
    }
}