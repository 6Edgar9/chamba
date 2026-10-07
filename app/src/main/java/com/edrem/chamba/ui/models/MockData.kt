package com.edrem.chamba.ui.models

data class Category(val id: String, val icon: String, val name: String)
data class Professional(val id: String, val name: String, val trade: String, val rating: Double, val price: String, val isVerified: Boolean)
data class ServiceRequest(val id: String, val title: String, val date: String, val status: String, val assignedProName: String? = null)
data class JobOffer(val id: String, val title: String, val distance: String, val urgency: String, val clientName: String)
data class ChatConversation(val id: String, val contactName: String, val lastMessage: String, val time: String, val unread: Int = 0)
data class ChatMessage(val id: String, val text: String, val isMine: Boolean, val time: String)

object MockData {
    val categories = listOf(Category("1", "🏠", "Hogar"), Category("2", "🔧", "Técnico"), Category("3", "📚", "Educación"), Category("4", "✨", "Belleza"), Category("5", "🚗", "Auto"))
    val professionals = listOf(Professional("1", "Carlos Mendoza", "Gasfitero", 4.9, "S/ 50/hr", true), Professional("2", "Ana Torres", "Electricista", 4.8, "S/ 60/hr", true), Professional("3", "Luis Ramírez", "Carpintero", 4.7, "S/ 45/hr", false))
    val clientRequests = listOf(ServiceRequest("1", "Reparación de tubería", "12 Oct 2026", "En proceso", "Carlos Mendoza"), ServiceRequest("2", "Instalación de luminarias", "15 Oct 2026", "Pendientes", null), ServiceRequest("3", "Limpieza profunda", "01 Oct 2026", "Completadas", "Elena Gómez"))
    val jobOffers = listOf(JobOffer("1", "Fuga de agua en lavadero", "A 1.2 km", "Alta", "María P."), JobOffer("2", "Mantenimiento de terma", "A 3.5 km", "Media", "José L."))
    val chats = listOf(ChatConversation("1", "Carlos Mendoza", "Ya estoy en camino, llego en 10 min.", "10:45 AM", 1), ChatConversation("2", "Elena Gómez", "Gracias por el servicio.", "Ayer", 0))
    val messages = listOf(ChatMessage("1", "Hola Carlos, ¿a qué hora llegas?", true, "10:40 AM"), ChatMessage("2", "Ya estoy en camino, llego en 10 min.", false, "10:45 AM"))
}
