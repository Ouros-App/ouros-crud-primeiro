<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Tabelas do BD</title>
    <style>
        body{
            font-family: Arial, sans-serif;
            margin: 40px;
        }
        table{
            width: 100%;
            border-collapse: collapse;
        }
        th, td{
            padding: 10px;
            border: 1px solid #cccccc;
            text-align: left;
        }
        th{
            background-color: #cccccc;
        }
    </style>
</head>
<body>
    <h1>Informações</h1>
        <h2>Granjas</h2>
        <c:if test="${empty granjas}">
            <p>Nenhuma granja cadastrada.</p>
        </c:if>

        <c:if test="${not empty granjas}">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Região</th>
                    <th>Area da propriedade</th>
                    <th>Capacidade de aves</th>
                    <th>ID_Empresa</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="granjas" items="${granjas}">
                    <tr>
                        <td><c:out value ="${granjas.id}"></c:out></td>
                        <td><c:out value="${granjas.nome}"></c:out></td>
                        <td><c:out value="${granjas.regiao}"></c:out></td>
                        <td><c:out value="${granjas.areaPropriedade}"></c:out></td>
                        <td><c:out value="${granjas.capacidadeDeAves}"></c:out></td>
                        <td><c:out value="${granjas.idEmpresa}"></c:out></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    <h2>Funcionário</h2>
    <c:if test="${empty funcionarios}">
        <p>Nenhum funcionário cadastrado.</p>
    </c:if>

    <c:if test="${not empty funcionarios}">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>CPF</th>
                <th>Setor</th>
                <th>Email</th>
                <th>ID_Empresa</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="funcionarios" items="${funcionarios}">
                <tr>
                    <td><c:out value ="${funcionarios.id}"></c:out></td>
                    <td><c:out value="${funcionarios.nome}"></c:out></td>
                    <td><c:out value="${funcionarios.cpf}"></c:out></td>
                    <td><c:out value="${funcionarios.setor}"></c:out></td>
                    <td><c:out value="${funcionarios.email}"></c:out></td>
                    <td><c:out value="${funcionarios.idEmpresa}"></c:out></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <h2>Lotes</h2>
    <c:if test="${empty lotes}">
        <p>Nenhum lote cadastrado.</p>
    </c:if>

    <c:if test="${not empty lotes}">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Data de chegada</th>
                <th>Data de entrega</th>
                <th>Data de nascimento</th>
                <th>Galinhas recebidas</th>
                <th>Galinhas entregadas</th>
                <th>Ganho</th>
                <th>ID_Granja</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="lotes" items="${lotes}">
                <tr>
                    <td><c:out value ="${lotes.id}"></c:out></td>
                    <td><c:out value="${lotes.dtChegada}"></c:out></td>
                    <td><c:out value="${lotes.dtEntrega}"></c:out></td>
                    <td><c:out value="${lotes.dtNascimento}"></c:out></td>
                    <td><c:out value="${lotes.galinhasRecebidas}"></c:out></td>
                    <td><c:out value="${lotes.galinhasEntregadas}"></c:out></td>
                    <td><c:out value="${lotes.ganho}"></c:out></td>
                    <td><c:out value="${lotes.idGranja}"></c:out></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <h2>Metas<h2>
    <c:if test="${empty metas}">1
        <p>Nenhuma meta cadastrada.</p>
    </c:if>

    <c:if test="${not empty metas}">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Título</th>
                <th>Descrição</th>
                <th>Estado</th>
                <th>Região</th>
                <th>Status</th>
                <th>Tipo da meta</th>
                <th>Valor alvo</th>
                <th>ID_Granja</th>
                <th>Individual</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="metas" items="${metas}">
                <tr>
                    <td><c:out value ="${metas.id}"></c:out></td>
                    <td><c:out value="${metas.titulo}"></c:out></td>
                    <td><c:out value="${metas.descricao}"></c:out></td>
                    <td><c:out value="${metas.estado}"></c:out></td>
                    <td><c:out value="${metas.regiao}"></c:out></td>
                    <td><c:out value ="${metas.status}"></c:out></td>
                    <td><c:out value="${metas.tipoMeta}"></c:out></td>
                    <td><c:out value="${metas.valorAlvo}"></c:out></td>
                    <td><c:out value="${metas.idGranja}"></c:out></td>
                    <td><c:out value="${metas.individual}"></c:out></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <h2>Registros de Água</h2>
    <c:if test="${empty registroAguas}">
    <p>Nenhum registro cadastrado.</p>
    </c:if>

    <c:if test="${not empty registroAguas}">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Data Registro</th>
            <th>Hidrômetro início</th>
            <th>Hidrômetro final</th>
            <th>ID_Lote</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="registroAguas" items="${registroAguas}">
            <tr>
                <td><c:out value ="${registroAguas.id}"></c:out></td>
                <td><c:out value="${registroAguas.dtRegistro}"></c:out></td>
                <td><c:out value="${registroAguas.hidrometroInicio}"></c:out></td>
                <td><c:out value="${registroAguas.hidrometroFinal}"></c:out></td>
                <td><c:out value="${registroAguas.idLote}"></c:out></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </c:if>
        <h2>Registros de Energia</h2>
        <c:if test="${empty registroEnegias}">
        <p>Nenhum registro cadastrado.</p>
        </c:if>

        <c:if test="${not empty registroEnergias}">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Consumo</th>
                <th>Data de registro</th>
                <th>ID_Lote</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="registroEnergias" items="${registroEnergias}">
                <tr>
                    <td><c:out value ="${registroEnergias.id}"></c:out></td>
                    <td><c:out value="${registroEnergias.consumo}"></c:out></td>
                    <td><c:out value="${registroEnergias.dtRegistro}"></c:out></td>
                    <td><c:out value="${registroEnergias.idLote}"></c:out></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:if>
</body>
</html>
