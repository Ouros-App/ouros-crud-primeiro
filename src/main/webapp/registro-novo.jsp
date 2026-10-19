<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Novo Registro de água</title>
<link rel="stylesheet" href="Style.css?v=5">
<link rel="icon" href="img/Asa-icon.png">
</head>
<body class="EditorPagina">
<main class="EditorCartao" aria-labelledby="editorTitulo">
<header class="EditorCabecalho">
<h1 id="editorTitulo">Novo Registro de água</h1>
<a class="EditorFechar" href="registros.jsp" aria-label="Fechar">×</a>
</header>
<form action="registros" method="post" data-editor="registro">
<input type="hidden" name="acao" value="criar">
<input type="hidden" name="id" value="">
<div class="EditorAbas" role="group" aria-label="Tipo de registro" aria-describedby="registroTipoAjuda">
<button type="button" data-group="tipo" data-value="Agua" aria-pressed="true" class="EditorAba ativa">
<svg class="EditorIcone agua" viewBox="0 0 16 20" aria-hidden="true">
<path fill="currentColor" d="M8 0C6 4 1 9 1 13a7 7 0 0 0 14 0C15 9 10 4 8 0Z"/>
</svg> Água</button>
<button type="button" data-group="tipo" data-value="Energia" aria-pressed="false" class="EditorAba">
<svg class="EditorIcone energia" viewBox="0 0 16 20" aria-hidden="true">
<path fill="currentColor" d="M9 0 1 12h6l-1 8 9-13H9Z"/>
</svg> Energia</button>
<input type="hidden" name="tipo" value="Agua">
</div>
<p class="EditorRegistroTipoAjuda" id="registroTipoAjuda">Água usa as leituras inicial e final do hidrômetro; energia usa o consumo em kWh.</p>
<div class="EditorGrid">
<div class="EditorCampo">
<label for="granja">Granja</label>
<input id="granja" name="granja" type="text" placeholder="Granja Exemplo" maxlength="120" autocomplete="organization" required>
</div>
<div class="EditorCampo">
<label for="lote">Lote</label>
<select id="lote" name="lote" required>
<option value="" disabled selected>Selecione um lote</option>
<option>#067</option>
<option>#14</option>
<option>#13</option>
<option>#12</option>
<option>#11</option>
<option>#10</option>
</select>
</div>
<div class="EditorCampo">
<label for="data">Data do registro</label>
<input id="data" name="data" type="date" required>
</div>
<div class="EditorCampo">
<label for="status">Status</label>
<select id="status" name="status" required>
<option>Normal</option>
<option>Abaixo</option>
<option>Acima</option>
</select>
</div>
<div class="EditorCampo" id="medidorInicialCampo">
<label for="medidorInicial">Hidrômetro inicial (água)</label>
<input id="medidorInicial" name="medidorInicial" type="number" placeholder="1234" min="0" step="1" inputmode="numeric" aria-describedby="medidorAjuda">
</div>
<div class="EditorCampo" id="medidorFinalCampo">
<label for="medidorFinal">Hidrômetro final (água)</label>
<input id="medidorFinal" name="medidorFinal" type="number" placeholder="4321" min="0" step="1" inputmode="numeric" aria-describedby="medidorAjuda">
</div>
<div class="EditorCampo EditorCampo--full" id="medidorAjudaCampo">
<small class="EditorCampoAjuda" id="medidorAjuda">Informe leituras inteiras; a leitura final deve ser igual ou maior que a inicial.</small>
</div>
<div class="EditorCampo" id="consumoEnergiaCampo" hidden>
<label for="consumo">Consumo (kWh)</label>
<input id="consumo" name="consumo" type="number" placeholder="1250" min="0" step="1" inputmode="numeric" aria-describedby="consumoAjuda" disabled>
<small class="EditorCampoAjuda" id="consumoAjuda">Informe o total consumido em quilowatts-hora.</small>
</div>
</div>
<footer class="EditorAcoes">
<a href="registros.jsp" class="EditorCancelar">Cancelar</a>
<button type="submit" class="EditorSalvar">Salvar</button>
</footer>
<p class="EditorAviso" role="status" hidden>
</p>
</form>
</main>
<script>
(() => {
 const form=document.querySelector('[data-editor]'); if(!form)return;
 const energia=form.elements.consumo, inicial=form.elements.medidorInicial, final=form.elements.medidorFinal;
 const kind=form.dataset.editor, editing=new URLSearchParams(location.search).get('acao')==='editar';
 form.elements.acao.value=editing?'editar':'criar';
 if(editing){const titles={granja:'Granja',lote:'Lote',funcionario:'Funcionário',registro:'Registro de água',meta:'Meta'};document.title='Editar '+titles[kind];document.getElementById('editorTitulo').textContent=document.title;}
 // Dados temporários apenas para pré-visualizar a edição. O backend deve carregar pelo ID.
 if(editing){try{const data=JSON.parse(sessionStorage.getItem('ouros-editor-'+kind)||'{}');for(const [key,value] of Object.entries(data)){const input=form.elements.namedItem(key);if(input && value!=null)input.value=value;}}catch(e){/* Edição direta abre sem dados. */}}
 function refresh(){for(const button of form.querySelectorAll('[data-group]')){const active=form.elements.namedItem(button.dataset.group).value===button.dataset.value;button.classList.toggle('ativa',active);button.setAttribute('aria-pressed',String(active));}
 const energy=form.elements.namedItem(kind==='meta'?'tipoValor':'tipo')?.value==='Energia';
 if(kind==='registro'){document.getElementById('editorTitulo').textContent=(editing?'Editar':'Novo')+' Registro de '+(energy?'energia':'água');document.querySelector('label[for=medidorInicial]').textContent='Hidrômetro inicial (água)';document.querySelector('label[for=medidorFinal]').textContent='Hidrômetro final (água)';document.getElementById('medidorInicialCampo').hidden=energy;document.getElementById('medidorFinalCampo').hidden=energy;document.getElementById('medidorAjudaCampo').hidden=energy;document.getElementById('consumoEnergiaCampo').hidden=!energy;inicial.disabled=energy;final.disabled=energy;inicial.required=!energy;final.required=!energy;energia.disabled=!energy;energia.required=energy;}
 if(kind==='meta')document.querySelector('label[for=alvo]').textContent='Valor alvo ('+(energy?'kWh':'litros')+')';
 }
 form.querySelectorAll('[data-group]').forEach(button=>button.addEventListener('click',()=>{form.elements.namedItem(button.dataset.group).value=button.dataset.value;refresh();}));refresh();
 form.addEventListener('input',()=>{form.querySelectorAll('input').forEach(input=>input.setCustomValidity(''));});
 form.addEventListener('submit',event=>{
 if(kind==='registro'&&form.elements.tipo.value==='Agua'){const first=form.elements.medidorInicial,last=form.elements.medidorFinal;if(first.value!==''&&last.value!==''&&Number(last.value)<Number(first.value)){event.preventDefault();last.setCustomValidity('O medidor final deve ser maior ou igual ao inicial.');last.reportValidity();return;}}
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
