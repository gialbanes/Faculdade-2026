from fastapi import FastAPI, HTTPException
from app.schemas import SolicitacaoCredito, ResultadoAnaliseCredito
from app.service import avaliar_solicitacao_credito

app = FastAPI(
    title = "Motor de crédito API",
    description = "API para análise automatizada de crédito",
    version = "1.0.0"
)



