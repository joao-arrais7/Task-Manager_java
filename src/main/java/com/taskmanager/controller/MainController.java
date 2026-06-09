package com.taskmanager.controller;

import com.taskmanager.dao.TarefaDAO;
import com.taskmanager.model.Tarefa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private TableView<Tarefa> tabelaTarefas;
    @FXML private TableColumn<Tarefa, Integer> colId;
    @FXML private TableColumn<Tarefa, String>  colTitulo;
    @FXML private TableColumn<Tarefa, String>  colDescricao;
    @FXML private TableColumn<Tarefa, Boolean> colConcluida;
    @FXML private TableColumn<Tarefa, Void>    colAcoes;
    @FXML private TextField campoBusca;
    @FXML private Label     labelStatus;

    private final TarefaDAO tarefaDAO = new TarefaDAO();
    private final ObservableList<Tarefa> listaTarefas = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColunas();
        carregarTarefas();
    }

    //  Configura as colunas da TableView
    private void configurarColunas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colConcluida.setCellValueFactory(new PropertyValueFactory<>("concluida"));

        // Coluna concluída com CheckBox
        colConcluida.setCellFactory(col -> new TableCell<>() {
            private final CheckBox check = new CheckBox();

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    check.setSelected(item);
                    check.setDisable(true);
                    setGraphic(check);
                }
            }
        });

        // Coluna de ações com botões Editar e Remover
        colAcoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnEditar  = new Button("✏️ Editar");
            private final Button btnRemover = new Button("🗑️ Remover");
            private final HBox   hbox       = new HBox(5, btnEditar, btnRemover);

            {
                btnEditar.getStyleClass().add("btn-editar");
                btnRemover.getStyleClass().add("btn-perigo");

                btnEditar.setOnAction(e -> {
                    Tarefa t = getTableView().getItems().get(getIndex());
                    abrirFormEdicao(t);
                });

                btnRemover.setOnAction(e -> {
                    Tarefa t = getTableView().getItems().get(getIndex());
                    confirmarRemocao(t);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hbox);
            }
        });

        tabelaTarefas.setItems(listaTarefas);
    }

    // Carrega todas as tarefas do banco
    private void carregarTarefas() {
        listaTarefas.setAll(tarefaDAO.listarTodos());
        atualizarStatus();
    }

    //  Busca por título
    @FXML
    private void buscarTarefa() {
        String termo = campoBusca.getText().trim();
        List<Tarefa> resultado = termo.isEmpty()
                ? tarefaDAO.listarTodos()
                : tarefaDAO.buscarPorTitulo(termo);
        listaTarefas.setAll(resultado);
        atualizarStatus();
    }

    // Abre formulário para NOVA tarefa
    @FXML
    private void abrirFormNova() {
        abrirFormulario(null);
    }

    //  Abre formulário para EDITAR tarefa
    private void abrirFormEdicao(Tarefa tarefa) {
        abrirFormulario(tarefa);
    }

    //  Método genérico que abre o formulário
    private void abrirFormulario(Tarefa tarefa) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/taskmanager/form.fxml")
            );
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(tarefa == null ? "Nova Tarefa" : "Editar Tarefa");
            stage.initModality(Modality.APPLICATION_MODAL);

            FormController formController = loader.getController();
            formController.setStage(stage);
            formController.setTarefaDAO(tarefaDAO);
            formController.setOnSalvar(this::carregarTarefas);

            if (tarefa != null) {
                formController.preencherForm(tarefa);
            }

            stage.showAndWait();

        } catch (IOException e) {
            mostrarErro("Erro ao abrir formulário: " + e.getMessage());
        }
    }

    //  Confirmação antes de remover
    private void confirmarRemocao(Tarefa tarefa) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Remoção");
        alert.setHeaderText("Remover tarefa?");
        alert.setContentText("\"" + tarefa.getTitulo() + "\" será removida permanentemente.");

        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            tarefaDAO.deletar(tarefa.getId());
            carregarTarefas();
        }
    }

    //  Atualiza o label de status no rodapé
    private void atualizarStatus() {
        long concluidas = listaTarefas.stream()
                .filter(Tarefa::isConcluida).count();
        labelStatus.setText("Total: " + listaTarefas.size() +
                " | Concluídas: " + concluidas +
                " | Pendentes: " + (listaTarefas.size() - concluidas));
    }

    //  Exibe alerta de erro
    private void mostrarErro(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}