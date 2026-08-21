"""
Testes unitários — Segurança e Eficiência
Baseados nos requisitos do docs/prd_frete.md (ISO 25010).

Segurança:
  - UF deve ter exatamente 2 caracteres alfabéticos (A-Z).
  - UF com números, caracteres especiais ou tamanho ≠ 2 → ValueError
    com mensagem "UF deve conter exatamente 2 caracteres alfabéticos."
  - Espaços no início/fim devem ser removidos (sanitização) antes da validação.

Eficiência:
  - calcular_frete deve empregar lru_cache com maxsize=128.
  - Chamadas consecutivas com os mesmos parâmetros não devem recomputar
    o resultado (hits no cache aumentam, misses ficam estáveis).
"""

import pytest
from unittest.mock import patch
from app.main import calcular_frete


# ---------------------------------------------------------------------------
# Helpers
# ---------------------------------------------------------------------------

def _cache_info():
    """Retorna o cache_info do lru_cache, se aplicável."""
    return calcular_frete.cache_info()


# ===========================================================================
# SEGURANÇA — Validação de formato da UF (RF-SEC-01 / RF-SEC-02)
# ===========================================================================

class TestSegurancaValidacaoUF:
    """UF deve conter exatamente 2 caracteres estritamente alfabéticos."""

    MSG = "UF deve conter exatamente 2 caracteres alfabéticos."

    # --- Tamanho inválido ---------------------------------------------------

    def test_uf_vazia_levanta_value_error(self):
        with pytest.raises(ValueError, match=self.MSG):
            calcular_frete(peso=5.0, uf="")

    def test_uf_um_caractere_levanta_value_error(self):
        with pytest.raises(ValueError, match=self.MSG):
            calcular_frete(peso=5.0, uf="S")

    def test_uf_tres_caracteres_levanta_value_error(self):
        with pytest.raises(ValueError, match=self.MSG):
            calcular_frete(peso=5.0, uf="SPX")

    def test_uf_com_mais_de_dois_caracteres_levanta_value_error(self):
        with pytest.raises(ValueError, match=self.MSG):
            calcular_frete(peso=5.0, uf="SPXX")

    # --- Caracteres não-alfabéticos ----------------------------------------

    def test_uf_com_digito_levanta_value_error(self):
        with pytest.raises(ValueError, match=self.MSG):
            calcular_frete(peso=5.0, uf="S1")

    def test_uf_somente_digitos_levanta_value_error(self):
        with pytest.raises(ValueError, match=self.MSG):
            calcular_frete(peso=5.0, uf="12")

    def test_uf_com_caractere_especial_levanta_value_error(self):
        with pytest.raises(ValueError, match=self.MSG):
            calcular_frete(peso=5.0, uf="S@")

    def test_uf_com_espaco_interno_levanta_value_error(self):
        """Espaço interno (entre letras) não é alfabético — deve falhar."""
        with pytest.raises(ValueError, match=self.MSG):
            calcular_frete(peso=5.0, uf="S P")

    def test_uf_com_hifen_levanta_value_error(self):
        with pytest.raises(ValueError, match=self.MSG):
            calcular_frete(peso=5.0, uf="S-")

    def test_uf_com_ponto_levanta_value_error(self):
        with pytest.raises(ValueError, match=self.MSG):
            calcular_frete(peso=5.0, uf="S.")

    # --- Sanitização de espaços externos -----------------------------------

    def test_uf_com_espacos_externos_valida_apos_strip(self):
        """Espaços no início/fim devem ser removidos antes da validação."""
        resultado = calcular_frete(peso=5.0, uf="  SP  ")
        assert resultado == 20.0

    def test_uf_minuscula_com_espacos_externos_valida_apos_strip(self):
        """Strip + uppercase: '  am  ' → 'AM' → região Norte → adicional R$15."""
        resultado = calcular_frete(peso=5.0, uf="  am  ")
        assert resultado == 35.0

    def test_uf_com_tab_externo_valida_apos_strip(self):
        """Tabs no início/fim também devem ser removidos pelo strip."""
        resultado = calcular_frete(peso=5.0, uf="\tSP\t")
        assert resultado == 20.0

    # --- UFs válidas não devem levantar erro de formato --------------------

    @pytest.mark.parametrize("uf", ["SP", "RJ", "AM", "PA", "GO", "sc", "rj"])
    def test_uf_formato_valido_nao_levanta_erro_de_formato(self, uf):
        """UFs com 2 letras (maiúsculas ou minúsculas) não devem gerar erro de formato."""
        # Pode gerar "UF invalida." se a sigla não existir na tabela,
        # mas NUNCA o erro de formato.
        try:
            calcular_frete(peso=5.0, uf=uf)
        except ValueError as exc:
            assert self.MSG not in str(exc), (
                f"UF '{uf}' com formato correto não deveria gerar erro de formato."
            )


# ===========================================================================
# EFICIÊNCIA — lru_cache com maxsize=128 (RF-EFI-01)
# ===========================================================================

class TestEficienciaCache:
    """calcular_frete deve usar lru_cache(maxsize=128)."""

    def test_funcao_possui_cache_info(self):
        """calcular_frete deve expor cache_info() (decorada com lru_cache)."""
        assert hasattr(calcular_frete, "cache_info"), (
            "calcular_frete não possui cache_info — lru_cache não aplicado."
        )

    def test_cache_maxsize_e_128(self):
        """O maxsize do cache deve ser exatamente 128."""
        info = _cache_info()
        assert info.maxsize == 128, (
            f"Esperado maxsize=128, obtido maxsize={info.maxsize}."
        )

    def test_primeira_chamada_gera_miss(self):
        """Primeira chamada com novos parâmetros deve registrar 1 miss."""
        calcular_frete.cache_clear()
        calcular_frete(peso=7.0, uf="SP")
        info = _cache_info()
        assert info.misses >= 1

    def test_segunda_chamada_identica_gera_hit(self):
        """Segunda chamada com mesmos parâmetros deve usar o cache (hit)."""
        calcular_frete.cache_clear()
        calcular_frete(peso=7.0, uf="SP")
        hits_antes = _cache_info().hits

        calcular_frete(peso=7.0, uf="SP")
        hits_depois = _cache_info().hits

        assert hits_depois == hits_antes + 1, (
            "A segunda chamada idêntica não gerou cache hit."
        )

    def test_multiplas_chamadas_identicas_acumulam_hits(self):
        """N chamadas idênticas devem resultar em N-1 hits (1 miss inicial)."""
        calcular_frete.cache_clear()
        n = 5
        for _ in range(n):
            calcular_frete(peso=10.0, uf="RJ")

        info = _cache_info()
        assert info.misses == 1
        assert info.hits == n - 1

    def test_parametros_diferentes_geram_misses_distintos(self):
        """Parâmetros distintos não devem compartilhar entrada no cache."""
        calcular_frete.cache_clear()
        calcular_frete(peso=5.0, uf="SP")
        calcular_frete(peso=15.0, uf="SP")
        calcular_frete(peso=5.0, uf="AM")

        info = _cache_info()
        assert info.misses == 3, (
            "Três combinações distintas devem gerar exatamente 3 misses."
        )

    def test_cache_retorna_mesmo_resultado(self):
        """O valor retornado em cache hit deve ser idêntico ao miss original."""
        calcular_frete.cache_clear()
        resultado_miss = calcular_frete(peso=8.0, uf="MG")
        resultado_hit  = calcular_frete(peso=8.0, uf="MG")
        assert resultado_miss == resultado_hit

    def test_cache_clear_zera_contadores(self):
        """Após cache_clear(), hits e misses devem ser reiniciados a zero."""
        calcular_frete(peso=5.0, uf="SP")  # garante ao menos 1 entrada
        calcular_frete.cache_clear()
        info = _cache_info()
        assert info.hits == 0
        assert info.misses == 0
        assert info.currsize == 0
