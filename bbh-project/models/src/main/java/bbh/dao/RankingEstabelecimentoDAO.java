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
            int filtroDias, double notaMinima) throws PersistenciaException {
        return executarConsultaRanking("nota_media DESC, numero_avaliacoes DESC, nome_estabelecimento ASC",
                limiteDeBuscas, numeroAvaliacoesMin, filtroDias, notaMinima);
    }

    public List<RankingEstabelecimento> listarComOsMaioresNumerosDeVisitacoes(int limiteDeBuscas,
            int numeroAvaliacoesMin, int filtroDias, double notaMinima) throws PersistenciaException {
        return executarConsultaRanking("numero_visitacoes DESC, nota_media DESC, numero_avaliacoes ASC",
                limiteDeBuscas, numeroAvaliacoesMin, filtroDias, notaMinima);
    }

    private List<RankingEstabelecimento> executarConsultaRanking(String orderByClause, int limiteDeBuscas,
            int numeroAvaliacoesMin, int filtroDias, double notaMinima) throws PersistenciaException {
        String sql = """
                SELECT * FROM (
                    SELECT 
                        u.id AS id_estabelecimento,
                        u.nome AS nome_estabelecimento,
                        u.imagem_url AS imagem_estabelecimento,
                        'ESTABELECIMENTO' AS tipo,
                        AVG(a.nota_avaliacao) AS nota_media,
                        COUNT(a.id_avaliacao) AS numero_avaliacoes,
                        SUM(CASE WHEN a.data_avaliacao >= ? THEN 1 ELSE 0 END) AS numero_visitacoes
                    FROM usuarios u
                    JOIN avaliacao a ON a.id_estabelecimento = u.id AND a.categoria = 'ESTABELECIMENTO'
                    WHERE u.usuario_tipo = 'ESTABELECIMENTO'
                    GROUP BY u.id, u.nome, u.imagem_url

                    UNION ALL
                     
                    SELECT 
                        p.id AS id_estabelecimento,
                        p.nome AS nome_estabelecimento,
                        p.imagem_url AS imagem_estabelecimento,
                        'PONTO_TURISTICO' AS tipo,
                        AVG(a.nota_avaliacao) AS nota_media,
                        COUNT(a.id_avaliacao) AS numero_avaliacoes,
                        SUM(CASE WHEN a.data_avaliacao >= ? THEN 1 ELSE 0 END) AS numero_visitacoes
                    FROM ponto_turistico p
                    JOIN avaliacao a ON a.id_estabelecimento = p.id AND a.categoria = 'PONTO_TURISTICO'
                    GROUP BY p.id, p.nome, p.imagem_url
                ) AS ranking_unificado
                
                WHERE numero_avaliacoes >= ?
                  AND nota_media >= ?
                ORDER BY %s
                LIMIT ?
                """.formatted(orderByClause);

        List<RankingEstabelecimento> lista = new ArrayList<>();

        long millisWindow = TimeUnit.DAYS.toMillis(Math.max(0, filtroDias));
        Timestamp limiteTimestamp = new Timestamp(Instant.now().toEpochMilli() - millisWindow);

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            int i = 1;
            ps.setTimestamp(i++, limiteTimestamp);// Para Estabelecimentos
            ps.setTimestamp(i++, limiteTimestamp);// Para Pontos Turísticos
            ps.setInt(i++, numeroAvaliacoesMin);
            ps.setDouble(i++, notaMinima);
            ps.setInt(i++, limiteDeBuscas);

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
                rs.getInt("numero_visitacoes"),
                rs.getString("imagem_estabelecimento"),
                rs.getString("tipo"));
                
    }
}