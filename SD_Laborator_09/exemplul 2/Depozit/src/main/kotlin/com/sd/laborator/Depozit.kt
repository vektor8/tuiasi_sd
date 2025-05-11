package com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.integration.annotation.Transformer
import org.springframework.messaging.support.MessageBuilder
import kotlin.random.Random

@EnableBinding(Processor::class)
@SpringBootApplication
class DepozitMicroservice {
    companion object {
        ///TODO - modelare stoc depozit (baza de date cu stocurile de produse)
        val stocProduse: List<Pair<String, Int>> = mutableListOf(
            "Masca protectie" to 100,
            "Vaccin anti-COVID-19" to 20,
            "Combinezon" to 30,
            "Manusa chirurgicala" to 40
        )
    }

    private fun acceptareComanda(identificator: Int): String {
        println("Comanda cu identificatorul $identificator a fost acceptata!")

        val produsDeExpediat = stocProduse[(0 until stocProduse.size).shuffled()[0]]
        val cantitate = Random.nextInt(produsDeExpediat.second)

        return pregatireColet(produsDeExpediat.first, cantitate)
    }

    private fun respingereComanda(identificator: Int): String {
        println("Comanda cu identificatorul $identificator a fost respinsa! Stoc insuficient.")
        return "RESPINSA"
    }

    private fun verificareStoc(produs: String, cantitate: Int): Boolean {
        ///TODO - verificare stoc produs
        return true
    }

    private fun pregatireColet(produs: String, cantitate: Int): String {
        ///TODO - retragere produs de pe stoc in cantitatea specificata
        println("Produsul $produs in cantitate de $cantitate buc. este pregatit de livrare.")
        return "$produs|$cantitate"
    }

    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    ///TODO - parametrul ar trebui sa fie doar numarul de inregistrare al comenzii si atat
    fun procesareComanda(comanda: String?): String {
        val identificatorComanda = Random.nextInt()
        println("Procesez comanda cu identificatorul $identificatorComanda...")

        //TODO - procesare comanda in depozit
        val rezultatProcesareComanda: String = if (verificareStoc("", 100)) {
            acceptareComanda(identificatorComanda)
        } else {
            respingereComanda(identificatorComanda)
        }

        ///TODO - in loc sa se trimita mesajul cu toate datele in continuare, trebuie trimis doar ID-ul comenzii
        return "$comanda"
    }
}

fun main(args: Array<String>) {
    runApplication<DepozitMicroservice>(*args)
}