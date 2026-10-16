import pytest
from playwright.sync_api import Page
from axe_playwright_python.sync_playwright import Axe

@pytest.mark.accessibility
def test_auditoria_acessibilidade_wcag_aa(page: Page):
    """
    Valida a conformidade da página com as diretrizes WCAG 2.1 Nível AA
    utilizando o motor axe-core.
    """
    # Arrange: Acessa a página da aplicação
    page.goto("http://localhost:3000/formulario.html")

    # Act: Executa o scan do axe-core com as regras WCAG 2.1 AA
    axe = Axe()
    results = axe.run(
        page,
        options={
            "runOnly": {
                "type": "tag",
                "values": ["wcag2a", "wcag2aa", "wcag21a", "wcag21aa"]
            }
        }
    )

    # Formata a mensagem de erro caso existam violações
    violations = results.violations_count
    report = results.generate_report()

    # Assert: Nenhuma violação deve ser encontrada
    assert violations == 0, f"Foram encontradas {violations} violações de acessibilidade:\n{report}"