package ru.animbus.ordomentis.uicomponents

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import ru.animbus.ordomentis.data.models.domain.ItemStatus
import ru.animbus.ordomentis.data.models.domain.ItemType
import ru.animbus.ordomentis.data.models.domain.MainItemData

@Composable
fun MainItemCard(
    item: MainItemData,
    onItemClick: (MainItemData) -> Unit = {},
    onSubItemClick: (MainItemData) -> Unit = {},
    onItemChanged: (MainItemData) -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Внутреннее состояние раскрытия
    var isExpanded by remember { mutableStateOf(false) }
    // Состояние редактирования описания
    var isEditingDescription by remember { mutableStateOf(false) }
    // Локальная копия для редактирования
    var editedDescription by remember { mutableStateOf(item.description ?: "") }
    // Состояние статуса (для анимации изменений)
    var currentStatus by remember(item.status) { mutableStateOf(item.status) }

    // Сохранить изменения описания
    fun saveDescriptionChanges() {
        if (editedDescription != item.description) {
            onItemChanged(item.copy(description = editedDescription.ifEmpty { null }))
        }
        isEditingDescription = false
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isExpanded) 4.dp else 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Верхняя строка: иконка + название + стрелка/статус
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // Клик по всей строке для не-Simple элементов
                        if (item.itemType != ItemType.Simple) {
                            onItemClick(item)
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Иконка (или заглушка)
                IconSection(
                    iconUrl = item.iconUrl,
                    modifier = Modifier.size(32.dp)
                )

                // Название с возможностью клика
                Text(
                    text = item.itemName ?: "Без названия",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                        .clickable {
                            // Особый клик по имени для не-Simple
                            if (item.itemType != ItemType.Simple) {
                                onItemClick(item)
                            }
                        },
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Стрелка для раскрытия (если есть что показывать)
                if (shouldShowArrow(item)) {
                    IconButton(
                        onClick = { isExpanded = !isExpanded },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = if (isExpanded)
                                Icons.Filled.KeyboardArrowDown
                            else
                                Icons.Filled.KeyboardArrowRight,
                            contentDescription = if (isExpanded) "Свернуть" else "Раскрыть"
                        )
                    }
                }

                // Иконка статуса с выбором
                StatusIconSelector(
                    currentStatus = currentStatus,
                    availableStatuses = item.statusList,
                    onStatusSelected = { newStatus ->
                        currentStatus = newStatus
                        onItemChanged(item.copy(status = newStatus))
                    }
                )
            }

            // Раскрывающаяся часть
            if (isExpanded) {
                Column(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .animateContentSize()
                ) {
                    // Описание с редактированием
                    DescriptionSection(
                        description = item.description,
                        isEditing = isEditingDescription,
                        editedText = editedDescription,
                        onEditClick = { isEditingDescription = true },
                        onTextChange = { editedDescription = it },
                        onSaveClick = { saveDescriptionChanges() },
                        onCancelClick = {
                            editedDescription = item.description ?: ""
                            isEditingDescription = false
                        }
                    )

                    // Список подзадач
                    SubItemsSection(
                        subItems = item.subItems,
                        onSubItemClick = onSubItemClick,
                        maxVisibleItems = 3 // Показываем только 3
                    )
                }
            }
        }
    }
}

// Вспомогательные компоненты

@Composable
private fun IconSection(
    iconUrl: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        if (!iconUrl.isNullOrEmpty()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(iconUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Иконка",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Заглушка",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
private fun DescriptionSection(
    description: String?,
    isEditing: Boolean,
    editedText: String,
    onEditClick: () -> Unit,
    onTextChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (description != null || isEditing) {
        Column(modifier = modifier.padding(bottom = 12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Описание",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                )

                if (!isEditing) {
                    IconButton(onClick = onEditClick, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Filled.Edit, "Редактировать")
                    }
                } else {
                    Row {
                        IconButton(onClick = onSaveClick, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Filled.ArrowDropDown, "Сохранить")
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        IconButton(onClick = onCancelClick, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Filled.Close, "Отменить")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (isEditing) {
                OutlinedTextField(
                    value = editedText,
                    onValueChange = onTextChange,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    maxLines = 4
                )
            } else {
                Text(
                    text = description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SubItemsSection(
    subItems: List<MainItemData>?,
    onSubItemClick: (MainItemData) -> Unit,
    maxVisibleItems: Int,
    modifier: Modifier = Modifier
) {
    val items = subItems ?: emptyList()
    if (items.isNotEmpty()) {
        Column(modifier = modifier) {
            Text(
                text = "Подзадачи",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Ограничиваем высоту контейнера
            Box(
                modifier = Modifier
                    .heightIn(max = (maxVisibleItems * 56).dp) // 56dp на элемент
                    .verticalScroll(rememberScrollState())
            ) {
                Column {
                    items.take(maxVisibleItems).forEach { subItem ->
                        SubItemRow(
                            item = subItem,
                            onClick = { onSubItemClick(subItem) },
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    // Показываем количество скрытых элементов
                    if (items.size > maxVisibleItems) {
                        Text(
                            text = "+ ещё ${items.size - maxVisibleItems}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SubItemRow(
    item: MainItemData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconSection(
            iconUrl = item.iconUrl,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = item.itemName ?: "Без названия",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // Маленькая стрелочка для подзадачи
        if (shouldShowArrow(item)) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Перейти",
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun StatusIconSelector(
    currentStatus: ItemStatus,
    availableStatuses: List<ItemStatus>,
    onStatusSelected: (ItemStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        // Текущая иконка статуса
        IconButton(
            onClick = { showMenu = true },
            modifier = Modifier.size(32.dp)
        ) {
            StatusIcon(status = currentStatus)
        }

        // Выпадающее меню
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            availableStatuses.forEach { status ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            StatusIcon(status = status, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = status.toString(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    },
                    onClick = {
                        onStatusSelected(status)
                        showMenu = false
                    }
                )
            }
        }
    }
}

@Composable
private fun StatusIcon(
    status: ItemStatus,
    modifier: Modifier = Modifier
) {
    val (icon, color) = when (status) {
        ItemStatus.Close -> Icons.Filled.Lock to Color.Gray
        ItemStatus.Active -> Icons.Filled.PlayArrow to Color.Green
        ItemStatus.Dismiss -> Icons.Filled.Warning to Color.Red
        ItemStatus.Success -> Icons.Filled.CheckCircle to Color(0xFF4CAF50)
        ItemStatus.Fall -> Icons.Filled.Close to Color(0xFFF44336)
        ItemStatus.Free -> Icons.Filled.Star to Color(0xFF2196F3)
    }

    Icon(
        imageVector = icon,
        contentDescription = status.toString(),
        tint = color,
        modifier = modifier
    )
}

// Вспомогательная функция для определения показа стрелки
private fun shouldShowArrow(item: MainItemData): Boolean {
    return item.itemType != ItemType.Simple &&
            (item.description != null || !item.subItems.isNullOrEmpty())
}

// Превью для быстрой проверки
@Preview(showBackground = true)
@Composable
fun MainItemCardPreview() {
    MaterialTheme {
        Surface {
            Column(modifier = Modifier.padding(16.dp)) {
                // Простой элемент
                MainItemCard(
                    item = MainItemData(
                        itemId = "1",
                        itemName = "Простая задача",
                        itemType = ItemType.Simple,
                        description = "Это простая задача без вложений",
                        status = ItemStatus.Free,
                        statusList = ItemStatus.entries,
                        ownerId = "user1",
                        createDate = "2024-01-01"
                    ),
                    onItemChanged = { println("Changed: $it") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Сложный элемент с вложениями
                MainItemCard(
                    item = MainItemData(
                        itemId = "2",
                        iconUrl = "https://example.com/icon.png",
                        itemName = "Сложный проект",
                        itemType = ItemType.Head,
                        description = "Это главная задача со множеством подзадач",
                        subItems = listOf(
                            MainItemData(
                                itemId = "2.1",
                                itemName = "Подзадача 1",
                                itemType = ItemType.Simple,
                                ownerId = "user1",
                                createDate = "2024-01-01"
                            ),
                            MainItemData(
                                itemId = "2.2",
                                itemName = "Подзадача 2",
                                itemType = ItemType.Simple,
                                ownerId = "user1",
                                createDate = "2024-01-01"
                            ),
                            MainItemData(
                                itemId = "2.3",
                                itemName = "Подзадача 3",
                                itemType = ItemType.Simple,
                                ownerId = "user1",
                                createDate = "2024-01-01"
                            ),
                            MainItemData(
                                itemId = "2.4",
                                itemName = "Подзадача 4",
                                itemType = ItemType.Simple,
                                ownerId = "user1",
                                createDate = "2024-01-01"
                            )
                        ),
                        status = ItemStatus.Active,
                        statusList = ItemStatus.entries,
                        ownerId = "user1",
                        createDate = "2024-01-01"
                    ),
                    onItemChanged = { println("Changed: $it") }
                )
            }
        }
    }
}