from pydantic import BaseModel
from typing import Optional

class MatchFeatures(BaseModel):
    white_rating: float  
    black_rating: float  

class MatchRequest(BaseModel):
    match_id: Optional[str] = "unknown"
    white_id: str
    black_id: str
    opening_name: str
    features: MatchFeatures
class MatchPrediction(BaseModel):
    white_id: str
    black_id: str
    predicted_winner: str  
    probability_score: float 
    debug_info: dict 