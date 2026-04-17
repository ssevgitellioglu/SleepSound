package com.example.sleepsound

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sleepsound.core.ui.theme.SleepSoundTheme
import com.example.sleepsound.domain.model.Sound
import com.example.sleepsound.presentation.PlayerViewModel
import com.example.sleepsound.presentation.TimerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: PlayerViewModel by viewModels()
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SleepSoundTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SoundsGridScreen()
                }
            }
        }
    }
}

@Composable
fun SoundsGridScreen() {
    val viewModel: PlayerViewModel = hiltViewModel()
    val sounds by viewModel.state.collectAsStateWithLifecycle()
    var showTimer by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(50.dp),
            horizontalArrangement = Arrangement.spacedBy(50.dp),
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {

            items(sounds) { sound ->
                SoundItem(
                    sound = sound,
                    isPlaying = viewModel.isPlaying(sound),
                    onClick = {
                        viewModel.togglePlay(sound)
                        viewModel.isPlaying(sound)
                    }
                )
            }

        }
        IconButton(
            onClick = { showTimer = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .background(Color.White, shape = RoundedCornerShape(50))
        ) {
            Icon(Icons.Default.Timer, contentDescription = null)
        }
    }

    if (showTimer) {
        TimerBottomSheet(
            onDismiss = { showTimer = false }
        )
    }
}

@Composable
fun SoundItem(
    sound: Sound,
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            if (isPlaying) {
                Icon(
                    imageVector = Icons.Default.Pause,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }

            Image(
                painter = painterResource(sound.icon),
                contentDescription = sound.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(68.dp)
                    .align(Alignment.TopCenter)
                    .padding(top = 12.dp)
            )

            Text(
                text = sound.title,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp,
                maxLines = 2,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
            )
        }
    }
}
@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun TimerBottomSheet(
    onDismiss: () -> Unit,
    timerViewModel: TimerViewModel = hiltViewModel()
) {

    val timeLeft by timerViewModel.timeLeft.collectAsState()

    ModalBottomSheet(onDismissRequest = onDismiss) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Uyku Zamanlayıcı", fontSize = 20.sp)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = formatTime(timeLeft),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                Button(onClick = {
                    timerViewModel.startTimer(5 * 60 * 1000)
                }) {
                    Text("5 dk")
                }

                Button(onClick = {
                    timerViewModel.startTimer(15 * 60 * 1000)
                }) {
                    Text("15 dk")
                }

                Button(onClick = {
                    timerViewModel.startTimer(30 * 60 * 1000)
                }) {
                    Text("30 dk")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(onClick = {
                timerViewModel.stopTimer()
            }) {
                Text("İptal Et")
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
fun formatTime(ms: Long): String {
    val safeMs = ms.coerceAtLeast(0)
    val totalSeconds = safeMs / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}








