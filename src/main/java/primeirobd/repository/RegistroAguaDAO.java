package primeirobd.repository;

import primeirobd.model.Lote;
import primeirobd.model.RegistroAgua;

import java.sql.*;
import java.util.List;

public interface RegistroAguaDAO {
    // Busca registros de água por diferentes critérios.
    List<RegistroAgua> selectAll();
    List<RegistroAgua> selectLote();
    List<RegistroAgua> selectData();
    List<RegistroAgua> selectHidrometroInicio();
    List<RegistroAgua> selectHidrometroFim();
    RegistroAgua selectById(int id);
    // Remove e salva registros de água.
    String delete(int id);
    String insert(RegistroAgua registroAgua);
    // Atualiza os dados do registro de água.
    String updateId(int idNew, int idOld);
    String updateIdLote(String loteNew, String loteOld);
    String updateData(Date dataNew, Date dataOld);
    String updateHidrometroInicio(double hidrometroNew, double hidrometroOld);
    String updateHidrometroFim(double hidrometroNew, double hidrometroOld);

    // Conta e busca registros filtrados com ordenação e paginação.
    int contarBusca(String busca);
    List<RegistroAgua> selectPaginadoComBusca(String busca, int tamanho, int offset, String ordenarPor);

}
