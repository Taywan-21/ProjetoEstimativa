package br.projeto.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.projeto.dbConnection.connections.IDatabaseConnection;
import br.projeto.model.Usuario;

public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final IDatabaseConnection connection;
    private final List<Usuario> usuarios = new ArrayList<>();

    public UsuarioRepositoryImpl(IDatabaseConnection connection) {
        this.connection = connection;
    }

    @Override
    public Usuario inserir(String nome, String email, String senha) {
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            pstmt.setString(3, senha);

            pstmt.executeUpdate();

            // Recupera o ID gerado utilizando o método próprio do SQLite
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    return new Usuario.Builder()
                            .id(id)
                            .nome(nome)
                            .email(email)
                            .senha(senha)
                            .build();
                } else {
                    throw new SQLException("Falha ao obter o ID gerado.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível inserir o usuário", e);
        }
    }

    @Override
    public void atualizar(Usuario usuario, int id) {
        String sql = "UPDATE produto SET nome = ?, " + "senha = ? " + "email = ? " + "WHERE id = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getSenha());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível atualizar objeto, pois o mesmo não existe");
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Usuario> listar() {
        String sql = "SELECT nome, senha, email FROM usuarios";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nome = rs.getString("nome");
                String senha = rs.getString("senha");
                String email = rs.getString("email");
                Usuario usuario = new Usuario.Builder()
                        .nome(nome)
                        .senha(senha)
                        .email(email)
                        .build();
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Não foi possível listar os usuários", e);
        }
        return usuarios;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT id, nome, senha, email FROM usuarios WHERE email = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String senha = rs.getString("senha");
                return new Usuario.Builder()
                        .id(id)
                        .nome(nome)
                        .senha(senha)
                        .email(email)
                        .build();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Não foi possível buscar o usuário", e);
        }
        return null;
    }
}
