interface IFichero {
    fun cargarFichero(ruta:String): MutableList<String>
    fun guardarFichero(lista: MutableList<String>, ruta: String)
    fun borrarLista(lista: MutableList<String>)
}