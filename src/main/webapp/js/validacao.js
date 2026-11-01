/*
 * Validação dos formulários do CRUD.
 * Usa as MESMAS regras do Validador.java (regex de e-mail, telefone, CEP e
 * dígitos verificadores de CPF), então o que passa aqui também passa no servidor.
 *
 * Como usar: no <input> coloque data-validar="cpf|telefone|email|cep".
 * Campo com o atributo "required" também é checado como obrigatório.
 */
(function () {
    'use strict';

    function digitos(v) { return (v || '').replace(/\D/g, ''); }

    /* ---------- regras (espelham o Validador.java) ---------- */
    var EMAIL = /^[\w.+-]+@[\w-]+(\.[\w-]+)*\.[a-zA-Z]{2,}$/;
    var TELEFONE = /^\(?\d{2}\)?\s?9?\d{4}-?\d{4}$/;
    var CEP = /^\d{5}-?\d{3}$/;

    function digitoVerificador(d, pesos) {
        var soma = 0;
        for (var i = 0; i < pesos.length; i++) soma += Number(d.charAt(i)) * pesos[i];
        var resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }

    function cpfValido(v) {
        var d = digitos(v);
        if (d.length !== 11 || /^(\d)\1{10}$/.test(d)) return false;
        return digitoVerificador(d, [10, 9, 8, 7, 6, 5, 4, 3, 2]) === Number(d.charAt(9))
            && digitoVerificador(d, [11, 10, 9, 8, 7, 6, 5, 4, 3, 2]) === Number(d.charAt(10));
    }

    /* ---------- máscaras ---------- */
    function mascaraCpf(v) {
        var d = digitos(v).slice(0, 11), o = d.slice(0, 3);
        if (d.length > 3) o += '.' + d.slice(3, 6);
        if (d.length > 6) o += '.' + d.slice(6, 9);
        if (d.length > 9) o += '-' + d.slice(9);
        return o;
    }

    function mascaraTelefone(v) {
        var d = digitos(v).slice(0, 11);
        if (!d) return '';
        if (d.length <= 2) return '(' + d;
        var resto = d.slice(2);
        var corte = d.length > 10 ? 5 : 4;
        return '(' + d.slice(0, 2) + ') ' + (resto.length > corte ? resto.slice(0, corte) + '-' + resto.slice(corte) : resto);
    }

    function mascaraCep(v) {
        var d = digitos(v).slice(0, 8);
        return d.length > 5 ? d.slice(0, 5) + '-' + d.slice(5) : d;
    }

    var REGRAS = {
        cpf: { mascara: mascaraCpf, valida: cpfValido, msg: 'CPF inválido. Confira os 11 números (formato 000.000.000-00).' },
        telefone: { mascara: mascaraTelefone, valida: function (v) { return TELEFONE.test(v.trim()); }, msg: 'Telefone inválido. Use DDD + número, por exemplo (11) 91234-5678.' },
        email: { valida: function (v) { return EMAIL.test(v.trim()); }, msg: 'E-mail inválido. Use o formato nome@empresa.com.' },
        cep: { mascara: mascaraCep, valida: function (v) { return CEP.test(v.trim()); }, msg: 'CEP inválido. Use 8 números, por exemplo 07400-000.' }
    };

    /* ---------- mensagem de erro no campo ---------- */
    function campoDe(input) { return input.closest('.EditorCampo') || input.parentElement; }

    function caixaErro(input) {
        var campo = campoDe(input);
        var caixa = campo.querySelector('.CampoErro');
        if (!caixa) {
            caixa = document.createElement('small');
            caixa.className = 'CampoErro';
            caixa.setAttribute('role', 'alert');
            campo.appendChild(caixa);
        }
        if (!caixa.id) caixa.id = (input.id || input.name) + '-erro';
        input.setAttribute('aria-describedby', caixa.id);
        return caixa;
    }

    function mostrarErro(input, mensagem) {
        caixaErro(input).textContent = mensagem;
        campoDe(input).classList.add('EditorCampo--erro');
        input.setAttribute('aria-invalid', 'true');
    }

    function limparErro(input) {
        var caixa = campoDe(input).querySelector('.CampoErro');
        if (caixa) caixa.textContent = '';
        campoDe(input).classList.remove('EditorCampo--erro');
        input.removeAttribute('aria-invalid');
    }

    /* devolve a mensagem de erro do campo, ou '' quando está tudo certo */
    function mensagemDe(input) {
        var valor = input.value || '';
        var obrigatorio = input.required || input.hasAttribute('data-obrigatorio');

        if (valor.trim() === '') return obrigatorio ? 'Preencha este campo.' : '';

        var regra = REGRAS[input.getAttribute('data-validar')];
        if (regra && !regra.valida(valor)) return regra.msg;

        // regras nativas do HTML (min, max, data, número...) com a mesma aparência
        if (input.validity && !input.validity.valid) return input.validationMessage || 'Valor inválido.';

        return '';
    }

    function validar(input) {
        var msg = mensagemDe(input);
        if (msg) mostrarErro(input, msg); else limparErro(input);
        return !msg;
    }

    /* ---------- liga tudo ---------- */
    function iniciar(form) {
        form.noValidate = true; // as mensagens bonitas substituem os balões do navegador

        var campos = Array.prototype.slice.call(form.querySelectorAll('input, select, textarea'))
            .filter(function (el) { return el.type !== 'hidden' && !el.disabled && !el.readOnly; });
        var aviso = form.querySelector('.EditorAviso');

        campos.forEach(function (input) {
            var regra = REGRAS[input.getAttribute('data-validar')];
            var tocado = false;

            input.addEventListener('input', function (e) {
                if (regra && regra.mascara) {
                    var formatado = regra.mascara(input.value);
                    // ao apagar, não deixa a máscara "recolocar" o parêntese/ponto que o usuário acabou de tirar
                    if (e.inputType && e.inputType.indexOf('delete') === 0) formatado = formatado.replace(/\D+$/, '');
                    if (formatado !== input.value) input.value = formatado;
                }
                if (tocado || input.getAttribute('aria-invalid') === 'true') validar(input);
                if (aviso) aviso.hidden = true;
            });

            input.addEventListener('blur', function () {
                tocado = true;
                validar(input);
            });

            // erro que veio do servidor: marca o campo e foca no primeiro
            var doServidor = campoDe(input).querySelector('.CampoErro');
            if (doServidor && doServidor.textContent.trim()) {
                campoDe(input).classList.add('EditorCampo--erro');
                input.setAttribute('aria-invalid', 'true');
                input.setAttribute('aria-describedby', doServidor.id || '');
            }
        });

        // máscara nos valores que já vêm preenchidos (edição)
        campos.forEach(function (input) {
            var regra = REGRAS[input.getAttribute('data-validar')];
            if (regra && regra.mascara && input.value) input.value = regra.mascara(input.value);
        });

        form.addEventListener('submit', function (event) {
            var primeiro = null, total = 0;
            campos.forEach(function (input) {
                if (!validar(input)) {
                    total++;
                    if (!primeiro) primeiro = input;
                }
            });

            if (total > 0) {
                event.preventDefault();
                event.stopImmediatePropagation();
                if (aviso) {
                    aviso.textContent = total === 1
                        ? 'Corrija o campo destacado antes de salvar.'
                        : 'Corrija os ' + total + ' campos destacados antes de salvar.';
                    aviso.hidden = false;
                }
                primeiro.focus();
                return;
            }

            var botao = form.querySelector('button[type="submit"]');
            if (botao) {
                botao.disabled = true;
                botao.textContent = 'Salvando...';
            }
        }, true);

        var comErroServidor = form.querySelector('.EditorCampo--erro input, .EditorCampo--erro select');
        if (comErroServidor) comErroServidor.focus();
    }

    document.addEventListener('DOMContentLoaded', function () {
        document.querySelectorAll('form').forEach(function (form) {
            if (form.querySelector('[data-validar], [required]')) iniciar(form);
        });
    });
})();
