package com.fatec.merge_skills.basics

/**
 * ==========================================================
 * AULA 01 - FUNDAMENTOS DE KOTLIN
 * ==========================================================
 *
 */


// ─────────────────────────────────────────────────────────────────────────────
// 1. VAL E VAR — VARIÁVEIS MUTÁVEIS E IMUTÁVEIS
// ─────────────────────────────────────────────────────────────────────────────
// A regra de ouro no Kotlin é preferir `val` sempre que possível.
// `val` é de somente leitura — não pode ser reatribuída.
// `var` pode ter seu valor alterado ao longo do tempo.

fun entendendoVariaveis() {
    // val (Value): variável IMUTÁVEL
    val pi: Double = 3.14
    val nomeDoCurso = "Merge Skills: Android Nativo"   // Inferência de tipo

    // var (Variable): variável MUTÁVEL
    var count: Int = 0
    count = 1 // Permitido

    // pi = 3.14159  // ERRO de compilação — val não pode ser reatribuída
    // nomeDoCurso = "Outro"  // ERRO de compilação

    // Tipagem explícita vs. inferência
    val cargaHoraria: Int = 40
    val valorDaMensalidade: Double = 99.90
    val possuiCertificado: Boolean = true
    val inicialDoNome: Char = 'M'
}


// ─────────────────────────────────────────────────────────────────────────────
// 2. NULL SAFETY — PROTEÇÃO CONTRA NULLPOINTEREXCEPTION
// ─────────────────────────────────────────────────────────────────────────────
// Um dos recursos mais importantes do Kotlin é o sistema de tipos nulos.
// Referências Non-nullable NÃO aceitam null.
// Referências Nullable precisam do operador `?` para serem declaradas.
// Para usar uma variável nullable com segurança, use `?.` ou `?:`.

fun lidandoComNulos() {
    var nome: String = "João"           // Non-nullable: nunca pode ser null
    var sobrenome: String? = null       // Nullable: pode ser null

    // Safe Call `?.`: só executa se sobrenome não for nulo
    val tamanho: Int? = sobrenome?.length

    // Elvis Operator `?:`: retorna valor padrão caso a variável seja nula
    val tamanhoNaoNulo: Int = sobrenome?.length ?: 0

    // Exemplo do projeto: campo de GitHub do usuário
    var githubDoProfessor: String? = null
    val linkSeguro = githubDoProfessor ?: "Não Cadastrado"
}


// ─────────────────────────────────────────────────────────────────────────────
// 3. FUNÇÕES
// ─────────────────────────────────────────────────────────────────────────────
// Blocos de código que realizam uma tarefa específica.
// Declaradas com a palavra-chave `fun`.

// Função tradicional com parâmetros e retorno
fun sayHello(name: String) {
    println("Olá, $name!")
}

// Sintaxe de expressão (single-expression function)
fun somarModerno(a: Int, b: Int) = a + b

// Função com valores padrão nos parâmetros
fun saudar(nome: String, saudacao: String = "Olá") {
    println("$saudacao, $nome!")
}

// Exemplo de uso
fun exemploFuncoes() {
    sayHello("Kotlin")            // Olá, Kotlin!
    val resultado = somarModerno(3, 5)  // 8
    saudar("Maria")               // Olá, Maria!
    saudar("Pedro", "Oi")         // Oi, Pedro!
}


// ─────────────────────────────────────────────────────────────────────────────
// 4. CONTROLE DE FLUXO — IF, WHEN E LOOPS
// ─────────────────────────────────────────────────────────────────────────────
fun controleDeFluxo(notaMedia: Double) {
    // IF como expressão (retorna valor)
    val status = if (notaMedia >= 7.0) "Aprovado" else "Reprovado"

    // WHEN: o Switch-Case potente do Kotlin
    when (notaMedia) {
        10.0           -> println("Aluno Perfeito!")
        in 7.0..9.9    -> println("Aprovado com folga")
        else           -> println("Precisamos estudar mais")
    }

    // FOR em coleção
    val tecnologias = listOf("Kotlin", "Jetpack Compose", "Coroutines", "Koin")
    for (tech in tecnologias) {
        println("Aprenderemos: $tech")
    }

    // WHILE
    var i = 0
    while (i < 3) {
        println("Contando $i")
        i++
    }
}


// ─────────────────────────────────────────────────────────────────────────────
// 5. COLEÇÕES — LIST, SET, MAP
// ─────────────────────────────────────────────────────────────────────────────
// Kotlin oferece coleções ricas. A maioria é imutável por padrão,
// incentivando código mais seguro.

fun exemploColecoes() {
    // ── LIST: coleção ordenada, permite duplicatas ──────────────────────────
    val nomes = listOf("João", "Maria", "Pedro")    // Imutável, não consigo add itens
    val notas = mutableListOf(8, 7, 9)              // Mutável, preciso de "mutableListOf" para poder add
    notas.add(10)                                 // [8, 7, 9, 10]

    // ── SET: coleção de itens únicos, desordenada ────────────────────────────
    val frutas = setOf("Maçã", "Banana", "Morango", "Maçã")
    // frutas conterá apenas "Maçã", "Banana", "Morango" (sem duplicata)

    // ── MAP: pares chave-valor, chaves únicas ────────────────────────────────
    // Mapa Imutável
    val idades = mapOf("João" to 30, "Maria" to 25)
    // pair(2, 'Giovana') - posso inserir dessa maneira tb
    // idades["João"] = 31  // ERRO de compilação

    // Mapa Mutável
    val alunos = mutableMapOf<String, Int>()
    alunos["Pedro"] = 28
    alunos.put("Ana", 22)
    alunos.remove("Pedro")
    // alunos agora contém apenas ("Ana" to 22)
}


// ─────────────────────────────────────────────────────────────────────────────
// 6. HIGHER-ORDER FUNCTIONS E EXPRESSÕES LAMBDA
// ─────────────────────────────────────────────────────────────────────────────
// Funções de ordem superior aceitam outras funções como parâmetro ou as retornam.
// Lambdas são a forma concisa de escrever funções anônimas.

fun exemploHigherOrderFunctions() {
    // map: transforma cada item e retorna uma nova coleção
    val numeros = listOf(1, 2, 3, 4) // função de alta ordem
    val quadrados = numeros.map { it * it } // it serve para pegar o conexto dentro da lista, ou seja, it=1, depois it=2...
    // quadrados = [1, 4, 9, 16]

    // filter: mantém apenas os elementos que satisfazem a condição
    val todoNumeros = listOf(1, 2, 3, 4, 5, 6)
    val pares = todoNumeros.filter { it % 2 == 0 }
    // pares = [2, 4, 6]

    // forEach: executa uma ação para cada elemento
    val nomes = listOf("Ana", "Bruno", "Carlos")
    nomes.forEach { println("Olá, $it!") }

    // Encadeamento de operações (pipeline funcional)
    val resultado = numeros
        .filter { it > 2 }      // [3, 4]
        .map { it * 10 }        // [30, 40]
        .forEach { println(it) }
}


// ─────────────────────────────────────────────────────────────────────────────
// 7. ORIENTAÇÃO A OBJETOS — CLASSES E DATA CLASSES
// ─────────────────────────────────────────────────────────────────────────────
// Classes agrupam dados e comportamentos.
// `data class` é ideal para modelos de dados: gera equals, hashCode e toString.

class AlunoNormal(val nome: String, val idade: Int) {
    fun apresentar() {
        println("Olá, me chamo $nome e tenho $idade anos.")
    }
}

// Data class: usada para representar dados (ex: respostas de API)
data class Curso(
    val id: Int,
    val titulo: String,
    val descricao: String
)


// ─────────────────────────────────────────────────────────────────────────────
// 8. HERANÇA, POLIMORFISMO E INTERFACES
// ─────────────────────────────────────────────────────────────────────────────
// No Kotlin, classes são fechadas por padrão. Use `open` para permitir herança.

open class UsuarioPlataforma(val id: Int, val nickname: String) {
    open fun autenticar() {
        println("Autenticando via email e senha padrão...")
    }
}

class AlunoPremium(
    id: Int,
    nickname: String,
    val beneficiosVip: Boolean
) : UsuarioPlataforma(id, nickname) {

    override fun autenticar() {
        super.autenticar()
        println("Acesso VIP Liberado instantaneamente!")
    }
}

// Interface: define um contrato de ações que a classe DEVE implementar
interface NavegadorDeCursos {
    fun abrirAula(id: Int)
    fun finalizarCurso()
}

class AppCliente : NavegadorDeCursos {
    override fun abrirAula(id: Int) {
        println("Buscando vídeo da aula $id no Backend...")
    }

    override fun finalizarCurso() {
        println("Parabéns, emitindo certificado em PDF!")
    }
}


// ─────────────────────────────────────────────────────────────────────────────
// ATIVIDADE PRÁTICA — Manipulação de Dados em uma Lista
// ─────────────────────────────────────────────────────────────────────────────
// Objetivo: usar os conceitos acima para resolver um problema real.
//
// Tarefa:
// 1. Crie uma lista mutável de nomes de alunos.
// 2. Adicione 3 alunos à lista.
// 3. Use filter para manter apenas os nomes com mais de 4 letras.
// 4. Use map para deixar todos os nomes em maiúsculo.
// 5. Use forEach para imprimir cada nome no console.

fun atividadePratica() {
    val alunos = mutableListOf("Ana", "Bruno", "Carlos", "Bia", "Fernanda")

    alunos
        .filter { it.length > 4 }
        .map { it.uppercase() }
        .forEach { println(it) }

    // Saída esperada:
    // BRUNO
    // CARLOS
    // FERNANDA
}
