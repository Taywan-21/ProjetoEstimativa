package br.projeto.state.login;

import com.pss.senha.validacao.ValidadorSenha;

import br.projeto.presenter.LoginPresenter;

public class LoginNaoAutenticadoState implements LoginState {

    @Override
    public void handle(LoginPresenter presenter) {
        ValidadorSenha validadorSenha = new ValidadorSenha();
        if (!validadorSenha.validar(presenter.getView().getTxtSenha().getText()).isEmpty()) {

            System.out.println("Senha fora do padrão");
            //presenter.getView().getLblMensagem().setText("Senha fora do padrão");
            //return;
        }

        boolean isAuthenticated = false;
        try {
            isAuthenticated = presenter.getService().autenticar(
                presenter.getView().getTxtEmail().getText(),
                presenter.getView().getTxtSenha().getText()
            );
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
        }

        if (isAuthenticated) {
            presenter.setState(new LoginAutenticadoState());
            presenter.getState().handle(presenter);
        } else {
            System.out.println("Authentication failed");
        }
    }

}
