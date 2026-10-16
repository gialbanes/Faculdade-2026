from fastapi import FastAPI, HTTPException
from app.schemas import SolicitacaoCredito, ResultadoAnaliseCredito
from app.service import avaliar_solicitacao_credito

app = FastAPI(
    title="Motor de Credito API",
    description="API para analise automatizada de credito e auditoria de qualidade de software",
    version="1.0.0",
)

@app.get("/health")
def health_check():
    """Endpoint de verificacao de integridade da API."""
    return {"status": "healthy", "service": "motor-credito"}


@app.post("/credito/avaliar", response_model=ResultadoAnaliseCredito)
def endpoint_avaliar_credito(solicitacao: SolicitacaoCredito):
    """Processa a solicitacao de credito aplicando regras de negocio e limites."""
    try:
        resultado = avaliar_solicitacao_credito(solicitacao)
        return resultado
    except ValueError as exc:
        raise HTTPException(status_code=422, detail=str(exc))