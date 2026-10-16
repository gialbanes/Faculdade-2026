from playwright.sync_api import Page, Locator

class BasePage:
    """Classe base que encapsula a instancia da pagina e utilitarios comuns."""

    def __init__(self, page: Page):
        self.page = page

    def navegar_para(self, url: str) -> None:
        """Navega ate a URL especificada."""
        self.page.goto(url)

    def obter_titulo(self) -> str:
        """Retorna o titulo da pagina atual."""
        return self.page.title()