# SD Laborator 05

## Server rabbitmq

Alternativ la instalarea locala a rabbitmq, puteti folosi docker. Daca nu l-ati mai folosit sau nu stiti ce este atunci instalati local rabbitmq cum e descris in laborator. Vom afla impreuna mai multe despre docker in [laboratorul 08](http://mike.tuiasi.ro/labsd08.pdf). 


```bash
docker compose up -d
```

Credentialele de acces pentru interfata de admin de pe portul 15672 sunt `student:student`.

## Python GUIs

in fisierul `mq_communication.py` din fiecare exemplu aveti un dictionar de config. Asigurati-va ca host-ul si portul din acel dictionar sunt cele corecte. De asemenea s-ar putea sa trebuiasca sa inlocuiti:

```python
    parameters = (pika.ConnectionParameters(host=config['host']),
                  pika.ConnectionParameters(port=config['port']),
                  pika.ConnectionParameters(credentials=credentials))
```

cu

```python
    parameters = pika.ConnectionParameters(host=config["host"], 
                                           port=config["port"], 
                                           credentials=credentials)
```