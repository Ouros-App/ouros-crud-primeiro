<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Nova Meta</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Style.css">
<link rel="icon" href="${pageContext.request.contextPath}/img/Asa-icon.png">
</head>
<body class="EditorPagina">
<main class="EditorCartao" aria-labelledby="editorTitulo">
<header class="EditorCabecalho">
<h1 id="editorTitulo">Nova Meta</h1>
<a class="EditorFechar" href="metas.jsp" aria-label="Fechar">×</a>
</header>
<form action="metas" method="post" data-editor="meta">
<input type="hidden" name="acao" value="criar">
<input type="hidden" name="id" value="">
<div class="EditorAbas" role="group" aria-label="Tipo">
<button type="button" data-group="tipo" data-value="Individual" aria-pressed="true" class="EditorAba ativa">Individual</button>
<button type="button" data-group="tipo" data-value="Estadual" aria-pressed="false" class="EditorAba">Estadual</button>
<input type="hidden" name="tipo" value="Individual">
</div>
<div class="EditorGrid">
<div class="EditorCampo EditorCampo--full">
<label for="titulo">Título</label>
<input id="titulo" name="titulo" type="text" placeholder="Diminuindo água"  required>
</div>
<div class="EditorCampo EditorCampo--full">
<label for="descricao">Descrição</label>
<input id="descricao" name="descricao" type="text" placeholder="Granja Ouro Branco"  >
</div>
<div class="EditorCampo">
<span class="EditorLabel">Tipo de valor</span>
<div class="EditorAbas" role="group" aria-label="Tipo de valor">
<button type="button" data-group="tipoValor" data-value="Agua" aria-pressed="true" class="EditorAba ativa">
<svg class="EditorIcone agua" viewBox="0 0 16 20" aria-hidden="true">
<path fill="currentColor" d="M8 0C6 4 1 9 1 13a7 7 0 0 0 14 0C15 9 10 4 8 0Z"/>
</svg> Água</button>
<button type="button" data-group="tipoValor" data-value="Energia" aria-pressed="false" class="EditorAba">
<svg class="EditorIcone energia" viewBox="0 0 16 20" aria-hidden="true">
<path fill="currentColor" d="M9 0 1 12h6l-1 8 9-13H9Z"/>
</svg> Energia</button>
<input type="hidden" name="tipoValor" value="Agua">
</div>
</div>
<div class="EditorCampo">
<label for="alvo">Valor alvo (litros)</label>
<input id="alvo" name="alvo" type="number" placeholder="1100" min="0" step="any" >
</div>
<div class="EditorCampo">
<label for="status">Status</label>
<select id="status" name="status">
<option>Em processo</option>
<option>Feito</option>
<option>Falha</option>
</select>
</div>
<div class="EditorCampo">
<label for="granja">Granja</label>
<input id="granja" name="granja" type="text" placeholder="Granja Ouro Branco"  required>
</div>
</div>
<footer class="EditorAcoes">
<a href="metas.jsp" class="EditorCancelar">Cancelar</a>
<button type="submit" class="EditorSalvar">Salvar</button>
</footer>
<p class="EditorAviso" role="status" hidden>
</p>
</form>
</main>
<script>
(() => {
 const form=document.querySelector('[data-editor]'); if(!form)return;
 const kind=form.dataset.editor, editing=new URLSearchParams(location.search).get('acao')==='editar';
 form.elements.acao.value=editing?'editar':'criar';
 if(editing){const titles={granja:'Granja',lote:'Lote',funcionario:'Funcionário',registro:'Registro de água',meta:'Meta'};document.title='Editar '+titles[kind];document.getElementById('editorTitulo').textContent=document.title;}
 // Dados temporários apenas para pré-visualizar a edição. O backend deve carregar pelo ID.
 if(editing){try{const data=JSON.parse(sessionStorage.getItem('ouros-editor-'+kind)||'{}');for(const [key,value] of Object.entries(data)){const input=form.elements.namedItem(key);if(input && value!=null)input.value=value;}}catch(e){/* Edição direta abre sem dados. */}}
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
