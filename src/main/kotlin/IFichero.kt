interface IFichero {
    fun cargarFichero(): MutableList<String>
    fun guardarFichero(lista: MutableList<String>)
    fun borrarLista(lista: MutableList<String>)
}