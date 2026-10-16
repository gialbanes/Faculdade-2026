import pytest
from pathlib import Path
from playwright.sync_api import Page, expect
from axe_playwright_python.sync_playwright import Axe

# Obtém o caminho absoluto do arquivo HTML local
HTML_PATH = f"file://{Path(__file__).parent.parent.resolve()}/formulario.html"

def test_auditoria_axe_core(page: Page):
    """Auditoria automatizada com regras WCAG 2.1 AA."""
    page.goto(HTML_PATH)

    axe = Axe()
    results = axe.run(page, options={"runOnly": ["wcag2a", "wcag2aa", "wcag21a", "wcag21aa"]})

    assert results.violations_count == 0, f"Violacoes encontradas:\n{results.generate_report()}"

def test_navegacao_por_teclado(page: Page):
    """Garante operabilidade completa utilizando apenas o teclado."""
    page.goto(HTML_PATH)

    # 1. Tab para o campo Nome
    page.keyboard.press("Tab")
    campo_nome = page.get_by_label("Nome Completo")
    expect(campo_nome).to_be_focused()
    page.keyboard.type("Carlos Eduardo")

    # 2. Tab para o campo E-mail
    page.keyboard.press("Tab")
    campo_email = page.get_by_label("E-mail")
    expect(campo_email).to_be_focused()
    page.keyboard.type("carlos@fatec.sp.gov.br")

    # 3. Tab para o botão e acionamento com Enter
    page.keyboard.press("Tab")
    botao_enviar = page.get_by_role("button", name="Cadastrar Voluntário")
    expect(botao_enviar).to_be_focused()
    page.keyboard.press("Enter")

    # 4. Validação do feedback
    expect(page.get_by_text("Cadastro realizado com sucesso!")).to_be_visible()