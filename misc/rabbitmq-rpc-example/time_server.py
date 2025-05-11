import pika
import datetime
import pika.channel
import pika.spec

def get_current_time():
    return datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

connection = pika.BlockingConnection(pika.ConnectionParameters("localhost"))
channel = connection.channel()

channel.queue_declare(queue='rpc_queue')

def on_request(ch: pika.channel.Channel, method: pika.spec.Basic.Deliver, props: pika.spec.BasicProperties, body: bytes):
    print("Received RPC request for current time")
    
    response = get_current_time()
    
    ch.basic_publish(
        exchange='',
        routing_key=props.reply_to,
        properties=pika.BasicProperties(correlation_id=props.correlation_id),
        body=str(response),
    )
    ch.basic_ack(delivery_tag=method.delivery_tag)

channel.basic_qos(prefetch_count=1)
channel.basic_consume(queue='rpc_queue', on_message_callback=on_request)

print(" [x] Awaiting RPC requests")
channel.start_consuming()

