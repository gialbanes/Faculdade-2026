"""
Suíte de testes unitários do motor de gamificação.

Cobre:
  - calcular_xp      → faixas de tempo e valores limítrofes
  - calcular_nivel   → progressão de nível por XP acumulado
  - subiu_de_nivel   → detecção de transição entre níveis
  - cenários compostos (acúmulo de XP, sequências de respostas)
"""

import pytest

from app.business import calcular_nivel, calcular_xp, subiu_de_nivel


# ---------------------------------------------------------------------------
# calcular_xp — regras de pontuação por velocidade de resposta
# ---------------------------------------------------------------------------

class TestCalcularXP:
    """
    Faixas:
      tempo ≤  5 s  →  100 XP  (rápido)
      tempo ≤ 15 s  →   50 XP  (médio)
      tempo  > 15 s →   25 XP  (lento)
    """

    # — Faixa rápida (≤ 5 s) -------------------------------------------------

    @pytest.mark.unit
    def test_xp_tempo_zero(self):
        """Resposta instantânea ainda vale 100 XP."""
        assert calcular_xp(0.0) == 100

    @pytest.mark.unit
    def test_xp_tempo_muito_pequeno(self):
        """Frações de segundo dentro da faixa rápida."""
        assert calcular_xp(0.001) == 100

    @pytest.mark.unit
    def test_xp_tempo_rapido_tipico(self):
        """Valor típico dentro da faixa rápida."""
        assert calcular_xp(3.0) == 100

    @pytest.mark.unit
    def test_xp_limite_exato_5s(self):
        """Exatamente 5 s ainda pertence à faixa rápida (limite inclusivo)."""
        assert calcular_xp(5.0) == 100

    # — Faixa média (5 < tempo ≤ 15 s) --------------------------------------

    @pytest.mark.unit
    def test_xp_logo_apos_limite_5s(self):
        """5,001 s cai na faixa média."""
        assert calcular_xp(5.001) == 50

    @pytest.mark.unit
    def test_xp_tempo_medio_tipico(self):
        """Valor típico dentro da faixa média."""
        assert calcular_xp(10.0) == 50

    @pytest.mark.unit
    def test_xp_limite_exato_15s(self):
        """Exatamente 15 s ainda pertence à faixa média (limite inclusivo)."""
        assert calcular_xp(15.0) == 50

    # — Faixa lenta (tempo > 15 s) -------------------------------------------

    @pytest.mark.unit
    def test_xp_logo_apos_limite_15s(self):
        """15,001 s cai na faixa lenta."""
        assert calcular_xp(15.001) == 25

    @pytest.mark.unit
    def test_xp_tempo_lento_tipico(self):
        """Valor típico dentro da faixa lenta."""
        assert calcular_xp(20.0) == 25

    @pytest.mark.unit
    def test_xp_tempo_muito_lento(self):
        """Tempo extremamente alto ainda retorna 25 XP (sem penalidade extra)."""
        assert calcular_xp(9999.0) == 25

    # — Invariante: apenas os três valores são retornados --------------------

    @pytest.mark.unit
    @pytest.mark.parametrize("tempo", [0.0, 1.0, 5.0, 5.1, 14.9, 15.0, 15.1, 30.0, 100.0])
    def test_xp_so_retorna_valores_validos(self, tempo):
        """calcular_xp nunca retorna valor fora do conjunto {25, 50, 100}."""
        assert calcular_xp(tempo) in {25, 50, 100}


# ---------------------------------------------------------------------------
# calcular_nivel — mapeamento XP → nível
# ---------------------------------------------------------------------------

class TestCalcularNivel:
    """
    Fórmula: nivel = xp_total // 1000 + 1
    Nível mínimo = 1 (mesmo com 0 XP).
    """

    @pytest.mark.unit
    def test_nivel_com_xp_zero(self):
        """Sem nenhum XP, jogador está no nível 1."""
        assert calcular_nivel(0) == 1

    @pytest.mark.unit
    def test_nivel_1_antes_do_limite(self):
        """Qualquer valor de 1 a 999 XP mantém o nível 1."""
        assert calcular_nivel(1) == 1
        assert calcular_nivel(500) == 1
        assert calcular_nivel(999) == 1

    @pytest.mark.unit
    def test_nivel_2_no_limite_exato(self):
        """Exatamente 1000 XP eleva para o nível 2."""
        assert calcular_nivel(1000) == 2

    @pytest.mark.unit
    def test_nivel_2_dentro_da_faixa(self):
        """1001–1999 XP permanece no nível 2."""
        assert calcular_nivel(1001) == 2
        assert calcular_nivel(1500) == 2
        assert calcular_nivel(1999) == 2

    @pytest.mark.unit
    def test_nivel_3_no_limite_exato(self):
        """2000 XP eleva para o nível 3."""
        assert calcular_nivel(2000) == 3

    @pytest.mark.unit
    def test_nivel_3_dentro_da_faixa(self):
        """2001–2999 XP permanece no nível 3."""
        assert calcular_nivel(2500) == 3
        assert calcular_nivel(2999) == 3

    @pytest.mark.unit
    def test_nivel_10(self):
        """9000 XP → nível 10."""
        assert calcular_nivel(9000) == 10

    @pytest.mark.unit
    def test_nivel_11_no_limite_exato(self):
        """10 000 XP → nível 11."""
        assert calcular_nivel(10_000) == 11

    @pytest.mark.unit
    @pytest.mark.parametrize("xp,nivel_esperado", [
        (0,      1),
        (999,    1),
        (1000,   2),
        (1999,   2),
        (2000,   3),
        (4999,   5),
        (5000,   6),
        (9999,  10),
        (10000, 11),
    ])
    def test_nivel_parametrizado(self, xp, nivel_esperado):
        """Tabela de verdade para a função de nível."""
        assert calcular_nivel(xp) == nivel_esperado

    @pytest.mark.unit
    def test_nivel_nunca_menor_que_1(self):
        """O nível mínimo possível é sempre 1."""
        assert calcular_nivel(0) >= 1


# ---------------------------------------------------------------------------
# subiu_de_nivel — detecção de transição
# ---------------------------------------------------------------------------

class TestSubiuDeNivel:
    """
    Retorna True quando calcular_nivel(xp_antes) < calcular_nivel(xp_depois).
    """

    # — Casos em que NÃO sobe de nível ----------------------------------------

    @pytest.mark.unit
    def test_sem_xp_nao_sobe(self):
        """Nenhum XP ganho, sem mudança de nível."""
        assert subiu_de_nivel(0, 0) is False

    @pytest.mark.unit
    def test_mesmo_nivel_baixo_xp(self):
        """Acúmulo dentro do mesmo nível não aciona subida."""
        assert subiu_de_nivel(100, 200) is False

    @pytest.mark.unit
    def test_mesmo_nivel_proximo_do_limite(self):
        """999 → 999 (sem ganho) permanece no nível 1."""
        assert subiu_de_nivel(999, 999) is False

    @pytest.mark.unit
    def test_mesmo_nivel_muito_xp(self):
        """Dentro do nível 2, sem cruzar para o nível 3."""
        assert subiu_de_nivel(1000, 1999) is False

    # — Casos em que SOBE de nível --------------------------------------------

    @pytest.mark.unit
    def test_sobe_nivel_1_para_2_exato(self):
        """Cruzar exatamente 1000 XP aciona subida do nível 1 para o 2."""
        assert subiu_de_nivel(999, 1000) is True

    @pytest.mark.unit
    def test_sobe_nivel_1_para_2_com_xp_extra(self):
        """950 XP + 100 XP de resposta rápida cruza o limite."""
        assert subiu_de_nivel(950, 1050) is True

    @pytest.mark.unit
    def test_sobe_nivel_2_para_3(self):
        """Transição do nível 2 para o 3."""
        assert subiu_de_nivel(1800, 2100) is True

    @pytest.mark.unit
    def test_sobe_dois_niveis_de_uma_vez(self):
        """Ganho grande o suficiente para pular dois níveis."""
        assert subiu_de_nivel(900, 2100) is True

    @pytest.mark.unit
    def test_sobe_nivel_alto(self):
        """Transição em nível elevado (9 → 10)."""
        assert subiu_de_nivel(8999, 9000) is True

    # — Limite exato: um XP antes da fronteira --------------------------------

    @pytest.mark.unit
    def test_nao_sobe_um_xp_antes_do_limite(self):
        """Com 999 XP, ainda falta 1 para mudar de nível."""
        assert subiu_de_nivel(900, 999) is False

    @pytest.mark.unit
    def test_sobe_exatamente_no_limite(self):
        """Com 999 XP antes e 1000 depois, a subida deve ser detectada."""
        assert subiu_de_nivel(999, 1000) is True


# ---------------------------------------------------------------------------
# Cenários compostos — simulação de sessões de quiz
# ---------------------------------------------------------------------------

class TestCenariosCompostos:
    """
    Verifica o comportamento do motor quando múltiplas respostas
    são processadas em sequência, simulando uma sessão de jogo.
    """

    @pytest.mark.unit
    def test_10_respostas_rapidas_sobem_nivel(self):
        """
        10 respostas rápidas × 100 XP = 1000 XP → sobe para nível 2.
        """
        xp = 0
        for _ in range(10):
            xp += calcular_xp(3.0)
        assert calcular_nivel(xp) == 2

    @pytest.mark.unit
    def test_20_respostas_medias_sobem_nivel(self):
        """
        20 respostas médias × 50 XP = 1000 XP → nível 2.
        """
        xp = sum(calcular_xp(10.0) for _ in range(20))
        assert calcular_nivel(xp) == 2

    @pytest.mark.unit
    def test_40_respostas_lentas_sobem_nivel(self):
        """
        40 respostas lentas × 25 XP = 1000 XP → nível 2.
        """
        xp = sum(calcular_xp(30.0) for _ in range(40))
        assert calcular_nivel(xp) == 2

    @pytest.mark.unit
    def test_progressao_multi_nivel(self):
        """
        30 respostas rápidas → 3000 XP → nível 4.
        """
        xp = sum(calcular_xp(1.0) for _ in range(30))
        assert calcular_nivel(xp) == 4

    @pytest.mark.unit
    def test_subida_detectada_na_decima_resposta_rapida(self):
        """
        A décima resposta rápida (que leva a 1000 XP) deve ser
        a única a acionar subiu_de_nivel == True.
        """
        xp = 0
        subidas = 0
        for _ in range(10):
            xp_antes = xp
            xp += calcular_xp(3.0)
            if subiu_de_nivel(xp_antes, xp):
                subidas += 1
        assert subidas == 1

    @pytest.mark.unit
    def test_subida_detectada_na_resposta_correta_de_duas_sessoes(self):
        """
        Dois ciclos de 10 respostas rápidas devem gerar exatamente
        2 subidas de nível (nível 1→2 e nível 2→3).
        """
        xp = 0
        subidas = 0
        for _ in range(20):
            xp_antes = xp
            xp += calcular_xp(3.0)
            if subiu_de_nivel(xp_antes, xp):
                subidas += 1
        assert subidas == 2

    @pytest.mark.unit
    def test_xp_acumulado_misturado(self):
        """
        Mix de velocidades:
          5 rápidas × 100  = 500 XP
          5 médias  ×  50  = 250 XP
          2 lentas  ×  25  =  50 XP
        Total = 800 XP → ainda no nível 1.
        """
        xp = 0
        xp += sum(calcular_xp(2.0) for _ in range(5))
        xp += sum(calcular_xp(10.0) for _ in range(5))
        xp += sum(calcular_xp(20.0) for _ in range(2))
        assert xp == 800
        assert calcular_nivel(xp) == 1

    @pytest.mark.unit
    def test_nivel_apos_xp_acumulado_misturado_acima_de_1000(self):
        """
        5 rápidas (500) + 5 médias (250) + 10 lentas (250) = 1000 XP → nível 2.
        """
        xp = 0
        xp += sum(calcular_xp(2.0) for _ in range(5))
        xp += sum(calcular_xp(10.0) for _ in range(5))
        xp += sum(calcular_xp(20.0) for _ in range(10))
        assert xp == 1000
        assert calcular_nivel(xp) == 2
