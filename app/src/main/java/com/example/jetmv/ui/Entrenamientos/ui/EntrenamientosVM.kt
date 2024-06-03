import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.LocalTime

data class Ejercicio(val nombre: String, val hora: LocalTime)

class EntrenamientosVM : ViewModel() {

    private val _ejercicios = MutableStateFlow<List<Ejercicio>>(emptyList())
    val ejercicios: StateFlow<List<Ejercicio>> = _ejercicios

    fun obtenerEjerciciosPorFecha(fecha: LocalDate) {
        if (fecha.isBefore(LocalDate.now())) {
            // No hacer nada o lanzar una excepción si la fecha es anterior a la actual
            return
        }

        // Lista de nombres de ejercicios comunes en el gimnasio
        val nombresEjercicios = listOf(
            "Deadlift", "Bench Press", "Squat", "Pull-up", "Row", "Overhead Press",
            "Bicep Curl", "Tricep Extension", "Leg Press", "Lateral Raise", "Plank",
            "Crunch", "Russian Twist", "Leg Raise", "Mountain Climbers", "Jump Rope",
            "Burpee", "Lunge", "Kettlebell Swing", "Box Jump"
        )

        val ejercicios = mutableListOf<Ejercicio>()
        val horas = (8..12)

        for (hour in horas) {
            val ejerciciosPorHora = nombresEjercicios.shuffled().take(5).map { nombreEjercicio ->
                Ejercicio(nombre = nombreEjercicio, hora = LocalTime.of(hour, 0))
            }
            ejercicios.addAll(ejerciciosPorHora)
        }

        _ejercicios.value = ejercicios
    }
}