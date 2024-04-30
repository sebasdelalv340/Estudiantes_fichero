import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

@Composable
fun NewStudent(
    name: String,
    onNewStudentChange: (String) -> Unit,
    onNewStudentClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNewStudentChange,
            label = { Text("New student name") }
        )
        Button(
            onClick = onNewStudentClick
        ) {
            Text(text = "Add new student")
        }
    }
}


@Composable
fun ListaEstudiantes(listaEstudiantes: MutableList<String>, onClearClick: () -> Unit, fichero: IFichero, ruta: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
    ) {
        Surface(
            modifier = Modifier.height(400.dp).width(250.dp),
            color = MaterialTheme.colors.surface,
            border = BorderStroke(2.dp, Color.Black)
        ) {
            /*for (estudiante in fichero.cargarFichero(ruta)) {
                listaEstudiantes.add(estudiante)
            }*/
            LazyColumn(
                contentPadding = PaddingValues(20.dp)
            ) {
                items(listaEstudiantes) { estudiante ->
                    Text(
                        text = estudiante,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
        Button(
            onClick = onClearClick
        ) {
            Text(text = "Clear all")
        }
    }
}


fun main() = application {
    val windosState = rememberWindowState(size = DpSize(1200.dp, 800.dp))
    val icon = BitmapPainter(useResource("sample.png", ::loadImageBitmap))
    val ruta = "src/Estudiantes.txt"
    val fichero: IFichero = Fichero(ruta)
    var newStudent by remember { mutableStateOf("") }
    val listaEstudiantes = remember { mutableStateListOf<String>() }

    Window(onCloseRequest = ::exitApplication,
        title = "My Students",
        state = windosState,
        icon = icon
        ) {

        MaterialTheme {
            Column(
                modifier = Modifier.fillMaxSize().background(Color.Gray),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically),
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NewStudent(
                        newStudent,
                        { newStudent = it },
                        { listaEstudiantes.add(newStudent) }
                    )

                    ListaEstudiantes(
                        listaEstudiantes,
                    { fichero.borrarLista(listaEstudiantes) },
                        fichero,
                        ruta
                    )
                }

                Button(onClick = {
                    fichero.guardarFichero(listaEstudiantes)
                }) {
                    Text(text = "Save changes")
                }
            }
        }
    }
}

