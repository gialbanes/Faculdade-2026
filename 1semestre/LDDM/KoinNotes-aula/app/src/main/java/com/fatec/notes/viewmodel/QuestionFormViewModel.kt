package com.fatec.notes.viewmodel

import androidx.lifecycle.ViewModel
import com.fatec.notes.data.QuestionRepository
import com.fatec.notes.model.Question
import com.fatec.notes.model.QuestionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
data class QuestionFormUiState(
    val questionText: String = "",
    val questionType: QuestionType = QuestionType.MULTIPLE_CHOICE,
    val options: List<String> = listOf("", ""),
    val correctOptionIndex: Int = -1,
    val correctOrder: List<Int> = emptyList(),
    val hasAttemptedSubmit: Boolean = false,
    val savedQuestions: List<Question> = emptyList(),
    val showSuccess: Boolean = false
){
    // Computed properties
    // Só retorna mensagem se o usuário já tentou submeter.
    val textError: String?
        get() = when {
            !hasAttemptedSubmit -> null
            questionText.isBlank() -> "O enunciado é obrigatório"
            questionText.length < 10 -> "Mínimo de 10 caracteres (atual: ${questionText.length})"
            else -> null
        }

    // Verifica vazio e duplicadas
    val optionsErrors: List<String?>
        get() {
            if (!hasAttemptedSubmit) return options.map { null }
            val trimmed = options.map { it.trim().lowercase() }
            return options.mapIndexed { index, option ->
                when {
                    option.isBlank() -> "Alternativa não pode estar vazia"
                    trimmed.indexOf(trimmed[index]) != index -> "Alternativa duplicada"
                    else -> null
                }

            }
        }

    // Multipla escolha - Erro de resposta correta selecionar
    val correctAnswerError: String?
        get() = when {
            !hasAttemptedSubmit -> null
            questionType == QuestionType.MULTIPLE_CHOICE && correctOptionIndex < 0 -> "Selecione a resposta correta"
            else -> null
        }

    // Formálio válido - Todos os campos sem erro
    val isFormValid: Boolean
        get() {
            if (questionText.isBlank() || questionText.length < 10) return false
            if (options.any { it.isBlank() }) return false
            val trimmed = options.map { it.trim().lowercase() }
            if (trimmed.toSet().size != trimmed.size) return false // duplicatas
            if (questionType == QuestionType.MULTIPLE_CHOICE && correctOptionIndex < 0) return false
            return true
        }

    // Lista resumida de todos os erros (ValidationBanner)
    val allErrors: List<String>
        get() {
            if (!hasAttemptedSubmit) return emptyList()
            val errors = mutableListOf<String>()
            textError?.let { errors.add(it) }
            optionsErrors.forEachIndexed { i, err ->
                err?.let { errors.add("Alternativa ${i +1}: $it") }
            }
            correctAnswerError?.let { errors.add(it) }
            return errors
        }

    val canAddOption: Boolean get() = options.size < 6
    val canRemoveOption: Boolean get () = options.size > 2
    val totalSaved: Int get() = savedQuestions.size
}

class QuestionFormViewModel(
    private val repository: QuestionRepository // Koin injeta
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuestionFormUiState())
    val uiState: StateFlow<QuestionFormUiState> = _uiState.asStateFlow()

    // Ações do formulário
    fun onQuestionTextChange(text: String) {
        _uiState.value = _uiState.value.copy(questionText = text)
    }

    // Trocar o tipo reseta a resposta correta
    fun onQuestionTypeChange(type: QuestionType) {
        _uiState.value = _uiState.value.copy(
            questionType = type,
            correctOptionIndex = -1,
            correctOrder = emptyList()
        )
    }

    fun onOptionChange(index: Int, text: String) {
        val updated = _uiState.value.options.toMutableList()
        if (index in updated.indices) {
            updated[index] = text
            _uiState.value = _uiState.value.copy(options = updated)
        }
    }

    // Máximo 6
    // A nova alternativa começa vazia
    fun addOption() {
        val current = _uiState.value
        if (!current.canAddOption) return
        _uiState.value = current.copy(
            options = current.options + ""
        )
    }
    // Remover alternativas - mínimo 2
    fun removeOption(index: Int) {
        val current = _uiState.value
        if (!current.canRemoveOption) return
        val updated = current.options.toMutableList()
        if(index in updated.indices) {
            updated.removeAt(index)

            val newCorrectIndex = when {
                current.correctOptionIndex == index -> -1
                current.correctOptionIndex > index -> current.correctOptionIndex - 1
                else -> current.correctOptionIndex
            }

            _uiState.value = current.copy(
                options = updated,
                correctOptionIndex = newCorrectIndex,
                correctOrder = emptyList() // Reseta ordem ao remover
            )
        }
    }

    fun onCorrectOptionChange(index: Int) {
        _uiState.value = _uiState.value.copy(correctOptionIndex = index)
    }

    fun onCorrectOrderChange(order: List<Int>) {
        _uiState.value = _uiState.value.copy(correctOrder = order)
    }

    // Marca hasAttemptedSubmit = true
    // Só salva se isFormValid = true
    fun submitQuestion() {
        val current = _uiState.value.copy(hasAttemptedSubmit = true)
        _uiState.value = current

        if (!current.isFormValid) return

        // Sequencia digita (0,1,2,3,4)
        val order = if (current.questionType == QuestionType.ORDERING) current.options.indices.toList() else emptyList()

        val question = Question(
            text = current.questionText.trim(),
            type = current.questionType,
            options = current.options.map { it.trim() },
            correctIndex = current.correctOptionIndex,
            correctOrder = order
        )
        repository.add(question)

        _uiState.value = QuestionFormUiState(
            savedQuestions = repository.getAll(),
            showSuccess = true
        )
    }

    fun dismissSuccess() {
        _uiState.value = _uiState.value.copy(showSuccess = false)
    }

    fun removeQuestion(questionId: Long) {
        repository.remove(questionId)
        _uiState.value = _uiState.value.copy(savedQuestions = repository.getAll())
    }

}