package utils

object CalculoPrecioTarea {

    fun calculatePrecio(data1: Double?, data2: Double?, data3: Double?): Double {
        return (data1 ?: 0.0) + (data2 ?: 0.0) + (data3 ?: 0.0)
    }
}