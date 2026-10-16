import pytest
from starlette.testclient import TestClient
from tests.factories import ClienteFactory, CriarPedidoRequestFactory, ItemPedidoFactory
from app.schemas import MetodoPagamento, StatusPedido


@pytest.mark.synthetic_data
@pytest.mark.integration
def test_criar_pedido_com_dados_sinteticos_faker_factory(
    client: TestClient,
    resposta_gateway_sucesso: dict,
    mocker
):
    """Testa integracao de pedido utilizando carga de dados gerada via Factory e Faker."""
    # Arrange: Constroi requisicao sintetica completa
    requisicao = CriarPedidoRequestFactory.build()
    mocker.patch(
        "app.service.GatewayPagamentoClient.processar_transacao",
        return_value=resposta_gateway_sucesso
    )

    # Act
    response = client.post("/pedidos", json=requisicao.model_dump())

    # Assert
    assert response.status_code == 201
    dados = response.json()
    assert dados["status"] == StatusPedido.PAGO.value
    assert dados["transacao_id"] == "TX-99887766"

    # Confirma que o valor total bate com a soma exata dos itens sinteticos
    total_esperado = round(sum(i.quantidade * i.preco_unitario for i in requisicao.itens), 2)
    assert dados["valor_total"] == total_esperado

    # Consulta no endpoint para verificar integridade dos dados sinteticos persistidos
    pedido_id = dados["pedido_id"]
    get_res = client.get(f"/pedidos/{pedido_id}")
    assert get_res.status_code == 200
    pedido_banco = get_res.json()
    assert pedido_banco["cliente"]["nome"] == requisicao.cliente.nome
    assert pedido_banco["cliente"]["cpf"] == requisicao.cliente.cpf
    assert len(pedido_banco["itens"]) == len(requisicao.itens)


@pytest.mark.synthetic_data
@pytest.mark.integration
@pytest.mark.parametrize(
    "metodo",
    [MetodoPagamento.PIX, MetodoPagamento.CARTAO_CREDITO, MetodoPagamento.BOLETO],
    ids=["fluxo_pix", "fluxo_cartao", "fluxo_boleto"]
)
def test_variabilidade_metodos_pagamento_com_dados_sinteticos(
    client: TestClient,
    metodo: MetodoPagamento,
    resposta_gateway_sucesso: dict,
    mocker
):
    """Testa a robustez da integracao para todos os metodos de pagamento suportados com dados sinteticos."""
    # Arrange
    requisicao = CriarPedidoRequestFactory.build(metodo_pagamento=metodo)
    mocker.patch(
        "app.service.GatewayPagamentoClient.processar_transacao",
        return_value=resposta_gateway_sucesso
    )

    # Act
    response = client.post("/pedidos", json=requisicao.model_dump())

    # Assert
    assert response.status_code == 201
    dados = response.json()
    assert dados["status"] == "PAGO"

    pedido_id = dados["pedido_id"]
    get_res = client.get(f"/pedidos/{pedido_id}")
    assert get_res.json()["metodo_pagamento"] == metodo.value


@pytest.mark.synthetic_data
@pytest.mark.unit
def test_resiliencia_cliente_com_caracteres_acentuados_pt_br():
    """Valida a criacao de cliente com nomes brasileiros contendo acentos e cedilha."""
    nomes_teste = [
        "João Pedro de Alcântara",
        "Vitória Maria da Conceição",
        "Luís Inácio Müller",
        "Érica dos Santos Álvares"
    ]

    for nome in nomes_teste:
        cliente = ClienteFactory.build(nome=nome)
        assert cliente.nome == nome
        assert len(cliente.cpf) == 11
        assert "@" in cliente.email


@pytest.mark.synthetic_data
@pytest.mark.unit
def test_item_pedido_factory_customizacao_de_lote():
    """Valida a capacidade da factory de gerar lotes customizados com variabilidade estocastica."""
    itens = ItemPedidoFactory.build_batch(size=5)
    assert len(itens) == 5
    for item in itens:
        assert item.quantidade >= 1
        assert item.preco_unitario > 0.0
        assert item.produto_id.startswith("PROD-")