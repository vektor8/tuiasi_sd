# Exemplu RPC (Remote Procedure Call) folosind RabbitMQ

Observati mecanismul de functionare, care este urmatorul: serverul declara o coada si asculta mesaje pe aceasta (`rpc-queue` in cazul de fata). Clientul creeaza o coada proprie, temporara si exclusiva. Ulterior, trimite o cerere pe coada declarata de server, in care precizeaza un ID de corelare (`correlation_id`) si numele cozii temporare unde va astepta raspunsul. 

Cu acesti parametri, serverul poate indeplini cererea si stie sa trimita raspunsul pe coada specificata de client, folosind acelasi ID de corelare pentru a asigura asocierea corecta intre cerere si raspuns.
