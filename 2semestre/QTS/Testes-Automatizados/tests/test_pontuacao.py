"""
Suíte de testes unitários — Regras de Pontuação e Nível
Referência: docs/prd_pontuacao.md

Requisitos cobertos
-------------------
REQ-XP-1  Respostas em até 5 s concedem 100 XP (bônus máximo)
REQ-XP-2  Respostas entre 5 e 15 s concedem 50 XP
REQ-XP-3  Respostas acima de 15 s concedem 25 XP
REQ-VR-1  Verificação ignora espaços no início/fim da string
REQ-VR-2  Verificação ignora diferença de maiúsculas/minúsculas
REQ-NV-1  A cada 1000 XP o usuário avança 1 nível
          (0–999 XP = nível 1, 1000–1999 XP = nível 2, …)
"""

import pytest

from app.business import calcular_nivel, calcular_xp, verificar_resposta


# ============================================================
# REQ-XP-1 | Respostas em até 5 s → 100 XP
# ============================================================

class TestXPRespostaRapida:
    """REQ-XP-1: tempo ≤ 5 s deve conceder 100 XP."""

    @pytest.mark.unit
    def test_tempo_zero_concede_100_xp(self):
        """Resposta instantânea (0 s) deve valer 100 XP."""
        assert calcular_xp(0.0) == 100

    @pytest.mark.unit
    def test_tempo_tipico_rapido_concede_100_xp(self):
        """Valor típico dentro da faixa rápida (3 s) deve valer 100 XP."""
        assert calcular_xp(3.0) == 100

    @pytest.mark.unit
    def test_limite_exato_5s_concede_100_xp(self):
        """Exatamente 5 s é o limite inclusivo superior da faixa rápida."""
        assert calcular_xp(5.0) == 100

    @pytest.mark.unit
    def test_fracao_de_segundo_concede_100_xp(self):
        """0,001 s é suficientemente rápido para o bônus máximo."""
        assert calcular_xp(0.001) == 100


# ============================================================
# REQ-XP-2 | Respostas entre 5 e 15 s → 50 XP
# ============================================================

class TestXPRespostaMedia:
    """REQ-XP-2: 5 s < tempo ≤ 15 s deve conceder 50 XP."""

    @pytest.mark.unit
    def test_logo_apos_5s_concede_50_xp(self):
        """5,001 s está imediatamente além do limite rápido: deve valer 50 XP."""
        assert calcular_xp(5.001) == 50

    @pytest.mark.unit
    def test_tempo_tipico_medio_concede_50_xp(self):
        """Valor típico dentro da faixa média (10 s) deve valer 50 XP."""
        assert calcular_xp(10.0) == 50

    @pytest.mark.unit
    def test_limite_exato_15s_concede_50_xp(self):
        """Exatamente 15 s é o limite inclusivo superior da faixa média."""
        assert calcular_xp(15.0) == 50


# ============================================================
# REQ-XP-3 | Respostas acima de 15 s → 25 XP
# ============================================================

class TestXPRespostaLenta:
    """REQ-XP-3: tempo > 15 s deve conceder 25 XP."""

    @pytest.mark.unit
    def test_logo_apos_15s_concede_25_xp(self):
        """15,001 s está além da faixa média: deve valer 25 XP."""
        assert calcular_xp(15.001) == 25

    @pytest.mark.unit
    def test_tempo_tipico_lento_concede_25_xp(self):
        """Valor típico lento (30 s) deve valer 25 XP."""
        assert calcular_xp(30.0) == 25

    @pytest.mark.unit
    def test_tempo_extremamente_alto_concede_25_xp(self):
        """Tempo muito alto (9999 s) ainda retorna 25 XP — sem penalidade extra."""
        assert calcular_xp(9999.0) == 25


# ============================================================
# REQ-XP — Invariante geral
# ============================================================

class TestXPInvariante:
    """calcular_xp só deve retornar valores do conjunto {25, 50, 100}."""

    @pytest.mark.unit
    @pytest.mark.parametrize("tempo", [
        0.0, 0.5, 1.0, 4.9, 5.0,   # faixa rápida
        5.1, 8.0, 12.0, 14.9, 15.0, # faixa média
        15.1, 20.0, 60.0, 300.0,    # faixa lenta
    ])
    def test_xp_sempre_pertence_ao_conjunto_valido(self, tempo):
        """Para qualquer tempo ≥ 0, calcular_xp deve retornar 25, 50 ou 100."""
        assert calcular_xp(tempo) in {25, 50, 100}


# ============================================================
# REQ-VR-1 | Verificação ignora espaços no início/fim
# ============================================================

class TestVerificacaoRespostaEspacos:
    """REQ-VR-1: strip() deve ser aplicado antes da comparação."""

    @pytest.mark.unit
    def test_espaco_antes_na_resposta_aceita(self):
        """Espaço no início da resposta não deve invalidá-la."""
        assert verificar_resposta("  4", "4") is True

    @pytest.mark.unit
    def test_espaco_depois_na_resposta_aceita(self):
        """Espaço no fim da resposta não deve invalidá-la."""
        assert verificar_resposta("4  ", "4") is True

    @pytest.mark.unit
    def test_espacos_em_ambos_os_lados_aceita(self):
        """Espaços em ambos os lados da resposta não devem invalidá-la."""
        assert verificar_resposta("  4  ", "4") is True

    @pytest.mark.unit
    def test_espaco_antes_no_gabarito_aceita(self):
        """Espaço no início do gabarito também deve ser ignorado."""
        assert verificar_resposta("4", "  4") is True

    @pytest.mark.unit
    def test_espacos_em_ambos_aceita(self):
        """Espaços em resposta e gabarito ao mesmo tempo devem ser ignorados."""
        assert verificar_resposta("  python  ", "  python  ") is True

    @pytest.mark.unit
    def test_sem_espacos_aceita(self):
        """Strings sem espaços devem continuar funcionando normalmente."""
        assert verificar_resposta("4", "4") is True


# ============================================================
# REQ-VR-2 | Verificação ignora maiúsculas/minúsculas
# ============================================================

class TestVerificacaoRespostaCaixa:
    """REQ-VR-2: lower() deve ser aplicado antes da comparação."""

    @pytest.mark.unit
    def test_resposta_toda_maiuscula_aceita(self):
        """Resposta em letras maiúsculas deve ser aceita."""
        assert verificar_resposta("PRINT", "print") is True

    @pytest.mark.unit
    def test_resposta_capitalizada_aceita(self):
        """Resposta capitalizada deve ser aceita."""
        assert verificar_resposta("Print", "print") is True

    @pytest.mark.unit
    def test_gabarito_maiusculo_aceita(self):
        """Gabarito em maiúsculas e resposta em minúsculas devem ser iguais."""
        assert verificar_resposta("print", "PRINT") is True

    @pytest.mark.unit
    def test_caixa_mista_em_ambos_aceita(self):
        """Caixa mista em ambos os lados deve ser normalizada."""
        assert verificar_resposta("PyThOn", "python") is True

    @pytest.mark.unit
    def test_numeros_nao_sao_afetados_por_caixa(self):
        """Respostas numéricas devem continuar funcionando (sem letras)."""
        assert verificar_resposta("42", "42") is True


# ============================================================
# REQ-VR — Casos de resposta incorreta
# ============================================================

class TestVerificacaoRespostaIncorreta:
    """Respostas erradas devem retornar False independentemente de formatação."""

    @pytest.mark.unit
    def test_resposta_diferente_rejeita(self):
        """Conteúdo diferente deve ser rejeitado."""
        assert verificar_resposta("5", "4") is False

    @pytest.mark.unit
    def test_resposta_parcial_rejeita(self):
        """Resposta que é prefixo do gabarito não deve ser aceita."""
        assert verificar_resposta("prin", "print") is False

    @pytest.mark.unit
    def test_resposta_vazia_com_gabarito_nao_vazio_rejeita(self):
        """String vazia não pode ser igual a uma resposta real."""
        assert verificar_resposta("", "print") is False

    @pytest.mark.unit
    def test_gabarito_vazio_com_resposta_nao_vazia_rejeita(self):
        """Resposta não vazia não pode ser igual a gabarito vazio."""
        assert verificar_resposta("print", "") is False

    @pytest.mark.unit
    def test_resposta_e_gabarito_vazios_aceita(self):
        """Dois campos vazios são estritamente iguais."""
        assert verificar_resposta("", "") is True


# ============================================================
# REQ-NV-1 | A cada 1000 XP o usuário avança 1 nível
# ============================================================

class TestCalcularNivel:
    """
    REQ-NV-1: nivel = xp_total // 1000 + 1
      0–999   XP → nível 1
      1000–1999 XP → nível 2
      2000–2999 XP → nível 3
      …
    """

    # — Nível 1 ---------------------------------------------------------------

    @pytest.mark.unit
    def test_0_xp_e_nivel_1(self):
        """Sem XP, o usuário começa no nível 1."""
        assert calcular_nivel(0) == 1

    @pytest.mark.unit
    def test_999_xp_permanece_nivel_1(self):
        """Com 999 XP o limite ainda não foi atingido: nível 1."""
        assert calcular_nivel(999) == 1

    # — Nível 2 ---------------------------------------------------------------

    @pytest.mark.unit
    def test_1000_xp_avanca_para_nivel_2(self):
        """1000 XP é o marco exato de avanço para o nível 2."""
        assert calcular_nivel(1000) == 2

    @pytest.mark.unit
    def test_1999_xp_permanece_nivel_2(self):
        """Com 1999 XP o próximo marco ainda não foi atingido: nível 2."""
        assert calcular_nivel(1999) == 2

    # — Nível 3 ---------------------------------------------------------------

    @pytest.mark.unit
    def test_2000_xp_avanca_para_nivel_3(self):
        """2000 XP é o marco exato de avanço para o nível 3."""
        assert calcular_nivel(2000) == 3

    @pytest.mark.unit
    def test_2999_xp_permanece_nivel_3(self):
        """Com 2999 XP o próximo marco ainda não foi atingido: nível 3."""
        assert calcular_nivel(2999) == 3

    # — Tabela de verdade parametrizada ---------------------------------------

    @pytest.mark.unit
    @pytest.mark.parametrize("xp,nivel_esperado", [
        (0,    1),   # início — REQ-NV-1
        (1,    1),
        (999,  1),   # limite superior do nível 1
        (1000, 2),   # marco do nível 2 — REQ-NV-1
        (1500, 2),
        (1999, 2),   # limite superior do nível 2
        (2000, 3),   # marco do nível 3 — REQ-NV-1
        (4999, 5),
        (5000, 6),
        (9999, 10),
        (10000, 11),
    ])
    def test_nivel_por_xp_parametrizado(self, xp, nivel_esperado):
        """Tabela de verdade completa para calcular_nivel (REQ-NV-1)."""
        assert calcular_nivel(xp) == nivel_esperado

    # — Invariante: nível mínimo é sempre 1 -----------------------------------

    @pytest.mark.unit
    def test_nivel_minimo_e_sempre_1(self):
        """calcular_nivel nunca deve retornar valor menor que 1."""
        assert calcular_nivel(0) >= 1
