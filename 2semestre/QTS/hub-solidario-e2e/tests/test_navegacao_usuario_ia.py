# tests/test_navegacao_usuario_ia.py (Logica de Teste e Assercoes Declarativas)
import pytest
from playwright.sync_api import Page, expect
from pages.campanhas_page import CampanhasPage
from pages.login_page import LoginPage


@pytest.mark.e2e
def test_jornada_completa_login_cadastro_e_filtro(page: Page, server_url: str):
    """Valida a jornada completa do usuario de login, criacao de campanha e busca por filtro."""
    # Arrange
    login_page = LoginPage(page)
    campanhas_page = CampanhasPage(page)

    login_page.acessar_pagina(server_url)

    # Act 1: Autenticacao
    login_page.realizar_login_completo("coordenador@hubsolidario.org", "Solidario@2026")
    expect(campanhas_page.titulo_painel).to_be_visible()

    # Act 2: Cadastro de nova campanha
    campanhas_page.cadastrar_campanha(
        titulo="Inverno com Calor 2026",
        meta="22000.00",
        categoria="Agasalhos e Cobertores",
    )
    expect(campanhas_page.notificacao_status).to_contain_text("Campanha cadastrada com sucesso")

    # Act 3: Filtragem
    campanhas_page.filtrar_campanhas("Inverno com Calor")

    # Assert: Linha encontrada e visivel
    linha = campanhas_page.obter_linha_campanha("Inverno com Calor 2026")
    expect(linha).to_be_visible()


@pytest.mark.e2e
def test_fluxo_cancelamento_ao_cadastrar_campanha(page: Page, server_url: str):
    """Valida que o cancelamento no modal de cadastro nao cria novo registro."""
    # Arrange
    campanhas_page = CampanhasPage(page)
    campanhas_page.acessar_painel(server_url)

    # Act
    campanhas_page.botao_nova_campanha.click()
    page.get_by_role("button", name="Cancelar").click()

    # Assert: O formulario deve ser ocultado
    expect(page.locator("#formulario-campanha")).not_to_be_visible()