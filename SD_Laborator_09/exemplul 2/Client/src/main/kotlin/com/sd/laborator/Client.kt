package com.sd.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.context.annotation.Bean
import org.springframework.integration.annotation.InboundChannelAdapter
import org.springframework.integration.annotation.Poller
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import kotlin.random.Random

@EnableBinding(Source::class)
@SpringBootApplication
open class ClientMicroservice {
    companion object {
        val listaProduse: List<String> = arrayListOf(
            "Masca protectie",
            "Vaccin anti-COVID-19",
            "Combinezon",
            "Manusa chirurgicala"
        )

        ///TODO - lista de produse sa fie preluata din baza de date / din fisier
    }

    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT, poller = [Poller(fixedDelay = "10000", maxMessagesPerPoll = "1")])
    open fun comandaProdus(): () -> Message<String> {
        return {
            val produsComandat = listaProduse[(0 until listaProduse.size).shuffled()[0]]
            val cantitate: Int = Random.nextInt(1, 100)
            val identitateClient = "Popescu Ion"
            val adresaLivrare = "Codrii Vlasiei nr 14"

            ///TODO - datele clientului sa fie citite dinamic si inregistrate in baza de date

            val mesaj = "$identitateClient|$produsComandat|$cantitate|$adresaLivrare"
            MessageBuilder.withPayload(mesaj).build()
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ClientMicroservice>(*args)
}