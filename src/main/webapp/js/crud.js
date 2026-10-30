/*
 * Comportamento comum das telas de CRUD.
 *  - Listagem: abrir/fechar o formulário (modal com iframe) e avisos (toast) de sucesso/erro.
 *  - Formulário dentro do modal: botões Fechar/Cancelar, tecla Esc e ajuste de altura.
 */
(function () {
    'use strict';

    var MENSAGENS = {
        ok: {
            inserido: 'Cadastrado com sucesso!',
            editado: 'Alterações salvas com sucesso!',
            excluido: 'Excluído com sucesso!'
        },
        erro: {
            dados: 'Alguns dados estavam inválidos. Confira o formulário e tente de novo.',
            medidor: 'O medidor final não pode ser menor que o inicial.',
            data: 'A data do registro precisa estar dentro do período do lote.',
            empresa: 'Empresa não encontrada. Digite o nome exatamente como está cadastrado.',
            granja: 'Granja não encontrada.',
            lote: 'Lote não encontrado.',
            naoencontrado: 'Esse item não existe mais.',
            id: 'Identificador inválido.'
        }
    };

    /* ---------- aviso (toast) ---------- */
    function toast(texto, tipo) {
        var el = document.createElement('div');
        el.className = 'Toast Toast--' + tipo;
        el.setAttribute('role', tipo === 'erro' ? 'alert' : 'status');
        el.textContent = texto;
        document.body.appendChild(el);
        requestAnimationFrame(function () { el.classList.add('Toast--visivel'); });
        setTimeout(function () {
            el.classList.remove('Toast--visivel');
            setTimeout(function () { el.remove(); }, 350);
        }, 4200);
    }

    function avisosDaUrl() {
        var params = new URLSearchParams(location.search);
        var ok = params.get('ok'), erro = params.get('erro');
        if (!ok && !erro) return;

        if (ok && MENSAGENS.ok[ok]) toast(MENSAGENS.ok[ok], 'ok');
        if (erro) toast(MENSAGENS.erro[erro] || 'Não foi possível concluir a operação.', 'erro');

        // tira ok/erro do endereço para o aviso não voltar ao atualizar a página
        params.delete('ok');
        params.delete('erro');
        var resto = params.toString();
        history.replaceState(null, '', location.pathname + (resto ? '?' + resto : '') + location.hash);
    }

    /* ---------- listagem: modal do formulário ---------- */
    function iniciarModal() {
        var dialog = document.getElementById('formularioModal');
        if (!dialog) return;
        var frame = dialog.querySelector('iframe');
        if (!frame) return;
        var overflowAnterior = '';

        function abrir(url) {
            frame.style.height = '';
            frame.src = url;
            overflowAnterior = document.body.style.overflow;
            document.body.style.overflow = 'hidden';
            if (!dialog.open) dialog.showModal();
        }

        // só define se a página não tiver o seu próprio (granjas, lotes, metas, registros)
        if (!window.abrirForms) window.abrirForms = abrir;
        if (!window.abrirFormulario) window.abrirFormulario = abrir;

        dialog.addEventListener('close', function () {
            document.body.style.overflow = overflowAnterior;
            frame.removeAttribute('src');
        });

        // clicar fora do cartão (no fundo escuro) fecha
        dialog.addEventListener('click', function (event) {
            if (event.target === dialog) dialog.close();
        });

        // o formulário (dentro do iframe) pede para fechar ou informa a altura dele
        window.addEventListener('message', function (event) {
            if (event.origin !== location.origin || event.source !== frame.contentWindow) return;
            if (event.data === 'ouros:fechar-formulario') dialog.close();
            if (event.data && event.data.type === 'ouros:altura-formulario' && Number.isFinite(event.data.height)) {
                frame.style.height = Math.min(Math.max(event.data.height, 300), 1200) + 'px';
            }
        });
    }

    /* ---------- formulário aberto dentro do modal ---------- */
    function iniciarFormulario() {
        var cartao = document.querySelector('.EditorCartao');
        if (!cartao || window.parent === window) return;

        document.body.classList.add('EditorEmModal');

        function fechar(event) {
            event.preventDefault();
            window.parent.postMessage('ouros:fechar-formulario', location.origin);
        }

        document.querySelectorAll('.EditorFechar, .EditorCancelar').forEach(function (el) {
            el.addEventListener('click', fechar);
        });
        document.addEventListener('keydown', function (event) {
            if (event.key === 'Escape') fechar(event);
        });

        new ResizeObserver(function () {
            window.parent.postMessage({
                type: 'ouros:altura-formulario',
                height: Math.ceil(cartao.getBoundingClientRect().height)
            }, location.origin);
        }).observe(cartao);
    }

    document.addEventListener('DOMContentLoaded', function () {
        avisosDaUrl();
        iniciarModal();
        iniciarFormulario();
    });
})();
