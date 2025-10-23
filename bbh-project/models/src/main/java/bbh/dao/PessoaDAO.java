package bbh.dao;

import bbh.common.PersistenciaException;
import bbh.domain.Pessoa;
import bbh.domain.util.PessoaTipo;
import bbh.service.util.ConexaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; //procurar no bd
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    private static PessoaDAO pessoaDAO;

    static {
        PessoaDAO.pessoaDAO = null;
    }

    public static PessoaDAO getInstance() {

        if (pessoaDAO == null) {
            pessoaDAO = new PessoaDAO();
        }

        return pessoaDAO;
    }

    public void inserir(Pessoa pessoa) throws PersistenciaException {
        if (pesquisarEmail(pessoa.getEmail()) != null) {
            throw new PersistenciaException("'" + pessoa.getEmail() + "' usuario ja existente");
        }

        String senha = pessoa.getSenha(); //Provisorio/ place holder
        pessoa.setSenha(senha);

        String sql = "INSERT INTO pessoa (id, nome, email, senha, naturalidade, endereco, contato, habilitado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, pessoa.getId());
            ps.setString(2, pessoa.getNome());
            ps.setString(3, pessoa.getEmail());
            ps.setString(4, pessoa.getSenha());
            ps.setString(5, pessoa.getNaturalidade());
            ps.setString(6, pessoa.getEndereco());
            ps.setLong(7, pessoa.getContato());
            ps.setBoolean(8, pessoa.getHabilitado());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao inserir pessoa: " + e.getMessage(), e);
        }
    }

    public Pessoa pesquisarEmail(String email) throws PersistenciaException {
        Pessoa pessoa = null;

        String sql = "SELECT id, nome, email, senha, naturalidade, endereco, contato, habilitado "
                + "FROM pessoa WHERE email = ?";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { //cria temporariamente uma instancia de pessoa, para poder encontrar se ja tem o mesmo login no bd
                    pessoa = new Pessoa(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getString("naturalidade")
                    );
                    pessoa.setId(rs.getLong("id"));
                    pessoa.setEndereco(rs.getString("endereco"));
                    pessoa.setContato(rs.getLong("contato"));
                    pessoa.setHabilitado(rs.getBoolean("habilitado"));
                }
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao pesquisar email: " + e.getMessage(), e);
        }

        return pessoa;
    }

    public List<String> listarNomes() throws PersistenciaException {
        List<String> nomes = new ArrayList<>();

        String sql = "SELECT nome FROM pessoa";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                nomes.add(rs.getString("nome"));
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar nomes: " + e.getMessage(), e);
        }

        return nomes;
    }

    public void delete(Pessoa pessoa) throws PersistenciaException {
        if (pessoa.getPessoaTipo() == PessoaTipo.ADMINISTRADOR) {
            throw new PersistenciaException("Não pode deletar administradores");
        }

        String sql = "UPDATE pessoa SET habilitado = false WHERE email = ?";

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pessoa.getEmail());
            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas == 0) { //verifica se alguma linha foi alterada
                throw new PersistenciaException("Pessoa não encontrada para deletar");
            }

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao desabilitar pessoa: " + e.getMessage(), e);
        }
    }
}
