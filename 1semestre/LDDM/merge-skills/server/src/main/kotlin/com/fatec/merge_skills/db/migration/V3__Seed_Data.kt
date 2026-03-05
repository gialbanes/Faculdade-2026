package com.fatec.merge_skills.db.migration

import com.fatec.merge_skills.db.Courses
import com.fatec.merge_skills.db.Lessons
import com.fatec.merge_skills.db.Questions
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

// arquivo para popular o banco
class V3__Seed_Data : BaseJavaMigration() {

    override fun migrate(context: Context) {
        val safeConnection = FlywayConnection(context.connection)
        val database = Database.connect({ safeConnection })

        transaction(database) {
            seedCourses()
            seedLessons()
            seedQuestions()
        }
    }

    // ─── Cursos ───
    private fun seedCourses() {
        data class CourseData(val title: String, val description: String, val icon: String, val color: String, val totalLessons: Int)
        val courses = listOf(
            CourseData("Java Icaro", "Aprenda os fundamentos da linguagem de programação Java", "code", "#E76F00", 4),
            CourseData("Kotlin", "Domine a programação em Kotlin desde o início", "code", "#7F52FF", 4),
            CourseData("Python", "Explore os fundamentos da programação em Python", "code", "#3776AB", 4),
            CourseData("TypeScript", "Construa aplicações com tipagem segura usando TypeScript", "code", "#3178C6", 4)
        )
        courses.forEach { c ->
            Courses.insert {
                it[title] = c.title
                it[description] = c.description
                it[icon] = c.icon
                it[color] = c.color
                it[totalLessons] = c.totalLessons
            }
        }
    }

    // ─── Lições ───
    private fun seedLessons() {
        data class LessonData(val courseId: Int, val title: String, val description: String, val order: Int)
        val lessons = listOf(
            // Java (course 1)
            LessonData(1, "Variáveis", "Tipos de dados, declarações e inicialização em Java", 1),
            LessonData(1, "Laços de Repetição", "Domine for, while e do-while em Java", 2),
            LessonData(1, "Funções", "Métodos, parâmetros e tipos de retorno", 3),
            LessonData(1, "Classes", "Programação orientada a objetos com classes Java", 4),
            // Kotlin (course 2)
            LessonData(2, "Variáveis", "Entenda val, var e inferência de tipo em Kotlin", 1),
            LessonData(2, "Laços de Repetição", "Explore for, while e loops baseados em range", 2),
            LessonData(2, "Funções", "Funções, lambdas e extensões em Kotlin", 3),
            LessonData(2, "Classes", "Data classes, sealed classes e herança", 4),
            // Python (course 3)
            LessonData(3, "Variáveis", "Tipagem dinâmica, atribuições e tipos de dados", 1),
            LessonData(3, "Laços de Repetição", "for-in, while e list comprehensions", 2),
            LessonData(3, "Funções", "def, parâmetros padrão, *args e **kwargs", 3),
            LessonData(3, "Classes", "POO em Python, __init__, herança e métodos especiais", 4),
            // TypeScript (course 4)
            LessonData(4, "Variáveis", "let, const, anotações de tipo e inferência", 1),
            LessonData(4, "Laços de Repetição", "for, for-of, while e métodos de array", 2),
            LessonData(4, "Funções", "Funções tipadas, arrow functions e generics", 3),
            LessonData(4, "Classes", "Classes, interfaces e modificadores de acesso", 4)
        )
        lessons.forEach { l ->
            Lessons.insert {
                it[courseId] = l.courseId
                it[title] = l.title
                it[description] = l.description
                it[order] = l.order
            }
        }
    }

    // ─── Questões ───
    private fun seedQuestions() {
        data class QuestionData(
            val lessonId: Int, val question: String, val code: String?,
            val options: String, val correctAnswer: Int, val order: Int
        )
        val questions = listOf(
            // === Java — Lição 1: Variáveis ===
            QuestionData(1, "Qual é o tipo primitivo usado para armazenar números inteiros em Java?", null, """["float","int","String","boolean"]""", 1, 1),
            QuestionData(1, "Qual palavra reservada é usada para declarar uma constante em Java?", null, """["const","static","final","let"]""", 2, 2),
            QuestionData(1, "Qual é o valor padrão de uma variável int não inicializada como atributo de classe?", null, """["null","0","undefined","-1"]""", 1, 3),
            QuestionData(1, "Qual será o resultado deste código?", "int x = 10;\ndouble y = x;\nSystem.out.println(y);", """["10","10.0","Erro de compilação","null"]""", 1, 4),
            QuestionData(1, "O que este código imprime?", "String nome = \"Java\";\nint versao = 21;\nSystem.out.println(nome + \" \" + versao);", """["Java21","Java 21","Erro de compilação","null 21"]""", 1, 5),

            // === Java — Lição 2: Laços ===
            QuestionData(2, "Qual laço garante que o bloco será executado pelo menos uma vez?", null, """["for","while","do-while","foreach"]""", 2, 1),
            QuestionData(2, "Qual palavra-chave é usada para pular uma iteração do loop?", null, """["break","skip","continue","pass"]""", 2, 2),
            QuestionData(2, "Quantas vezes o loop executa?", "for (int i = 0; i < 5; i++) {\n    System.out.println(i);\n}", """["4","5","6","Infinito"]""", 1, 3),
            QuestionData(2, "Qual é a saída deste código?", "int i = 3;\nwhile (i > 0) {\n    System.out.print(i + \" \");\n    i--;\n}", """["3 2 1","3 2 1 0","2 1 0","Erro"]""", 0, 4),
            QuestionData(2, "O que acontece ao executar este código?", "for (int i = 0; i < 3; i++) {\n    if (i == 1) continue;\n    System.out.print(i + \" \");\n}", """["0 2","0 1 2","1 2","0"]""", 0, 5),

            // === Java — Lição 3: Funções ===
            QuestionData(3, "Qual palavra-chave indica que um método não retorna nenhum valor?", null, """["null","void","empty","none"]""", 1, 1),
            QuestionData(3, "O que é sobrecarga de métodos (overloading)?", null, """["Usar o mesmo nome com parâmetros diferentes","Reutilizar um método de outra classe","Chamar um método recursivamente","Criar métodos estáticos"]""", 0, 2),
            QuestionData(3, "Qual é o retorno deste método?", "public static int soma(int a, int b) {\n    return a + b;\n}\n// Chamada: soma(3, 7)", """["37","10","Erro","null"]""", 1, 3),
            QuestionData(3, "O que este método retorna?", "public static boolean ehPar(int n) {\n    return n % 2 == 0;\n}\n// Chamada: ehPar(5)", """["true","false","0","Erro"]""", 1, 4),
            QuestionData(3, "Quantas vezes a mensagem é impressa?", "public static void repetir(String msg, int vezes) {\n    for (int i = 0; i < vezes; i++) {\n        System.out.println(msg);\n    }\n}\n// Chamada: repetir(\"Oi\", 3)", """["1","2","3","0"]""", 2, 5),

            // === Java — Lição 4: Classes ===
            QuestionData(4, "Qual é o método especial chamado ao criar um objeto em Java?", null, """["init()","create()","construtor","new()"]""", 2, 1),
            QuestionData(4, "Qual modificador de acesso permite que apenas a própria classe acesse o atributo?", null, """["public","protected","private","default"]""", 2, 2),
            QuestionData(4, "O que significa herança em Java?", null, """["Uma classe herda atributos e métodos de outra","Copiar código entre arquivos","Criar variáveis globais","Importar bibliotecas"]""", 0, 3),
            QuestionData(4, "O que este código imprime?", "class Carro {\n    String modelo;\n    Carro(String modelo) {\n        this.modelo = modelo;\n    }\n}\nCarro c = new Carro(\"Civic\");\nSystem.out.println(c.modelo);", """["Carro","Civic","null","Erro"]""", 1, 4),
            QuestionData(4, "Qual é a saída?", "class Animal {\n    String falar() { return \"...\"; }\n}\nclass Gato extends Animal {\n    String falar() { return \"Miau\"; }\n}\nAnimal a = new Gato();\nSystem.out.println(a.falar());", """["...","Miau","Erro","null"]""", 1, 5),

            // === Kotlin — Lição 5: Variáveis ===
            QuestionData(5, "Qual palavra-chave define uma variável imutável (constante) em Kotlin?", null, """["var","final","val","const"]""", 2, 1),
            QuestionData(5, "Como o Kotlin lida com valores nulos por padrão?", null, """["Permite null em qualquer tipo","Tipos são não-nulos por padrão","Usa Optional igual Java","Ignora null"]""", 1, 2),
            QuestionData(5, "O que é inferência de tipo em Kotlin?", null, """["O compilador deduz o tipo baseado no valor","Tipos são dinâmicos","Toda variável é Any","Não existe tipagem forte"]""", 0, 3),
            QuestionData(5, "Qual é o tipo da variável `x`?", "val x = 10.5", """["Int","Double","Float","Number"]""", 1, 4),
            QuestionData(5, "O que acontece neste código?", "var nome: String = \"Kotlin\"\nnome = null", """["Erro de compilação","Compila normal","Imprime null","Crash"]""", 0, 5),

            // === Kotlin — Lição 6: Laços ===
            QuestionData(6, "Qual a sintaxe correta para iterar um range inclusivo?", null, """["for (i in 1..5)","for (i = 1; i <= 5; i++)","foreach (i : 1..5)","loop 1 to 5"]""", 0, 1),
            QuestionData(6, "Como fazer um loop decrescente?", null, """["downTo","minus","step -1","reverse"]""", 0, 2),
            QuestionData(6, "Quantas vezes imprime?", "for (i in 1..3) print(i)", """["2","3","4","1"]""", 1, 3),
            QuestionData(6, "Qual a saída?", "for (i in 1 until 4) print(i)", """["1234","123","12","Erro"]""", 1, 4),
            QuestionData(6, "O que imprime?", "var x = 3\nwhile(x > 0) {\n  print(x)\n  x--\n}", """["321","3210","210","Infinito"]""", 0, 5),

            // === Kotlin — Lição 7: Funções ===
            QuestionData(7, "Qual palavra reservada inicia a declaração de função?", null, """["func","def","fun","function"]""", 2, 1),
            QuestionData(7, "O que é uma Single-Expression Function?", null, """["Uma função sem corpo entre chaves usando =","Uma função lambda","Uma função anônima","Uma função privada"]""", 0, 2),
            QuestionData(7, "O que retorna?", "fun soma(a: Int, b: Int) = a + b\nprintln(soma(2, 3))", """["23","5","Erro","Unit"]""", 1, 3),
            QuestionData(7, "Qual o tipo de retorno padrão se não especificado?", null, """["void","null","Unit","Any"]""", 2, 4),
            QuestionData(7, "O que este código faz?", "fun String.ola() = \"Olá \$this\"\nprintln(\"Mundo\".ola())", """["Olá Mundo","Erro","Mundo Ola","Null"]""", 0, 5),

            // === Kotlin — Lição 8: Classes ===
            QuestionData(8, "Qual classe é usada automaticamente para conter dados?", null, """["struct","record","data class","pojo"]""", 2, 1),
            QuestionData(8, "Classes em Kotlin são por padrão...", null, """["final (fechadas)","open (abertas)","abstract","static"]""", 0, 2),
            QuestionData(8, "Como declarar um construtor primário?", null, """["class User(val nome: String)","constructor User()","def init()","class User { init() }"]""", 0, 3),
            QuestionData(8, "O que imprime?", "data class User(val nome: String)\nval u1 = User(\"Ana\")\nval u2 = User(\"Ana\")\nprintln(u1 == u2)", """["true","false","Erro","Depende da memória"]""", 0, 4),
            QuestionData(8, "Qual a saída?", "open class A\nclass B : A()\nprintln(B() is A)", """["true","false","Erro","null"]""", 0, 5),

            // === Python — Lição 9: Variáveis ===
            QuestionData(9, "Python necessita de declaração explícita de tipos?", null, """["Sim","Não, é dinâmico","Apenas para strings","Depende da versão"]""", 1, 1),
            QuestionData(9, "Como verificar o tipo de uma variável?", null, """["typeof(x)","type(x)","instanceof(x)","check(x)"]""", 1, 2),
            QuestionData(9, "Qual o valor de x?", "x = 10\nx = \"texto\"\nprint(x)", """["10","texto","Erro de tipo","null"]""", 1, 3),
            QuestionData(9, "Como se cria uma lista vazia?", null, """["list() ou []","new List()","Array()","{}"]""", 0, 4),
            QuestionData(9, "O que imprime?", "a = [1, 2, 3]\nb = a\nb.append(4)\nprint(len(a))", """["3","4","Erro","0"]""", 1, 5),

            // === Python — Lição 10: Laços ===
            QuestionData(10, "Qual função gera uma sequência de números?", null, """["seq()","range()","xrange()","list()"]""", 1, 1),
            QuestionData(10, "Como interromper um loop imediatamente?", null, """["stop","exit","break","halt"]""", 2, 2),
            QuestionData(10, "Quantas vezes imprime?", "for i in range(5):\n    print(i)", """["4","5","6","1"]""", 1, 3),
            QuestionData(10, "Qual a saída?", "x = [i*2 for i in range(3)]\nprint(x)", """["[0, 1, 2]","[0, 2, 4]","[2, 4, 6]","[1, 2, 3]"]""", 1, 4),
            QuestionData(10, "O que imprime?", "i = 0\nwhile i < 3:\n    print(i, end=\"\")\n    i += 1", """["0 1 2","012","123","01"]""", 1, 5),

            // === Python — Lição 11: Funções ===
            QuestionData(11, "Qual palavra define uma função em Python?", null, """["func","fun","function","def"]""", 3, 1),
            QuestionData(11, "Como definir valor padrão para parâmetro?", null, """["def f(p: 0)","def f(p=0)","def f(p == 0)","def f(p -> 0)"]""", 1, 2),
            QuestionData(11, "O que retorna?", "def soma(a, b):\n    return a + b\nprint(soma(\"Oj\", \"a\"))", """["Oja","Erro","NaN","null"]""", 0, 3),
            QuestionData(11, "Qual a saída?", "def f(x=[]):\n    x.append(1)\n    return x\nprint(f()); print(f())", """["[1] [1]","[1] [1, 1]","[1] []","Erro"]""", 1, 4),
            QuestionData(11, "O que args captura?", "def f(*args):\n    print(type(args))", """["list","tuple","dict","set"]""", 1, 5),

            // === Python — Lição 12: Classes ===
            QuestionData(12, "Qual é o nome do método construtor?", null, """["constructor","__init__","init","new"]""", 1, 1),
            QuestionData(12, "O que representa o primeiro parâmetro de métodos (self)?", null, """["A instância atual","A classe","O módulo","Nada"]""", 0, 2),
            QuestionData(12, "Como indicar herança?", null, """["class A(B):","class A extends B:","class A : B","class A inherits B"]""", 0, 3),
            QuestionData(12, "Qual a saída?", "class Cao:\n    kind = \"canino\"\nc = Cao()\nc.kind = \"lobo\"\nprint(Cao.kind)", """["lobo","canino","Erro","null"]""", 1, 4),
            QuestionData(12, "O que imprime?", "class A:\n    def __str__(self): return \"A\"\nprint(A())", """["<Object A>","A","Endereço de memória","Erro"]""", 1, 5),

            // === TypeScript — Lição 13: Variáveis ===
            QuestionData(13, "Qual é o superset que TypeScript estende?", null, """["Java","C#","JavaScript","Python"]""", 2, 1),
            QuestionData(13, "Como tipar explicitamente uma variável numérica?", null, """["let n: number","let n: int","var n = (int)","const n :: Number"]""", 0, 2),
            QuestionData(13, "Qual tipo representa \"qualquer coisa\"?", null, """["Object","void","any","unknown"]""", 2, 3),
            QuestionData(13, "O que acontece?", "let x: string = \"TS\";\nx = 10;", """["Erro de compilação TS","Funciona pois é JS","Apenas warning","Crash"]""", 0, 4),
            QuestionData(13, "Qual a saída?", "const lista: number[] = [1, 2];\nlista.push(\"3\");", """["[1, 2, '3']","Erro de tipo","NaN","null"]""", 1, 5),

            // === TypeScript — Lição 14: Laços ===
            QuestionData(14, "Qual loop itera sobre valores de um array?", null, """["for..in","for..of","foreach","loop"]""", 1, 1),
            QuestionData(14, "Qual método de array cria um novo array transformado?", null, """["forEach","map","filter","reduce"]""", 1, 2),
            QuestionData(14, "O que imprime?", "const a = [10, 20];\nfor (let i of a) console.log(i);", """["0 1","10 20","undefined","Erro"]""", 1, 3),
            QuestionData(14, "Qual a saída?", "let i = 0;\ndo { i++; } while (i < 0);\nconsole.log(i);", """["0","1","Erro","-1"]""", 1, 4),
            QuestionData(14, "O que filter faz?", "const nums = [1, 2, 3, 4];\nconsole.log(nums.filter(n => n % 2 === 0));", """["[1, 3]","[2, 4]","[true, false, true, false]","[2]"]""", 1, 5),

            // === TypeScript — Lição 15: Funções ===
            QuestionData(15, "Como definir retorno opcional?", null, """["func f(): void?","func f()?","Retorno é sempre obrigatório","void | undefined"]""", 3, 1),
            QuestionData(15, "Sintaxe de Arrow Function:", null, """["=>","->","function","def"]""", 0, 2),
            QuestionData(15, "O que este código retorna?", "const soma = (a: number, b: number) => a + b;\nconsole.log(soma(2, \"2\"));", """["4","22","Erro de tipo","NaN"]""", 2, 3),
            QuestionData(15, "Qual o tipo de retorno?", "function nada(): void { return 1; }", """["number","void","Erro TS","undefined"]""", 2, 4),
            QuestionData(15, "Generics servem para...", null, """["Criar componentes reutilizáveis e tipados","Aumentar performance","Ofuscar código","Validar em runtime"]""", 0, 5),

            // === TypeScript — Lição 16: Classes ===
            QuestionData(16, "Qual modificador deixa o atributo acessível apenas na classe?", null, """["public","static","private","readonly"]""", 2, 1),
            QuestionData(16, "O que é uma interface em TS?", null, """["Um contrato de estrutura de objeto","Uma classe compilada","Uma função global","Uma variável"]""", 0, 2),
            QuestionData(16, "Como implementar uma interface?", null, """["class A implements I","class A extends I","class A inherits I","class A : I"]""", 0, 3),
            QuestionData(16, "O que imprime?", "class A { static x = 10 }\nconsole.log(A.x)", """["undefined","10","Erro","null"]""", 1, 4),
            QuestionData(16, "Qual a saída?", "interface User { name: string }\nconst u: User = { name: 10 };", """["{ name: 10 }","Erro de tipo","null","undefined"]""", 1, 5)
        )

        questions.forEach { q ->
            Questions.insert {
                it[lessonId] = q.lessonId
                it[question] = q.question
                it[code] = q.code
                it[options] = q.options
                it[correctAnswer] = q.correctAnswer
                it[order] = q.order
            }
        }
    }
}