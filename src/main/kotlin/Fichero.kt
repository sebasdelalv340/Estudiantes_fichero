import java.io.File
import java.io.FileWriter

class Fichero: IFichero {

    override fun cargarFichero(ruta: String): MutableList<String> {
        val datos: MutableList<String> = mutableListOf()

        try {
            File(ruta).forEachLine { linea -> datos.add(linea) }
        } catch (e: Exception) {
            println("Error al cargar los datos del fichero: ${e.message}")
        }
        return datos
    }

    override fun guardarFichero(lista: MutableList<String>, ruta: String) {
        try {
            FileWriter(ruta).use { fichero ->
                for (linea in lista) {
                    fichero.write("$linea\n")
                }
            }
        } catch (e: Exception) {
            println("Error al sobreescribir el fichero: ${e.message}")
        }
    }

    override fun borrarLista(lista: MutableList<String>) {
        lista.clear()
    }
}