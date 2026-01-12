import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.animbus.ordomentis.ui.main.SyncState

@Composable
fun SyncButton(
    syncState: SyncState,
    onSyncClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(48.dp),
        contentAlignment = Alignment.Center
    ) {
        when (syncState) {
            SyncState.IDLE -> {
                IconButton(
                    onClick = onSyncClick,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        // Используем иконку синхронизации из расширенного набора
                        imageVector = Icons.Filled.Sync,
                        contentDescription = "Синхронизировать",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            SyncState.SYNCING -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            SyncState.SUCCESS -> {
                IconButton(
                    onClick = onSyncClick,
                    modifier = Modifier.size(48.dp),
                    enabled = true
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Синхронизация успешна",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }

            SyncState.ERROR -> {
                IconButton(
                    onClick = onSyncClick,
                    modifier = Modifier.size(48.dp),
                    enabled = true
                ) {
                    Icon(
                        imageVector = Icons.Filled.Error,
                        contentDescription = "Ошибка синхронизации",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}