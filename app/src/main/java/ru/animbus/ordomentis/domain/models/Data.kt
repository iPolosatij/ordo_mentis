package ru.animbus.ordomentis.domain.models

import java.util.UUID

/*
UnitData это основной информационный класс для лоюбой группы как коммерческой так и не коммерческой, возможно семья и т.д.
UserData это основной информационный класс для любого пользователя SharedUser и ItemUser промежуточные
классы для прикрепления юзера к ккакой либо группе с определенной ролью содержит роль и идентификатор
совпадающий с идентификатором основного дата класса UserData
*/
data class UnitData(
    val unitId: String = UUID.randomUUID().toString(),
    val owners: List<String> = listOf(), //лист идентификаторов UserData
    val internal: List<String> = listOf(), //лист идентификаторов UserData
    val external: List<String> = listOf(), //лист идентификаторов UserData
    val publicItems: List<String> = listOf(), //лист идентификаторов MainItemData
    val privateItems: List<String> = listOf(), //лист идентификаторов MainItemData
    val internalUnits: List<String> = listOf(), //лист идентификаторов UnitData
    val externalUnits: List<String> = listOf(), //лист идентификаторов UnitData
    val type: UnitType
)

enum class UnitType{ Family, Group, Organization, Corporation}

data class UserData(
    val userId: String = UUID.randomUUID().toString(),
    val units: List<String>? = null, // список идентификаторов UnitData
    val name: String?,
    val lastName: String?,
    val patronymic: String?,
    val nikName: String,
    val accessItem: List<String> = listOf() //лист идентификаторов MainItemData
)

//может быть задача, цель, идея и т.д.
data class MainItemData(
    val itemId: String = UUID.randomUUID().toString(),
    val iconUrl: String? = null,
    val itemName: String? = null,
    val itemType: ItemType = ItemType.Simple,
    val description: String? = null,
    val subItems: List<MainItemData>? = null,
    val comments: List<ItemComment>? = null,
    val status: ItemStatus = ItemStatus.Free,
    val statusList: List<ItemStatus> = listOf(ItemStatus.Free, ItemStatus.Fall, ItemStatus.Success),
    val ownerId: String,
    val users: List<ItemUser> = listOf(ItemUser(ownerId)),
    val createDate: String,
    val deadline: String? = null,
    val timeEstimation: List<WorkingHours> = listOf(),
    val spentTime: List<WorkingHours> = listOf(),
    val costEstimation: Money? = null,
    val realCost: Money? = null,
    val shared: List<SharedUser> = listOf()// все юзеры добавленные в этот список имеют роль добавившего их юзера
)

data class SharedUser(
    val userId: String,
    val userRole: UserRole,
)

enum class ItemType{
    List, // Может содержать только itemId iconUrl itemName description itemType subItems
    Head, // Может содержать все поля иметь все статусы subItems все кроме List
    Body, // Может содержать все поля иметь все статусы, subItems только Simple
    Simple // Может содержать только itemId iconUrl itemName description itemType, status только Success, Fall или Free
}

//Статус для MainItemData
enum class ItemStatus {Close, Active, Dismiss, Success, Fall, Free}

data class ItemUser (
    val userId: String,
    val role: UserRole = UserRole.Owner
)
enum class UserRole {
    Simple, // Может создать только List и Simple , может добавлять юзеров в shared
    Checker, // только меняет статусы Success, Fall или Free и добавляет и удаляет свои комментарии, может добавлять юзеров в shared
    Worker, // меняет все статусы, добавляет и удаляет свои комментарии, может менять затраты времени для своей роли и менять оценку для своей роли, может добавлять юзеров в shared
    Tester,// меняет все статусы, добавляет и удаляет свои комментарии, может менять затраты времени для своей роли и менять оценку для своей роли, может добавлять юзеров в shared
    Owner, // меняет все статусы, добавляет и удаляет комментарии, может менять затраты времени ,менять оценку, может удалить задачу и все дочерние,  может менять затраты денег и оценку затрат денег, может добавлять юзеров в shared
    Researcher // меняет все статусы, добавляет и удаляет свои комментарии, может менять затраты времени для своей роли и менять оценку для своей роли, может менять затраты денег и оценку затрат денег, может добавлять юзеров в shared
}
// Время планируемое или затраченное
data class WorkingHours(
    val userId: String,
    val hours: Float = 0.0f,
    val workRole: UserRole = UserRole.Owner
)
//комментарий для задачи
data class ItemComment(
    val userId: String,
    val description: String,
)
//деньши для оценки и результатов работы
data class Money(
    val value: Double,
    val type: MoneyType,
)
enum class MoneyType(type: String){ Rub("RUB"), Usd("USD"), Cny("CNY"), Gbp("GBP")}