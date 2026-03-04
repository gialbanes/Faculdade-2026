from fastapi import APIRouter, Depends
from functools import lru_cache
from src.schemas import TrackRequest, TrackResponse
from src.models.perceptron import Perceptron

router = APIRouter()

@lru_cache
def get_perceptron():
    return Perceptron()

@router.post("/recommend/predict", response_model=TrackResponse)
def predict_track(request: TrackRequest, model: Perceptron = Depends(get_perceptron)):
    result = model.predict(request.features.energy, request.features.loudness)
    mood = "Festa/Agitada" if result["prediction"] == 1 else "Relax/Calma"

    return {
        "track": request.track_name,
        "artist": request.artist_name,
        "recommendation": mood,
        "debug_info": result
    }