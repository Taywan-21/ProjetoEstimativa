package br.projeto.command;

import br.projeto.presenter.DetalheProjetoPresenter;
import br.projeto.presenter.helpers.WindowManager;
import br.projeto.repository.ProjetoRepositoryImpl;
import br.projeto.view.DetalheProjetoView;

import javax.swing.*;

public class AbrirDetalhesProjetoProjetoCommand implements ProjetoCommand {
    private final ProjetoRepositoryImpl repository;
    private final JDesktopPane desktop;
    private String projetoNome;

    public AbrirDetalhesProjetoProjetoCommand(ProjetoRepositoryImpl repository, JDesktopPane desktop) {
        this.repository = repository;
        this.desktop = desktop;
    }

    public void setProjetoNome(String projetoNome) {
        this.projetoNome = projetoNome;
    }

    @Override
    public void execute() {
        if (projetoNome == null || projetoNome.isEmpty()) {
            throw new IllegalStateException("O nome do projeto não foi definido para este comando.");
        }

        String tituloJanela = "Detalhes do Projeto: " + projetoNome;
        WindowManager windowManager = WindowManager.getInstance();

        if (windowManager.isFrameAberto(tituloJanela)) {
            windowManager.bringToFront(tituloJanela);
        } else {
            DetalheProjetoView detalheView = new DetalheProjetoView();
            detalheView.setTitle(tituloJanela);
            new DetalheProjetoPresenter(detalheView, repository, projetoNome);
            desktop.add(detalheView);
            detalheView.setVisible(true);
            try {
                detalheView.setMaximum(true);
            } catch (Exception ignored) {
            }
        }
    }
}
