from playwright.sync_api import Locator, Page
from pages.base_page import BasePage

class LoginPage(BasePage):
    """Encapsula a interface e as acoes da tela de login do Hub Solidario."""

    def __init__(self, page: Page):
        super().__init__(page)
        self.campo_email: Locator = page.get_by_label("E-mail de Acesso")
        self.campo_senha: Locator = page.get_by_label("Senha")
        self.botao_entrar: Locator = page.get_by_role("button", name="Entrar no Sistema")
        self.alerta_erro: Locator = page.get_by_role("alert")

    def acessar_pagina(self, base_url: str) -> None:
        """Acessa diretamente a rota de autenticacao."""
        self.navegar_para(f"{base_url}/login")

    def preencher_credenciais(self, email: str, senha: str) -> None:
        """Preenche os campos de email e senha."""
        self.campo_email.fill(email)
        self.campo_senha.fill(senha)

    def submeter_login(self) -> None:
        """Clica no botao de login."""
        self.botao_entrar.click()

    def realizar_login_completo(self, email: str, senha: str) -> None:
        """Executa o preenchimento e envio das credenciais."""
        self.preencher_credenciais(email, senha)
        self.submeter_login()