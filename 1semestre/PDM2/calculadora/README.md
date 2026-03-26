# Calculadora 

Aplicativo de calculadora simples com interface em **Jetpack Compose**, permitindo operações básicas entre dois números.

## Funcionalidades
- Soma (`+`)
- Subtração (`-`)
- Multiplicação (`*`)
- Divisão (`/`)
- Exibição do resultado em tela
- Preservação de estado em rotação de tela com `rememberSaveable`

## Tecnologias utilizadas
- Kotlin
- Android Studio / Android SDK
- Jetpack Compose (Material 3)
- Gradle (KTS)

## Configuração do projeto
- **Nome do app:** calculadora
- **Application ID:** `com.example.calculadora`
- **Min SDK:** 24
- **Target SDK:** 36
- **Compile SDK:** 36
- **Java/Kotlin JVM target:** 11

## Como executar
1. Abra o projeto no Android Studio.
2. Aguarde a sincronização do Gradle.
3. Execute o app em um emulador Android ou dispositivo físico.

## Estrutura principal
- `app/src/main/java/com/example/calculadora/MainActivity.kt`
- `app/src/main/java/com/example/calculadora/models/CalculadoraModel.kt`
- `app/src/main/java/com/example/calculadora/ui/screens/calculadora/CalculadoraScreen.kt`
