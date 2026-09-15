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
    <h2>Administradores</h2>
        <c:if test="${empty administradores}">
            <p>Nenhum administrador cadastrado.</p>
        </c:if>

        <c:if test="${not empty administradores}">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Email</th>
                        <th>Senha</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="administrador" items="${administradores}">
                        <tr>
                            <td><c:out value="${administrador.id}"></c:out></td>
                            <td><c:out value="${administrador.email}"></c:out></td>
                            <td><c:out value="${administrador.senha}"></c:out></td>
                        </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <h2>Empresas</h2>
        <c:if test="${empty empresas}">
            <p>Nenhuma empresa cadastrada.</p>
        </c:if>

        <c:if test="${not empty empresas}">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Telefone Corporativo</th>
                    <th>Email Corporativo</th>
                    <th>CNPJ</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="empresa" items="${empresas}">
                    <tr>
                        <td><c:out value ="${empresa.id}"></c:out></td>
                        <td><c:out value="${empresa.nome}"></c:out></td>
                        <td><c:out value="${empresa.telefoneCorporativo}"></c:out></td>
                        <td><c:out value="${empresa.emailCorporativo}"></c:out></td>
                        <td><c:out value="${empresa.cnpj}"></c:out></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    <h2>EnderecoEmpresa</h2>
    <c:if test="${empty enderecoEmpresas}">
        <p>Nenhum enderecoEmpresa cadastrada.</p>
    </c:if>

    <c:if test="${not empty enderecoEmpresas}">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Cep</th>
                <th>Numero</th>
                <th>Municipio</th>
                <th>Estado</th>
                <th>logradouro</th>
                <th>Id_Empresa</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="enderecoEmpresa" items="${enderecoEmpresas}">
                <tr>
                    <td><c:out value ="${enderecoEmpresa.id}"></c:out></td>
                    <td><c:out value="${enderecoEmpresa.cep}"></c:out></td>
                    <td><c:out value="${enderecoEmpresa.numero}"></c:out></td>
                    <td><c:out value="${enderecoEmpresa.municipio}"></c:out></td>
                    <td><c:out value="${enderecoEmpresa.estado}"></c:out></td>
                    <td><c:out value="${enderecoEmpresa.logradouro}"></c:out></td>
                    <td><c:out value="${enderecoEmpresa.idEmpresa}"></c:out></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <h2>EnderecoGranja</h2>
    <c:if test="${empty enderecoGranjas}">
        <p>Nenhum enderecoGranja cadastrada.</p>
    </c:if>

    <c:if test="${not empty enderecoGranjas}">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Cep</th>
                <th>Numero</th>
                <th>Municipio</th>
                <th>Estado</th>
                <th>logradouro</th>
                <th>Id_Granja</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="enderecoGranjas" items="${enderecoGranjas}">
                <tr>
                    <td><c:out value ="${enderecoGranjas.id}"></c:out></td>
                    <td><c:out value="${enderecoGranjas.cep}"></c:out></td>
                    <td><c:out value="${enderecoGranjas.numero}"></c:out></td>
                    <td><c:out value="${enderecoGranjas.municipio}"></c:out></td>
                    <td><c:out value="${enderecoGranjas.estado}"></c:out></td>
                    <td><c:out value="${enderecoGranjas.logradouro}"></c:out></td>
                    <td><c:out value="${enderecoGranjas.idGranja}"></c:out></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>