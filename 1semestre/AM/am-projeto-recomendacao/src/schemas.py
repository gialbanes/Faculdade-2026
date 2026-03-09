from pydantic import BaseModel
from typing import Optional

# 1. Características da música (Base do cálculo)
class MusicFeatures(BaseModel):
    energy: float
    loudness: float

# 2. O que o usuário envia (Pedido)
class TrackRequest(BaseModel):
    track_id: Optional[str] = "unknown"
    track_name: str
    artist_name: str
    features: MusicFeatures

# 3. O que a API responde (Resposta)
class TrackResponse(BaseModel):
    track: str
    artist: str
    recommendation: str
    debug_info: dict

# Reutiliza MusicFeatures (mesmas 2 features: energy + loudness)
class BatchTrackItem(BaseModel):
    """Uma música dentro de um lote (batch)."""
    track_name: str
    artist_name: str
    features: MusicFeatures   # Reutiliza o que já existe!

# Requisição com várias músicas de uma vez
class TrackBatchRequest(BaseModel):
    tracks: list[BatchTrackItem]

# Resposta com o resultado de todas as músicas
class TrackBatchResponse(BaseModel):
    results: list[TrackResponse]
    total: int
    summary: dict