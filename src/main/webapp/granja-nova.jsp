<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Nova Granja</title>
<link rel="stylesheet" href="Style.css?v=4">
<link rel="icon" href="img/Asa-icon.png">
</head>
<body class="EditorPagina">
<main class="EditorCartao" aria-labelledby="editorTitulo">
<header class="EditorCabecalho">
<h1 id="editorTitulo">Nova Granja</h1>
<a class="EditorFechar" href="granjas.jsp" aria-label="Fechar">×</a>
</header>
<form action="granjas" method="post" data-editor="granja">
<input type="hidden" name="acao" value="criar">
<input type="hidden" name="id" value="">
<div class="EditorGrid">
<div class="EditorCampo EditorCampo--full">
<label for="nome">Nome da Granja</label>
<input id="nome" name="nome" type="text" placeholder="Granja Ouro Branco" maxlength="120" autocomplete="organization" required>
</div>
<div class="EditorCampo">
<label for="capacidade">Capacidade de aves</label>
<input id="capacidade" name="capacidade" type="number" placeholder="5000" min="0" step="1" inputmode="numeric" required>
<small class="EditorCampoAjuda">Informe a quantidade inteira de aves.</small>
</div>
<div class="EditorCampo">
<label for="area">Área da propriedade (HA)</label>
<input id="area" name="area" type="number" placeholder="2" min="0" step="1" inputmode="numeric" aria-describedby="areaAjuda">
<small class="EditorCampoAjuda" id="areaAjuda">Use hectares inteiros neste cadastro.</small>
</div>
<div class="EditorCampo">
<label for="regiao">Região</label>
<select id="regiao" name="regiao" required>
<option value="" disabled selected>Selecione uma região</option>
<option>Sudeste</option>
<option>Sul</option>
<option>Centro-Oeste</option>
<option>Nordeste</option>
<option>Norte</option>
</select>
</div>
<div class="EditorCampo">
<label for="empresa">Empresa</label>
<select id="empresa" name="empresa" required>
<option value="" disabled selected>Selecione uma empresa</option>
<option>Seara</option>
</select>
</div>
<div class="EditorCampo">
<label for="cep">CEP</label>
<input id="cep" name="cep" type="text" placeholder="00000-000" inputmode="numeric" autocomplete="postal-code" maxlength="9" pattern="[0-9]{5}-[0-9]{3}" title="Digite um CEP com oito números." aria-describedby="cepAjuda">
<small class="EditorCampoAjuda" id="cepAjuda">Opcional. Digite os oito números; o hífen é incluído automaticamente.</small>
</div>
<div class="EditorCampo">
<label for="municipio">Município</label>
<input id="municipio" name="municipio" type="text" placeholder="Arujá" maxlength="100">
</div>
<div class="EditorCampo">
<label for="cidade">Cidade</label>
<input id="cidade" name="cidade" type="text" placeholder="São Paulo" maxlength="100">
</div>
<div class="EditorCampo">
<label for="estado">Estado</label>
<input id="estado" name="estado" type="text" placeholder="SP" minlength="2" maxlength="2" pattern="[A-Za-z]{2}" title="Use a sigla de duas letras do estado." autocapitalize="characters" autocomplete="address-level1" aria-describedby="estadoAjuda">
<small class="EditorCampoAjuda" id="estadoAjuda">Use a sigla do estado, como SP.</small>
</div>
<div class="EditorCampo EditorCampo--full">
<label for="logradouro">Logradouro</label>
<input id="logradouro" name="logradouro" type="text" placeholder="Estrada Rural, km 12, nº 480" maxlength="150" autocomplete="street-address">
</div>
</div>
<footer class="EditorAcoes">
<a href="granjas.jsp" class="EditorCancelar">Cancelar</a>
<button type="submit" class="EditorSalvar">Salvar</button>
</footer>
<p class="EditorAviso" role="status" hidden>
</p>
</form>
</main>
<script>
(() => {
 const form=document.querySelector('[data-editor]'); if(!form)return;
 const cep=form.elements.cep, estado=form.elements.estado;
 const formatCep=()=>{const digits=cep.value.replace(/\D/g,'').slice(0,8);cep.value=digits.length>5?digits.slice(0,5)+'-'+digits.slice(5):digits;};
 const formatEstado=()=>{estado.value=estado.value.replace(/[^a-z]/gi,'').slice(0,2).toUpperCase();};
 cep.addEventListener('input',formatCep);estado.addEventListener('input',formatEstado);
 const kind=form.dataset.editor, editing=new URLSearchParams(location.search).get('acao')==='editar';
 form.elements.acao.value=editing?'editar':'criar';
 if(editing){const titles={granja:'Granja',lote:'Lote',funcionario:'Funcionário',registro:'Registro de água',meta:'Meta'};document.title='Editar '+titles[kind];document.getElementById('editorTitulo').textContent=document.title;}
 // Dados temporários apenas para pré-visualizar a edição. O backend deve carregar pelo ID.
 if(editing){try{const data=JSON.parse(sessionStorage.getItem('ouros-editor-'+kind)||'{}');for(const [key,value] of Object.entries(data)){const input=form.elements.namedItem(key);if(input && value!=null)input.value=value;}}catch(e){/* Edição direta abre sem dados. */}}
 formatCep();formatEstado();
 function refresh(){for(const button of form.querySelectorAll('[data-group]')){const active=form.elements.namedItem(button.dataset.group).value===button.dataset.value;button.classList.toggle('ativa',active);button.setAttribute('aria-pressed',String(active));}
 const energy=form.elements.namedItem(kind==='meta'?'tipoValor':'tipo')?.value==='Energia';
 if(kind==='registro'){document.getElementById('editorTitulo').textContent=(editing?'Editar':'Novo')+' Registro de '+(energy?'energia':'água');document.querySelector('label[for=medidorInicial]').textContent=energy?'Medidor inicial (kWh)':'Hidrômetro inicial (água)';document.querySelector('label[for=medidorFinal]').textContent=energy?'Medidor final (kWh)':'Hidrômetro final (água)';}
 if(kind==='meta')document.querySelector('label[for=alvo]').textContent='Valor alvo ('+(energy?'kWh':'litros')+')';
 }
 form.querySelectorAll('[data-group]').forEach(button=>button.addEventListener('click',()=>{form.elements.namedItem(button.dataset.group).value=button.dataset.value;refresh();}));refresh();
 form.addEventListener('input',()=>{form.querySelectorAll('input').forEach(input=>input.setCustomValidity(''));});
 form.addEventListener('submit',event=>{
 if(kind==='registro'){const first=form.elements.medidorInicial,last=form.elements.medidorFinal;if(first.value!==''&&last.value!==''&&Number(last.value)<Number(first.value)){event.preventDefault();last.setCustomValidity('O medidor final deve ser maior ou igual ao inicial.');last.reportValidity();return;}}
 if(editing&&!form.elements.id.value){event.preventDefault();const notice=form.querySelector('.EditorAviso');notice.hidden=false;notice.textContent='Esta edição usa dados de exemplo. O salvamento será habilitado com a integração do registro ao backend.';}
 });
 document.addEventListener('keydown',event=>{if(event.key==='Escape')location.href=document.querySelector('.EditorFechar').href;});
})();

</script>
<script>
if (window.parent !== window) {
    document.body.classList.add('EditorEmModal');
    function fecharFormulario(event) {
        event.preventDefault();
        window.parent.postMessage('ouros:fechar-formulario', location.origin);
    }
    document.querySelectorAll('.EditorFechar, .EditorCancelar').forEach(function (link) {
        link.addEventListener('click', fecharFormulario);
    });
    document.addEventListener('keydown', function (event) {
        if (event.key === 'Escape') { event.stopImmediatePropagation(); fecharFormulario(event); }
    }, true);
    new ResizeObserver(function () {
        window.parent.postMessage({type:'ouros:altura-formulario',height:Math.ceil(document.querySelector('.EditorCartao').getBoundingClientRect().height)}, location.origin);
    }).observe(document.querySelector('.EditorCartao'));
}
</script>
</body>
</html>
