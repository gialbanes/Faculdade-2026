package com.fatec.merge_skills.basics

import org.junit.Test

/**
 * Arquivo de Testes Locais para a Aula 01 (Fundamentos)
 *
 * Como estamos dentro de um projeto Android (app), tentar rodar uma função 'main()'
 * tradicional pelo botão Play verde às vezes falha por conta do "Android Gradle Plugin"
 * que não encontra o classpath correto da JVM pura.
 *
 * A forma profissional e GARANTIDA de executar e testar blocos de código sem
 * precisar abrir o emulador é usar o ambiente de Testes Unitários!
 *
 * Clique no botão verde de ▶️ (Play) ao lado da função abaixo para testá-la!
 */
class FundamentosTest {

    @Test
    fun testarNotas() {
        println("=== TESTANDO IF e WHEN ===")
        // Ao rodar, você verá o output do println aqui embaixo no console (Run / Build Output)!
        checarAprovacao(3.9)
        checarAprovacao(8.5)
        checarAprovacao(10.0)
    }

    @Test
    fun testarPOO() {
        println("=== TESTANDO ORIENTAÇÃO A OBJETOS ===")
        val aluno = AlunoNormal(nome = "Yuri", idade = 20)
        aluno.apresentar()

        val alunoVip = AlunoPremium(id = 1, nickname = "yuri_dev", beneficiosVip = true)
        alunoVip.autenticar()
    }

    @Test
    fun testarLoops() {
        println("=== TESTANDO LOOPS ===")
        testandoLoops()
    }
}
