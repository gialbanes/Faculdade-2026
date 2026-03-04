from fastapi import APIRouter
from src.api.v1 import recommendation

api_router = APIRouter()
api_router.include_router(recommendation.router, tags=["recomendacao"])