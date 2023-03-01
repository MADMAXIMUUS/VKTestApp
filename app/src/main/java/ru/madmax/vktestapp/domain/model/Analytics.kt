package ru.madmax.vktestapp.domain.model

data class Analytics(
    val onclick: Onclick = Onclick(),
    val onload: Onload = Onload(),
    val onsent: Onsent = Onsent()
)