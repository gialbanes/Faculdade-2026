import httpx


class GatewayPagamentoError(Exception):
    """Excecao base para falhas de comunicacao com o Gateway de Pagamento."""
    pass


class GatewayTimeoutError(GatewayPagamentoError):
    """Excecao quando o gateway excede o tempo limite de resposta."""
    pass


class GatewayIndisponivelError(GatewayPagamentoError):
    """Excecao quando o gateway esta fora do ar ou inacessivel."""
    pass


class GatewayPagamentoClient:
    """Cliente HTTP para comunicacao com o servico externo de pagamentos."""

    def __init__(self, base_url: str = "https://api.gateway-financeiro.fake.com", timeout: float = 5.0):
        self.base_url = base_url
        self.timeout = timeout

    def processar_transacao(
        self,
        valor: float,
        metodo: str,
        cliente_cpf: str,
        dados_pagamento: dict[str, str] | None = None
    ) -> dict:
        """Envia requisicao de cobranca para a API externa de pagamentos."""
        payload = {
            "valor": valor,
            "metodo": metodo,
            "cpf": cliente_cpf,
            "detalhes": dados_pagamento or {},
        }

        try:
            with httpx.Client(base_url=self.base_url, timeout=self.timeout) as client:
                response = client.post("/v1/cobrancas", json=payload)

                if response.status_code == 200:
                    return response.json()
                elif response.status_code == 400:
                    corpo = response.json()
                    return {
                        "aprovado": False,
                        "status": "RECUSADO",
                        "motivo": corpo.get("motivo", "Transacao recusada pela operadora."),
                        "transacao_id": None
                    }
                else:
                    raise GatewayPagamentoError(
                        f"Gateway retornou erro HTTP {response.status_code}: {response.text}"
                    )
        except httpx.TimeoutException as exc:
            raise GatewayTimeoutError("Tempo limite excedido na comunicacao com o gateway.") from exc
        except (httpx.ConnectError, httpx.NetworkError) as exc:
            raise GatewayIndisponivelError("Falha de conexao de rede com o gateway de pagamentos.") from exc