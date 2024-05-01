import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewStudent(
    newStudent: String,
    onNewStudentChange: (String) -> Unit,
    onNewStudentClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically)
    ) {
        OutlinedTextField(
            value = newStudent,
            onValueChange = onNewStudentChange,
            label = { Text("New student name") },
            singleLine = true,
            modifier = Modifier.onKeyEvent { event ->
                if (event.key == Key.Enter && newStudent.isNotBlank()) {
                    onNewStudentClick()
                    true
                } else false
            }
        )

        Button(
            onClick = onNewStudentClick
        ) {
            Text(text = "Add new student")
        }
    }
}


@Composable
fun ListaEstudiantes(
    listaEstudiantes: MutableList<String>,
    onClearClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "Estudiantes: ${listaEstudiantes.count()}"
        )
        Surface(
            modifier = Modifier.height(400.dp).width(250.dp),
            color = MaterialTheme.colors.surface,
            border = BorderStroke(2.dp, Color.Black)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(20.dp)
            ) {
                items(listaEstudiantes) { estudiante ->
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = estudiante,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(10.dp),
                        )
                        IconButton(
                            onClick = { listaEstudiantes.remove(estudiante) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null
                            )
                        }
                    }
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


@Composable
fun ButtonSaveChanges(
    onSaveClick: () -> Unit
) {
    Button(
        onClick = onSaveClick
    ) {
        Text(text = "Save changes")
    }
}


fun main() = application {
    val windosState = rememberWindowState(size = DpSize(1200.dp, 800.dp))
    val icon = BitmapPainter(useResource("sample.png", ::loadImageBitmap))
    val ruta = "src/Estudiantes.txt"
    val fichero: IFichero = Fichero()
    var newStudent by remember { mutableStateOf("") }
    val listaEstudiantes = remember { mutableStateListOf<String>() }

    Window(onCloseRequest = ::exitApplication,
        title = "My Students",
        state = windosState,
        icon = icon
        ) {
        for (estudiante in fichero.cargarFichero(ruta)) {
            listaEstudiantes.add(estudiante)

            MaterialTheme {
                Column(
                    modifier = Modifier.fillMaxSize().background(Color.Gray),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NewStudent(
                            newStudent,
                            { newStudent = it },
                            { listaEstudiantes.add(newStudent) }
                        )

                        ListaEstudiantes(
                            listaEstudiantes
                        )
                        { fichero.borrarLista(listaEstudiantes) }
                    }

                    ButtonSaveChanges {
                        fichero.guardarFichero(listaEstudiantes, ruta)
                    }
                }
            }
        }
    }
}

