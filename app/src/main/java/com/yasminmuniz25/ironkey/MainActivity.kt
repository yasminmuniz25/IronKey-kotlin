package com.yasminmuniz25.ironkey

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yasminmuniz25.ironkey.ui.theme.IronKeyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IronKeyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IronKeyForm(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun IronKeyForm(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var generatedPassword by remember { mutableStateOf("") }
    var maxCharacters by remember { mutableStateOf(8) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Image(
            painter = painterResource(R.drawable.ironmanimage),
            contentDescription = "Logo do App",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "Senhas Seguras",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier= Modifier.fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        //serve para criar uma caixa com um elemento
        Box (
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ){
            Column {
                OutlinedTextField(
                    //esses dois criam a caixa para o texto
                    value = generatedPassword,
                    onValueChange = {
                        //it é o valor que recebe na variável
                        //vou mudar o nome
                        if(it.length <= maxCharacters)
                        generatedPassword = it
                    },
                    //o label requer um composable, por isso as chaves
                    label = { Text("password") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Password"

                        )
                    },
                    //serve para copiar a senha
                    trailingIcon = {
                        if(generatedPassword.isNotEmpty()){
                            Icon(
                                imageVector = Icons.Filled.ContentCopy,
                                contentDescription = "Copiar",
                                //vira um ícone clicável
                                modifier = Modifier.clickable {
                                    copyPassword(context, generatedPassword)
                                }
                            )
                        }
                    }

                )
                //$ interpolação
                Text("${generatedPassword.length} / $maxCharacters",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.align(Alignment.End)
                        .padding(end=8.dp, top = 4.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
private fun IronManFormPreview() {
    IronKeyTheme {
        IronKeyForm()
    }

}

fun copyPassword(context: Context, password: String) {
    val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as
                ClipboardManager
    val clip = ClipData.newPlainText("Senha", password)
    clipboardManager.setPrimaryClip(clip)
    Toast.makeText(context, "Senha copiada!",
        //short-3segundos
        //long-5segundos
        //tempo p o usuário visualizar a mensagem
        Toast.LENGTH_SHORT).show()
}