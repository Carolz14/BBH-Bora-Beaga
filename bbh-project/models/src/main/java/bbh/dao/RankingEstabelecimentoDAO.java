package bbh.dao;

import bbh.common.PersistenciaException;
import bbh.service.util.ConexaoBD;
import bbh.domain.RankingEstabelecimento;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RankingEstabelecimentoDAO {

    public List<RankingEstabelecimento> listarComAsMelhoresMedias(int limiteDeBuscas, int numeroAvaliacoesMin,
            int filtroDias) throws PersistenciaException {
        return executarConsultaRanking("nota_media DESC, numero_avaliacoes DESC, u.nome ASC",
                limiteDeBuscas, numeroAvaliacoesMin, filtroDias);
    }

    public List<RankingEstabelecimento> listarComOsMaioresNumerosDeVisitacoes(int limiteDeBuscas,
            int numeroAvaliacoesMin, int filtroDias) throws PersistenciaException {
        return executarConsultaRanking("numero_visitacoes DESC, nota_media DESC, numero_avaliacoes ASC",
                limiteDeBuscas, numeroAvaliacoesMin, filtroDias);
    }

    private List<RankingEstabelecimento> executarConsultaRanking(String orderByClause, int limiteDeBuscas,
            int numeroAvaliacoesMin, int filtroDias) throws PersistenciaException {
        String sql = """
                SELECT u.id AS id_estabelecimento,
                       u.nome AS nome_estabelecimento,
                       AVG(a.nota_avaliacao) AS nota_media,
                       COUNT(a.id_avaliacao) AS numero_avaliacoes,
                       SUM(CASE WHEN a.data_avaliacao >= ? THEN 1 ELSE 0 END) AS numero_visitacoes
                FROM usuarios u
                JOIN avaliacao a ON a.id_estabelecimento = u.id
                WHERE u.usuario_tipo = 'ESTABELECIMENTO'
                GROUP BY u.id, u.nome
                HAVING COUNT(a.id_avaliacao) >= ?
                ORDER BY """ + orderByClause + """
                LIMIT ?
                """;

        List<RankingEstabelecimento> lista = new ArrayList<>();

        long millisWindow = TimeUnit.DAYS.toMillis(Math.max(0, filtroDias));
        Timestamp limiteTimestamp = new Timestamp(Instant.now().toEpochMilli() - millisWindow);

        try (Connection conn = ConexaoBD.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, limiteTimestamp);
            ps.setInt(2, numeroAvaliacoesMin);
            ps.setInt(3, limiteDeBuscas);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(criarObjetoRankingEstabelecimento(rs));
                }
            }
            return lista;

        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao consultar ranking de estabelecimentos: " + e.getMessage(), e);
        }
    }

    private RankingEstabelecimento criarObjetoRankingEstabelecimento(ResultSet rs) throws SQLException {
        return new RankingEstabelecimento(
                rs.getLong("id_estabelecimento"),
                rs.getDouble("nota_media"),
                rs.getInt("numero_avaliacoes"),
                rs.getString("nome_estabelecimento"),
                rs.getInt("numero_visitacoes"));
    }
}