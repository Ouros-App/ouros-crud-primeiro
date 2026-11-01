package primeirobd.repository;

import primeirobd.model.CGI;
import primeirobd.model.Funcionario;
import primeirobd.model.Granja;

import java.util.List;

public interface GranjaDAO {
    // Busca granjas por diferentes critérios.
    List<Granja> selectAll();
    List<CGI> selectCgi();
    List<Granja> selectNome();
    List<Granja> selectRegiao();
    List<Granja> selectCapacidade();
    Granja selectById(int id);
    // Remove e salva granjas.
    String delete(int id);
    String insert(Granja granja);
    // Atualiza os dados da granja.
    String updateId(int idNew, int idOld);
    String updateNome(String nomeNew, String nomeOld);
    String updateCapacidade(String capacidadeNew, String capacidadeOld);
    String updateRegiao(String regiaoNew, String regiaoOld);
    String updateArea(int areaNew, int areaOld);
    String updateIdEmpresa(int idEmpresaNew, int idEmpresaOld);
    String updateGranjaComEndereco(Granja granja);
    // Conta e busca granjas filtradas com paginação.
    int contar();
    int contarBuscaELocalizacao(String busca, String localizacao);
    List<Granja> selectPaginadoComBusca(String busca, int tamanho, int offset);
}
