(() => {
    const draftKey = "ourosCadastroDraft";
    const digits = (value) => value.replace(/\D/g, "");

    document.querySelectorAll("[data-login-voltar]").forEach((link) => {
        link.addEventListener("click", (event) => {
            if (event.button !== 0 || event.metaKey || event.ctrlKey || event.shiftKey || event.altKey) return;
            if (window.history.length > 1) {
                event.preventDefault();
                window.history.back();
            }
        });
    });

    const formatCnpj = (value) => {
        const clean = digits(value).slice(0, 14);
        let formatted = clean.slice(0, 2);
        if (clean.length > 2) formatted += "." + clean.slice(2, 5);
        if (clean.length > 5) formatted += "." + clean.slice(5, 8);
        if (clean.length > 8) formatted += "/" + clean.slice(8, 12);
        if (clean.length > 12) formatted += "-" + clean.slice(12, 14);
        return formatted;
    };

    const formatPhone = (value) => {
        const clean = digits(value).slice(0, 11);
        return clean.length <= 10
            ? clean.replace(/^(\d{2})(\d)/, "($1) $2").replace(/(\d{4})(\d)/, "$1-$2")
            : clean.replace(/^(\d{2})(\d)/, "($1) $2").replace(/(\d{5})(\d)/, "$1-$2");
    };

    const showMessage = (form, text, type = "info") => {
        const output = form.querySelector(".LoginAviso");
        if (!output) return;
        output.textContent = text;
        output.dataset.tipo = type;
    };

    document.querySelectorAll("[data-auth-form]").forEach((form) => {
        const cnpj = form.querySelector("#cnpj");
        const telefone = form.querySelector("#telefone");
        const cep = form.querySelector("#cep");
        const estado = form.querySelector("#estado");

        cnpj?.addEventListener("input", () => {
            cnpj.value = formatCnpj(cnpj.value);
        });
        telefone?.addEventListener("input", () => {
            telefone.value = formatPhone(telefone.value);
        });
        cep?.addEventListener("input", () => {
            cep.value = digits(cep.value).slice(0, 8).replace(/^(\d{5})(\d)/, "$1-$2");
        });
        estado?.addEventListener("input", () => {
            estado.value = estado.value.replace(/[^a-z]/gi, "").slice(0, 2).toUpperCase();
        });

        form.addEventListener("submit", (event) => {
            event.preventDefault();
            if (!form.reportValidity()) return;

            switch (form.dataset.authForm) {
                case "cadastro-etapa1": {
                    if (digits(cnpj.value).length !== 14) {
                        showMessage(form, "Informe um CNPJ com 14 números.", "erro");
                        cnpj.focus();
                        return;
                    }
                    if (digits(telefone.value).length < 10) {
                        showMessage(form, "Informe um telefone válido com DDD.", "erro");
                        telefone.focus();
                        return;
                    }
                    const senha = form.querySelector("#senha").value;
                    if (senha !== form.querySelector("#confirmarSenha").value) {
                        showMessage(form, "As senhas não coincidem.", "erro");
                        form.querySelector("#confirmarSenha").focus();
                        return;
                    }
                    sessionStorage.setItem(draftKey, JSON.stringify({
                        nome: form.querySelector("#nome").value.trim(),
                        email: form.querySelector("#email").value.trim(),
                        cnpj: cnpj.value,
                        telefone: telefone.value
                    }));
                    window.location.href = "cadastro2.html";
                    break;
                }
                case "cadastro-etapa2":
                    if (digits(cep.value).length !== 8) {
                        showMessage(form, "Informe um CEP com 8 números.", "erro");
                        cep.focus();
                        return;
                    }
                    showMessage(form, "Dados validados. O serviço de cadastro ainda não está conectado; nenhuma conta foi criada.");
                    break;
                case "login":
                    showMessage(form, "A autenticação ainda não está conectada ao servidor. Seus dados não foram enviados.");
                    break;
                case "recuperacao":
                    showMessage(form, "O serviço de recuperação ainda não está conectado. Nenhum e-mail foi enviado.");
                    break;
            }
        });
    });
})();
