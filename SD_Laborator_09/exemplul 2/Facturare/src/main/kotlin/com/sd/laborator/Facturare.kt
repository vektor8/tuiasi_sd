package com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.integration.annotation.Transformer
import kotlin.math.abs
import kotlin.random.Random
import kotlin.random.nextUInt

@EnableBinding(Processor::class)
@SpringBootApplication
class FacturareMicroservice {
    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    ///TODO - parametrul ar trebui sa fie doar numarul de inregistrare al comenzii si atat
    fun emitereFactura(comanda: String?): String {
        val (identitateClient, produsComandat, cantitate, adresaLivrare) = comanda!!.split("|")
        println("Emit factura pentru comanda $comanda...")
        val nrFactura = abs(Random.nextInt())
        println("S-a emis factura cu nr $nrFactura.")

        ///TODO - inregistrare factura in baza de date

        return "$comanda"
    }
}

fun main(args: Array<String>) {
    runApplication<FacturareMicroservice>(*args)
}