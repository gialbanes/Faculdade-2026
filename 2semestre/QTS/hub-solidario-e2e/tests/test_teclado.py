import pytest
from playwright.sync_api import Page, expect

@pytest.mark.keyboard
def test_fluxo_cadastro_via_teclado(page: Page):
    """
    Garante que o usuário consegue navegar por todos os campos e submeter
    o formulário utilizando apenas as teclas Tab, Enter e Espaço.
    """
    page.goto("http://localhost:3000/formulario.html")

    # 1. Navega com Tab para o primeiro campo (Nome)
    page.keyboard.press("Tab")
    campo_nome = page.get_by_label("Nome Completo")
    expect(campo_nome).to_be_focused()
    page.keyboard.type("Maria Silva")

    # 2. Navega para o segundo campo (E-mail)
    page.keyboard.press("Tab")
    campo_email = page.get_by_label("E-mail Institucional")
    expect(campo_email).to_be_focused()
    page.keyboard.type("maria@fatec.sp.gov.br")

    # 3. Navega para a seleção de perfil (Select ou Radio)
    page.keyboard.press("Tab")
    select_perfil = page.get_by_label("Perfil de Voluntário")
    expect(select_perfil).to_be_focused()
    page.keyboard.press("ArrowDown")

    # 4. Navega para o botão de envio e aciona com Enter
    page.keyboard.press("Tab")
    botao_enviar = page.get_by_role("button", name="Cadastrar Voluntário")
    expect(botao_enviar).to_be_focused()
    page.keyboard.press("Enter")

    # 5. Valida a mensagem de sucesso
    mensagem_sucesso = page.get_by_text("Cadastro realizado com sucesso")
    expect(mensagem_sucesso).to_be_visible()